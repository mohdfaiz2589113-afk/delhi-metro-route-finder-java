package dsa;

import model.Edge;
import model.MetroLine;
import model.Station;

import java.util.*;

/**
 * Implements Dijkstra's Shortest Path Algorithm for the Delhi Metro network.
 *
 * <h3>State-Space Formulation</h3>
 * <p>To correctly model line switches without state contamination at multi-line
 * interchange stations, the Dijkstra state space is augmented to
 * {@code (stationId, currentLine)}. This ensures that arriving at an interchange
 * station on different lines does not prematurely settle the node for other lines.</p>
 *
 * <h3>Optimization Modes</h3>
 * <ul>
 *   <li><b>SHORTEST_DISTANCE</b>: edge weight = distance in kilometres.</li>
 *   <li><b>MINIMUM_INTERCHANGES</b>: adds a +50.0 km equivalent penalty whenever
 *       {@code currentLine != edge.getLine()}, directing the min-heap to prefer
 *       single-line or fewer-transfer paths.</li>
 * </ul>
 *
 * <h3>Complexity</h3>
 * <ul>
 *   <li>Time: O((V' + E') log V') where V' = V × |L|, E' = E × |L|</li>
 *   <li>Space: O(V' + E')</li>
 * </ul>
 */
public class PathFinder {

    /** Penalty added when transferring between lines in MINIMUM_INTERCHANGES mode. */
    private static final double INTERCHANGE_PENALTY = 50.0;

    /** Optimization modes supported by the pathfinder. */
    public enum OptimizationMode {
        SHORTEST_DISTANCE,
        MINIMUM_INTERCHANGES
    }

    /**
     * Represents a state in the priority queue: (station, line arrived on).
     */
    private static class NodeState implements Comparable<NodeState> {
        final String stationId;
        final MetroLine currentLine;
        final double cost;
        final String parentKey;

        NodeState(String stationId, MetroLine currentLine, double cost, String parentKey) {
            this.stationId = stationId;
            this.currentLine = currentLine;
            this.cost = cost;
            this.parentKey = parentKey;
        }

        String getKey() {
            return stationId + "@" + (currentLine == null ? "START" : currentLine.name());
        }

        @Override
        public int compareTo(NodeState other) {
            return Double.compare(this.cost, other.cost);
        }
    }

    // ── Telemetry counters ──────────────────────────────────────────────
    private int nodesVisited;
    private int relaxationsPerformed;

    public PathFinder() {}

    /**
     * Finds the optimal path between two stations using Dijkstra's algorithm.
     *
     * @param graph    the metro graph
     * @param sourceId source station ID
     * @param destId   destination station ID
     * @param mode     optimization mode
     * @return a map containing: "path" (List&lt;String&gt;), "lineUsed" (Map&lt;String,MetroLine&gt;),
     *         "distance" (Double), "time" (Integer), "nodesVisited" (Integer),
     *         "relaxations" (Integer)
     */
    public Map<String, Object> findPath(MetroGraph graph, String sourceId,
                                        String destId, OptimizationMode mode) {
        nodesVisited = 0;
        relaxationsPerformed = 0;

        PriorityQueue<NodeState> pq = new PriorityQueue<>();
        Map<String, Double> dist = new HashMap<>();
        Map<String, String> parent = new HashMap<>();
        Map<String, NodeState> stateMap = new HashMap<>();
        Set<String> visited = new HashSet<>();

        // Start state: source station with no line arrived on yet
        NodeState startState = new NodeState(sourceId, null, 0.0, null);
        dist.put(startState.getKey(), 0.0);
        stateMap.put(startState.getKey(), startState);
        pq.offer(startState);

        NodeState destSettledState = null;

        // ── Main Dijkstra loop ──────────────────────────────────────────
        while (!pq.isEmpty()) {
            NodeState current = pq.poll();

            if (visited.contains(current.getKey())) continue;
            visited.add(current.getKey());
            nodesVisited++;

            // Destination reached with optimal cost
            if (current.stationId.equals(destId)) {
                destSettledState = current;
                break;
            }

            // Relax outgoing edges
            for (Edge edge : graph.getNeighbors(current.stationId)) {
                String neighborId = edge.getDestination().getId();
                MetroLine nextLine = edge.getLine();
                String nextKey = neighborId + "@" + nextLine.name();

                if (visited.contains(nextKey)) continue;

                double edgeCost = edge.getDistanceKm();
                if (mode == OptimizationMode.MINIMUM_INTERCHANGES) {
                    if (current.currentLine != null && current.currentLine != nextLine) {
                        edgeCost += INTERCHANGE_PENALTY;
                    }
                }

                double newDist = current.cost + edgeCost;
                if (newDist < dist.getOrDefault(nextKey, Double.MAX_VALUE)) {
                    dist.put(nextKey, newDist);
                    parent.put(nextKey, current.getKey());
                    NodeState nextState = new NodeState(neighborId, nextLine, newDist, current.getKey());
                    stateMap.put(nextKey, nextState);
                    pq.offer(nextState);
                    relaxationsPerformed++;
                }
            }
        }

        // ── Reconstruct path and lines ──────────────────────────────────
        List<String> path = new ArrayList<>();
        Map<String, MetroLine> lineUsed = new HashMap<>();

        if (destSettledState != null) {
            String curKey = destSettledState.getKey();
            while (curKey != null) {
                NodeState s = stateMap.get(curKey);
                path.add(0, s.stationId);
                if (s.currentLine != null) {
                    lineUsed.put(s.stationId, s.currentLine);
                }
                curKey = parent.get(curKey);
            }
        }

        // Set line for the source station (same as first edge)
        if (path.size() > 1) {
            lineUsed.put(path.get(0), lineUsed.get(path.get(1)));
        }

        // ── Compute actual (unpenalised) distance & travel time ─────────
        double actualDistance = 0.0;
        int actualTime = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            MetroLine usedLine = lineUsed.get(path.get(i + 1));
            for (Edge e : graph.getNeighbors(path.get(i))) {
                if (e.getDestination().getId().equals(path.get(i + 1))
                        && e.getLine() == usedLine) {
                    actualDistance += e.getDistanceKm();
                    actualTime += e.getTravelTimeMin();
                    break;
                }
            }
        }

        // Add ~3 minutes per interchange for walking/transfer time
        long interchangeCount = 0;
        for (int i = 1; i < path.size() - 1; i++) {
            MetroLine prev = lineUsed.get(path.get(i));
            MetroLine next = lineUsed.get(path.get(i + 1));
            if (prev != null && next != null && prev != next) {
                interchangeCount++;
            }
        }
        actualTime += (int) (interchangeCount * 3);

        // ── Package results ─────────────────────────────────────────────
        Map<String, Object> result = new HashMap<>();
        result.put("path", path);
        result.put("lineUsed", lineUsed);
        result.put("distance", Math.round(actualDistance * 10.0) / 10.0);
        result.put("time", actualTime);
        result.put("nodesVisited", nodesVisited);
        result.put("relaxations", relaxationsPerformed);
        return result;
    }
}
