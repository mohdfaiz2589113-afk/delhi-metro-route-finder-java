package model;

import java.util.List;

/**
 * Data Transfer Object encapsulating the result of a route computation.
 * Includes the path, step-by-step instructions, fare information,
 * and algorithm telemetry data for demonstration purposes.
 */
public class RouteResult {

    private final List<Station> path;
    private final List<String> stepByStepInstructions;
    private final List<Station> interchanges;
    private final double totalDistanceKm;
    private final int totalTimeMin;
    private final int tokenFare;
    private final double smartCardFare;
    private final int sundayFare;

    // Algorithm telemetry
    private final int nodesVisited;
    private final int relaxationsPerformed;
    private final long executionTimeMicros;

    // Line segments for visual rendering
    private final List<LineSegment> lineSegments;

    /**
     * A segment of the route on a single metro line.
     */
    public static class LineSegment {
        private final MetroLine line;
        private final List<Station> stations;

        public LineSegment(MetroLine line, List<Station> stations) {
            this.line = line;
            this.stations = stations;
        }

        public MetroLine getLine() { return line; }
        public List<Station> getStations() { return stations; }
    }

    /**
     * Creates a new RouteResult with all computed data.
     */
    public RouteResult(List<Station> path, List<String> stepByStepInstructions,
                       List<Station> interchanges, double totalDistanceKm, int totalTimeMin,
                       int tokenFare, double smartCardFare, int sundayFare,
                       int nodesVisited, int relaxationsPerformed, long executionTimeMicros,
                       List<LineSegment> lineSegments) {
        this.path = path;
        this.stepByStepInstructions = stepByStepInstructions;
        this.interchanges = interchanges;
        this.totalDistanceKm = totalDistanceKm;
        this.totalTimeMin = totalTimeMin;
        this.tokenFare = tokenFare;
        this.smartCardFare = smartCardFare;
        this.sundayFare = sundayFare;
        this.nodesVisited = nodesVisited;
        this.relaxationsPerformed = relaxationsPerformed;
        this.executionTimeMicros = executionTimeMicros;
        this.lineSegments = lineSegments;
    }

    public List<Station> getPath() { return path; }
    public List<String> getStepByStepInstructions() { return stepByStepInstructions; }
    public List<Station> getInterchanges() { return interchanges; }
    public double getTotalDistanceKm() { return totalDistanceKm; }
    public int getTotalTimeMin() { return totalTimeMin; }
    public int getTokenFare() { return tokenFare; }
    public double getSmartCardFare() { return smartCardFare; }
    public int getSundayFare() { return sundayFare; }
    public int getNodesVisited() { return nodesVisited; }
    public int getRelaxationsPerformed() { return relaxationsPerformed; }
    public long getExecutionTimeMicros() { return executionTimeMicros; }
    public List<LineSegment> getLineSegments() { return lineSegments; }
}
