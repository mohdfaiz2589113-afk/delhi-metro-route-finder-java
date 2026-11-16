package model;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a Delhi Metro station with its associated metro lines.
 * Automatically identifies interchange stations (served by multiple lines).
 */
public class Station {

    private final String id;
    private final String name;
    private final Set<MetroLine> lines;
    private final boolean isTerminal;

    /**
     * Creates a new Station.
     *
     * @param id         unique snake_case identifier (e.g., "rajiv_chowk")
     * @param name       display name (e.g., "Rajiv Chowk")
     * @param lines      set of metro lines serving this station
     * @param isTerminal true if this is a terminal (first/last) station on any line
     */
    public Station(String id, String name, Set<MetroLine> lines, boolean isTerminal) {
        this.id = Objects.requireNonNull(id, "Station id must not be null");
        this.name = Objects.requireNonNull(name, "Station name must not be null");
        this.lines = Collections.unmodifiableSet(Objects.requireNonNull(lines));
        this.isTerminal = isTerminal;
    }

    /** Returns the unique station identifier. */
    public String getId() { return id; }

    /** Returns the display name. */
    public String getName() { return name; }

    /** Returns an unmodifiable set of metro lines serving this station. */
    public Set<MetroLine> getLines() { return lines; }

    /** Returns true if this station is a terminal station. */
    public boolean isTerminal() { return isTerminal; }

    /**
     * Returns true if this station is an interchange (served by more than one line).
     * Interchanges allow passengers to transfer between lines.
     */
    public boolean isInterchange() {
        return lines.size() > 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return id.equals(station.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Station{" + name + " [" + id + "], lines=" + lines + ", interchange=" + isInterchange() + "}";
    }
}
