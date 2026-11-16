package dsa;

import model.Edge;
import model.MetroLine;
import model.Station;

import java.util.*;

/**
 * Custom weighted graph for the Delhi Metro network.
 * Implemented using an Adjacency List (Map&lt;String, List&lt;Edge&gt;&gt;).
 *
 * <h3>Complexity</h3>
 * <ul>
 *   <li>Space: O(V + E) where V = stations, E = edges</li>
 *   <li>addStation: O(1)</li>
 *   <li>addBiDirectionalEdge: O(1) amortized</li>
 *   <li>getNeighbors: O(1)</li>
 * </ul>
 */
public class MetroGraph {

    /** Map of station ID → Station object. */
    private final Map<String, Station> stations;

    /** Adjacency list: station ID → list of outgoing edges. */
    private final Map<String, List<Edge>> adjacencyList;

    /** Creates an empty metro graph. */
    public MetroGraph() {
        this.stations = new LinkedHashMap<>();
        this.adjacencyList = new LinkedHashMap<>();
    }

    /**
     * Adds a station to the graph.
     *
     * @param station the station to add
     * @throws IllegalArgumentException if a station with the same ID already exists
     */
    public void addStation(Station station) {
        if (stations.containsKey(station.getId())) {
            throw new IllegalArgumentException("Station already exists: " + station.getId());
        }
        stations.put(station.getId(), station);
        adjacencyList.put(station.getId(), new ArrayList<>());
    }

    /**
     * Adds a bi-directional (undirected) edge between two stations.
     * Creates two directed edges: source→dest and dest→source.
     *
     * @param srcId         source station ID
     * @param destId        destination station ID
     * @param distanceKm    distance in kilometres
     * @param travelTimeMin travel time in minutes
     * @param line          the metro line this connection belongs to
     * @throws IllegalArgumentException if either station is not found in the graph
     */
    public void addBiDirectionalEdge(String srcId, String destId,
                                     double distanceKm, int travelTimeMin, MetroLine line) {
        Station src = stations.get(srcId);
        Station dest = stations.get(destId);
        if (src == null) throw new IllegalArgumentException("Station not found: " + srcId);
        if (dest == null) throw new IllegalArgumentException("Station not found: " + destId);

        adjacencyList.get(srcId).add(new Edge(src, dest, distanceKm, travelTimeMin, line));
        adjacencyList.get(destId).add(new Edge(dest, src, distanceKm, travelTimeMin, line));
    }

    /**
     * Returns all outgoing edges from the given station.
     *
     * @param stationId the station ID
     * @return list of edges, or an empty list if station not found
     */
    public List<Edge> getNeighbors(String stationId) {
        return adjacencyList.getOrDefault(stationId, Collections.emptyList());
    }

    /**
     * Retrieves a station by its ID.
     *
     * @param stationId the station ID
     * @return the Station, or null if not found
     */
    public Station getStation(String stationId) {
        return stations.get(stationId);
    }

    /**
     * Returns all stations in the graph (insertion-order preserved).
     *
     * @return collection of all stations
     */
    public Collection<Station> getAllStations() {
        return Collections.unmodifiableCollection(stations.values());
    }

    /** Returns the number of stations in the graph. */
    public int getStationCount() {
        return stations.size();
    }

    /** Returns the total number of directed edges in the graph. */
    public int getEdgeCount() {
        int count = 0;
        for (List<Edge> edges : adjacencyList.values()) {
            count += edges.size();
        }
        return count;
    }
}
