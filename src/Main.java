import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import dsa.MetroGraph;
import dsa.PathFinder;
import dsa.PathFinder.OptimizationMode;
import model.*;
import model.RouteResult.LineSegment;
import service.FareCalculator;
import service.MetroDataLoader;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Main entry point for the Delhi Metro Route Finder application.
 * Starts an HTTP server on port 8080 serving a web UI and REST API.
 *
 * <h3>Endpoints</h3>
 * <ul>
 *   <li>{@code GET /} — serves web/index.html</li>
 *   <li>{@code GET /api/stations} — returns JSON array of all stations</li>
 *   <li>{@code GET /api/route?from=ID&to=ID&mode=MODE&sunday=BOOL} — computes route</li>
 * </ul>
 */
public class Main {

    private static MetroGraph graph;

    public static void main(String[] args) throws IOException {
        // Load metro data
        graph = MetroDataLoader.loadGraph();
        System.out.println("✅ Loaded " + graph.getStationCount() + " stations and "
                + graph.getEdgeCount() + " edges.");

        // Start HTTP server
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new StaticFileHandler());
        server.createContext("/api/stations", new StationsHandler());
        server.createContext("/api/route", new RouteHandler());
        server.setExecutor(null); // default executor
        server.start();

        System.out.println("🚇 Delhi Metro Route Finder running at http://localhost:" + port);
        System.out.println("   Press Ctrl+C to stop.");
    }

    // ═════════════════════════════════════════════════════════════════════
    //  HANDLERS
    // ═════════════════════════════════════════════════════════════════════

    /** Serves web/index.html for the root path. */
    static class StaticFileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"GET".equals(exchange.getRequestMethod())) {
                sendResponse(exchange, 405, "{\"error\":\"Method not allowed\"}");
                return;
            }

            // Resolve the web/ directory relative to the working directory
            Path filePath = Path.of("web", "index.html");
            if (!Files.exists(filePath)) {
                sendResponse(exchange, 404, "<!DOCTYPE html><html><body><h1>404 Not Found</h1>"
                        + "<p>web/index.html not found. Run from the project root directory.</p></body></html>");
                return;
            }

            byte[] content = Files.readAllBytes(filePath);
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, content.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(content);
            }
        }
    }

    /** Returns JSON array of all stations. */
    static class StationsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"GET".equals(exchange.getRequestMethod())) {
                sendResponse(exchange, 405, "{\"error\":\"Method not allowed\"}");
                return;
            }
            setCorsHeaders(exchange);

            StringBuilder sb = new StringBuilder("[");
            boolean first = true;
            for (Station station : graph.getAllStations()) {
                if (!first) sb.append(",");
                first = false;
                sb.append(stationToJson(station));
            }
            sb.append("]");

            sendJsonResponse(exchange, 200, sb.toString());
        }
    }

    /** Computes and returns a route between two stations. */
    static class RouteHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"GET".equals(exchange.getRequestMethod())) {
                sendResponse(exchange, 405, "{\"error\":\"Method not allowed\"}");
                return;
            }
            setCorsHeaders(exchange);

            // Parse query parameters
            Map<String, String> params = parseQuery(exchange.getRequestURI().getRawQuery());
            String from = params.getOrDefault("from", "");
            String to = params.getOrDefault("to", "");
            String modeStr = params.getOrDefault("mode", "SHORTEST_DISTANCE");
            boolean sunday = "true".equalsIgnoreCase(params.getOrDefault("sunday", "false"));

            // Validate
            if (from.isEmpty() || to.isEmpty()) {
                sendJsonResponse(exchange, 400,
                        "{\"error\":\"Missing 'from' or 'to' parameter\"}");
                return;
            }
            if (graph.getStation(from) == null) {
                sendJsonResponse(exchange, 404,
                        "{\"error\":\"Source station not found: " + escapeJson(from) + "\"}");
                return;
            }
            if (graph.getStation(to) == null) {
                sendJsonResponse(exchange, 404,
                        "{\"error\":\"Destination station not found: " + escapeJson(to) + "\"}");
                return;
            }
            if (from.equals(to)) {
                sendJsonResponse(exchange, 400,
                        "{\"error\":\"Source and destination cannot be the same\"}");
                return;
            }

            OptimizationMode mode;
            try {
                mode = OptimizationMode.valueOf(modeStr);
            } catch (IllegalArgumentException e) {
                mode = OptimizationMode.SHORTEST_DISTANCE;
            }

            // ── Run Dijkstra ────────────────────────────────────────────
            PathFinder pathFinder = new PathFinder();
            long startNanos = System.nanoTime();
            Map<String, Object> result = pathFinder.findPath(graph, from, to, mode);
            long elapsedMicros = (System.nanoTime() - startNanos) / 1000;

            @SuppressWarnings("unchecked")
            List<String> pathIds = (List<String>) result.get("path");
            @SuppressWarnings("unchecked")
            Map<String, MetroLine> lineUsed = (Map<String, MetroLine>) result.get("lineUsed");
            double distance = (Double) result.get("distance");
            int time = (Integer) result.get("time");
            int nodesVisited = (Integer) result.get("nodesVisited");
            int relaxations = (Integer) result.get("relaxations");

            // ── Build path stations ─────────────────────────────────────
            List<Station> pathStations = new ArrayList<>();
            for (String id : pathIds) {
                pathStations.add(graph.getStation(id));
            }

            // ── Find interchanges ───────────────────────────────────────
            List<Station> interchanges = new ArrayList<>();
            for (int i = 1; i < pathIds.size() - 1; i++) {
                MetroLine prev = lineUsed.get(pathIds.get(i));
                MetroLine next = (i + 1 < pathIds.size()) ? lineUsed.get(pathIds.get(i + 1)) : null;
                if (prev != null && next != null && prev != next) {
                    interchanges.add(graph.getStation(pathIds.get(i)));
                }
            }

            // ── Build line segments ─────────────────────────────────────
            List<LineSegment> segments = new ArrayList<>();
            if (pathIds.size() > 1) {
                MetroLine currentLine = lineUsed.get(pathIds.get(1));
                List<Station> currentStations = new ArrayList<>();
                currentStations.add(graph.getStation(pathIds.get(0)));

                for (int i = 1; i < pathIds.size(); i++) {
                    MetroLine thisLine = lineUsed.get(pathIds.get(i));
                    if (thisLine != currentLine && currentLine != null) {
                        segments.add(new LineSegment(currentLine, new ArrayList<>(currentStations)));
                        currentStations.clear();
                        currentStations.add(graph.getStation(pathIds.get(i - 1))); // overlap at interchange
                        currentLine = thisLine;
                    }
                    currentStations.add(graph.getStation(pathIds.get(i)));
                }
                if (!currentStations.isEmpty()) {
                    segments.add(new LineSegment(currentLine, currentStations));
                }
            }

            // ── Build step-by-step instructions ─────────────────────────
            List<String> instructions = buildInstructions(pathIds, lineUsed);

            // ── Calculate fares ─────────────────────────────────────────
            int tokenFare = FareCalculator.calculateTokenFare(distance);
            double smartCardFare = FareCalculator.calculateSmartCardFare(distance);
            int sundayFare = FareCalculator.calculateSundayFare(distance);

            // ── Build JSON response ─────────────────────────────────────
            RouteResult route = new RouteResult(
                    pathStations, instructions, interchanges,
                    distance, time, tokenFare, smartCardFare, sundayFare,
                    nodesVisited, relaxations, elapsedMicros, segments
            );

            String json = routeResultToJson(route);
            sendJsonResponse(exchange, 200, json);
        }
    }

    // ═════════════════════════════════════════════════════════════════════
    //  INSTRUCTION BUILDER
    // ═════════════════════════════════════════════════════════════════════

    private static List<String> buildInstructions(List<String> pathIds,
                                                   Map<String, MetroLine> lineUsed) {
        List<String> instructions = new ArrayList<>();
        if (pathIds.size() < 2) return instructions;

        MetroLine currentLine = lineUsed.get(pathIds.get(1));
        Station startStation = graph.getStation(pathIds.get(0));
        instructions.add("Board " + currentLine.getDisplayName()
                + " from " + startStation.getName());

        for (int i = 1; i < pathIds.size(); i++) {
            MetroLine thisLine = lineUsed.get(pathIds.get(i));
            if (i + 1 < pathIds.size()) {
                MetroLine nextLine = lineUsed.get(pathIds.get(i + 1));
                if (nextLine != null && thisLine != nextLine) {
                    Station interchange = graph.getStation(pathIds.get(i));
                    instructions.add("Change from " + thisLine.getDisplayName()
                            + " to " + nextLine.getDisplayName()
                            + " at " + interchange.getName());
                }
            }
        }

        Station endStation = graph.getStation(pathIds.get(pathIds.size() - 1));
        instructions.add("Alight at " + endStation.getName());
        return instructions;
    }

    // ═════════════════════════════════════════════════════════════════════
    //  JSON SERIALIZATION (manual — zero external libraries)
    // ═════════════════════════════════════════════════════════════════════

    private static String routeResultToJson(RouteResult r) {
        StringBuilder sb = new StringBuilder("{");

        // path
        sb.append("\"path\":[");
        for (int i = 0; i < r.getPath().size(); i++) {
            if (i > 0) sb.append(",");
            sb.append(stationToJson(r.getPath().get(i)));
        }
        sb.append("],");

        // instructions
        sb.append("\"instructions\":[");
        for (int i = 0; i < r.getStepByStepInstructions().size(); i++) {
            if (i > 0) sb.append(",");
            sb.append("\"").append(escapeJson(r.getStepByStepInstructions().get(i))).append("\"");
        }
        sb.append("],");

        // interchanges
        sb.append("\"interchanges\":[");
        for (int i = 0; i < r.getInterchanges().size(); i++) {
            if (i > 0) sb.append(",");
            sb.append(stationToJson(r.getInterchanges().get(i)));
        }
        sb.append("],");

        // scalar fields
        sb.append("\"totalDistanceKm\":").append(r.getTotalDistanceKm()).append(",");
        sb.append("\"totalTimeMin\":").append(r.getTotalTimeMin()).append(",");
        sb.append("\"tokenFare\":").append(r.getTokenFare()).append(",");
        sb.append("\"smartCardFare\":").append(r.getSmartCardFare()).append(",");
        sb.append("\"sundayFare\":").append(r.getSundayFare()).append(",");
        sb.append("\"nodesVisited\":").append(r.getNodesVisited()).append(",");
        sb.append("\"relaxationsPerformed\":").append(r.getRelaxationsPerformed()).append(",");
        sb.append("\"executionTimeMicros\":").append(r.getExecutionTimeMicros()).append(",");

        // line segments
        sb.append("\"lineSegments\":[");
        for (int i = 0; i < r.getLineSegments().size(); i++) {
            if (i > 0) sb.append(",");
            LineSegment seg = r.getLineSegments().get(i);
            sb.append("{\"line\":");
            sb.append(metroLineToJson(seg.getLine()));
            sb.append(",\"stations\":[");
            for (int j = 0; j < seg.getStations().size(); j++) {
                if (j > 0) sb.append(",");
                sb.append(stationToJson(seg.getStations().get(j)));
            }
            sb.append("]}");
        }
        sb.append("]");

        sb.append("}");
        return sb.toString();
    }

    private static String stationToJson(Station s) {
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":\"").append(escapeJson(s.getId())).append("\",");
        sb.append("\"name\":\"").append(escapeJson(s.getName())).append("\",");
        sb.append("\"lines\":[");
        int i = 0;
        for (MetroLine line : s.getLines()) {
            if (i++ > 0) sb.append(",");
            sb.append(metroLineToJson(line));
        }
        sb.append("],");
        sb.append("\"interchange\":").append(s.isInterchange()).append(",");
        sb.append("\"terminal\":").append(s.isTerminal());
        sb.append("}");
        return sb.toString();
    }

    private static String metroLineToJson(MetroLine line) {
        return "{\"name\":\"" + line.name()
                + "\",\"displayName\":\"" + escapeJson(line.getDisplayName())
                + "\",\"hexColor\":\"" + line.getHexColor() + "\"}";
    }

    private static String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    // ═════════════════════════════════════════════════════════════════════
    //  HTTP UTILITIES
    // ═════════════════════════════════════════════════════════════════════

    private static void sendResponse(HttpExchange exchange, int code, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(code, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private static void sendJsonResponse(HttpExchange exchange, int code, String json) throws IOException {
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.sendResponseHeaders(code, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private static void setCorsHeaders(HttpExchange exchange) {
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
    }

    private static Map<String, String> parseQuery(String query) {
        Map<String, String> params = new HashMap<>();
        if (query == null || query.isEmpty()) return params;
        for (String param : query.split("&")) {
            String[] kv = param.split("=", 2);
            String key = URLDecoder.decode(kv[0], StandardCharsets.UTF_8);
            String value = kv.length > 1 ? URLDecoder.decode(kv[1], StandardCharsets.UTF_8) : "";
            params.put(key, value);
        }
        return params;
    }
}
