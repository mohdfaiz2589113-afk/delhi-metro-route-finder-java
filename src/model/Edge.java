package model;

/**
 * Represents a weighted directed edge in the metro graph.
 * Connects a source station to a destination station on a specific metro line.
 */
public class Edge {

    private final Station source;
    private final Station destination;
    private final double distanceKm;
    private final int travelTimeMin;
    private final MetroLine line;

    /**
     * Creates a new directed Edge.
     *
     * @param source        the origin station
     * @param destination   the destination station
     * @param distanceKm    distance in kilometres
     * @param travelTimeMin travel time in minutes
     * @param line          the metro line this edge belongs to
     */
    public Edge(Station source, Station destination, double distanceKm, int travelTimeMin, MetroLine line) {
        this.source = source;
        this.destination = destination;
        this.distanceKm = distanceKm;
        this.travelTimeMin = travelTimeMin;
        this.line = line;
    }

    /** Returns the source (origin) station. */
    public Station getSource() { return source; }

    /** Returns the destination station. */
    public Station getDestination() { return destination; }

    /** Returns the distance in kilometres. */
    public double getDistanceKm() { return distanceKm; }

    /** Returns the travel time in minutes. */
    public int getTravelTimeMin() { return travelTimeMin; }

    /** Returns the metro line this edge belongs to. */
    public MetroLine getLine() { return line; }

    @Override
    public String toString() {
        return source.getName() + " --[" + line.getDisplayName() + ", " + distanceKm + "km, "
                + travelTimeMin + "min]--> " + destination.getName();
    }
}
