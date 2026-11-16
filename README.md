# 🚇 Delhi Metro Route & Fare Finder

A **production-grade**, zero-dependency Java 17+ web application that finds optimal routes across the Delhi Metro network using **Dijkstra's Shortest Path Algorithm**. Features a sleek dark-mode web dashboard with real-time algorithm telemetry.

> **Zero External Dependencies** — Built entirely with `java.util.*`, `java.io.*`, and `com.sun.net.httpserver.HttpServer`. No Maven, no Gradle, no Spring Boot, no JSON libraries.

---

## 🚀 Quick Start

### Prerequisites
- **JDK 17+** installed and `javac`/`java` available on PATH

### Compile & Run

```bash
# From the project root directory:
javac -encoding UTF-8 -d out src/model/*.java src/dsa/*.java src/service/*.java src/Main.java

# Run the server:
java -cp out Main
```

Then open **http://localhost:8080** in your browser.

---

## 📁 Project Structure

```
PBL project/
├── src/
│   ├── model/
│   │   ├── MetroLine.java          # Enum: DMRC lines with hex colors
│   │   ├── Station.java            # Station with interchange detection
│   │   ├── Edge.java               # Weighted directed edge
│   │   └── RouteResult.java        # Route computation DTO
│   ├── dsa/
│   │   ├── MetroGraph.java         # Adjacency-list weighted graph
│   │   └── PathFinder.java         # Dijkstra's algorithm (2 modes)
│   ├── service/
│   │   ├── MetroDataLoader.java    # Delhi Metro data (~120 stations)
│   │   └── FareCalculator.java     # DMRC fare slab calculator
│   └── Main.java                   # HTTP server + JSON serializer
├── web/
│   └── index.html                  # Single-file dark-mode dashboard
└── README.md
```

---

## 🏗️ UML Class Diagram

```
┌─────────────────────┐     ┌─────────────────────┐
│   <<enum>>          │     │      Station         │
│   MetroLine         │     │─────────────────────│
│─────────────────────│     │ - id: String         │
│ YELLOW              │◄────│ - name: String       │
│ BLUE                │     │ - lines: Set<Line>   │
│ RED                 │     │ - isTerminal: bool   │
│ VIOLET              │     │─────────────────────│
│ MAGENTA             │     │ + isInterchange()    │
│ PINK                │     └──────────┬──────────┘
│ AIRPORT_EXPRESS     │                │
│─────────────────────│                │ source/dest
│ + getDisplayName()  │     ┌──────────▼──────────┐
│ + getHexColor()     │     │       Edge           │
└─────────────────────┘     │─────────────────────│
                            │ - source: Station    │
                            │ - destination: Stn   │
                            │ - distanceKm: double │
                            │ - travelTimeMin: int │
                            │ - line: MetroLine    │
                            └──────────┬──────────┘
                                       │
          ┌────────────────────────────▼──────────────────────┐
          │                 MetroGraph                        │
          │──────────────────────────────────────────────────│
          │ - stations: Map<String, Station>                 │
          │ - adjacencyList: Map<String, List<Edge>>         │
          │──────────────────────────────────────────────────│
          │ + addStation(Station)                             │
          │ + addBiDirectionalEdge(src, dest, dist, time, L) │
          │ + getNeighbors(stationId): List<Edge>            │
          │ + getAllStations(): Collection<Station>           │
          └──────────────────────┬───────────────────────────┘
                                 │ uses
          ┌──────────────────────▼───────────────────────────┐
          │                PathFinder                         │
          │──────────────────────────────────────────────────│
          │ - INTERCHANGE_PENALTY = 50.0                     │
          │ - nodesVisited: int                              │
          │ - relaxationsPerformed: int                      │
          │──────────────────────────────────────────────────│
          │ + findPath(graph, src, dest, mode): Map          │
          │──────────────────────────────────────────────────│
          │ <<inner>> NodeState implements Comparable         │
          │   - stationId, cost, parentId, currentLine       │
          │ <<enum>> OptimizationMode                        │
          │   SHORTEST_DISTANCE | MINIMUM_INTERCHANGES       │
          └──────────────────────────────────────────────────┘

          ┌─────────────────────┐   ┌─────────────────────┐
          │  MetroDataLoader    │   │   FareCalculator     │
          │─────────────────────│   │─────────────────────│
          │ + loadGraph(): Graph│   │ + tokenFare(km)      │
          └─────────────────────┘   │ + smartCardFare(km)  │
                                    │ + sundayFare(km)     │
          ┌─────────────────────┐   └─────────────────────┘
          │    RouteResult      │
          │─────────────────────│
          │ path, instructions  │
          │ interchanges, fares │
          │ telemetry data      │
          │─────────────────────│
          │ <<inner>> LineSegment│
          └─────────────────────┘
```

---

## 🧮 Algorithm Details

### Dijkstra's Shortest Path Algorithm

The `PathFinder` class implements Dijkstra's algorithm using a **min-heap** (`PriorityQueue<NodeState>`) for efficient vertex extraction.

#### Two Optimization Modes

| Mode | Edge Cost Formula | Purpose |
|------|------------------|---------|
| `SHORTEST_DISTANCE` | `edge.distanceKm` | Minimizes total travel distance |
| `MINIMUM_INTERCHANGES` | `edge.distanceKm + 50.0` (if line changes) | Avoids line transfers by penalizing switches |

The **Minimum Interchanges** mode adds a 50 km-equivalent penalty whenever the metro line changes between consecutive edges. This makes Dijkstra naturally prefer paths that stay on the same line, even if they are slightly longer in distance.

---

## ⏱️ Time & Space Complexity Table

| Operation / Component | Time Complexity | Space Complexity | Notes |
|----------------------|----------------|-----------------|-------|
| **Dijkstra's Algorithm** | O((V + E) log V) | O(V + E) | V = stations, E = edges |
| PQ Insert/Extract-Min | O(log V) | O(V) | Binary heap via PriorityQueue |
| Edge Relaxation | O(1) per edge | O(1) | Compare and update dist[] |
| Path Reconstruction | O(V) | O(V) | Backtrack through parent map |
| **Graph Operations** | | | |
| addStation() | O(1) | O(1) | HashMap insert |
| addBiDirectionalEdge() | O(1) amortized | O(1) | ArrayList append × 2 |
| getNeighbors() | O(1) | O(degree) | HashMap lookup |
| getAllStations() | O(V) | O(V) | Collection view |
| **Fare Calculation** | O(1) | O(1) | Fixed slab lookup |
| **Graph Construction** | O(V + E) | O(V + E) | One-time initialization |
| **JSON Serialization** | O(V) per response | O(V) | Linear scan of path |

### Variables
- **V** (Vertices) = ~120 stations
- **E** (Edges) = ~280 directed edges (140 bi-directional connections)

---

## 💰 DMRC Fare Structure

| Distance | Token Fare | Smart Card (10% off) | Sunday/Holiday |
|----------|-----------|---------------------|----------------|
| 0–2 km   | ₹11       | ₹9.9                | ₹10            |
| 2–5 km   | ₹21       | ₹18.9               | ₹15            |
| 5–12 km  | ₹32       | ₹28.8               | ₹25            |
| 12–21 km | ₹43       | ₹38.7               | ₹35            |
| 21–32 km | ₹54       | ₹48.6               | ₹40            |
| >32 km   | ₹64       | ₹57.6               | ₹50            |

---

## 🌐 API Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/` | GET | Serves the web dashboard |
| `/api/stations` | GET | Returns all stations as JSON |
| `/api/route?from=ID&to=ID&mode=MODE&sunday=BOOL` | GET | Computes optimal route |

### Route API Parameters
- `from` — Source station ID (e.g., `rajiv_chowk`)
- `to` — Destination station ID (e.g., `huda_city_centre`)
- `mode` — `SHORTEST_DISTANCE` or `MINIMUM_INTERCHANGES`
- `sunday` — `true` for Sunday/holiday fares

---

## 🎯 Key Features

- ✅ **Dijkstra's Algorithm** with PriorityQueue (Min-Heap)
- ✅ **Two Optimization Modes**: Shortest Distance & Minimum Interchanges
- ✅ **~120 Stations** across 6 metro lines with realistic distances
- ✅ **11 Interchange Stations** with transfer detection
- ✅ **Official DMRC Fare Slabs** (Token, Smart Card, Sunday)
- ✅ **Algorithm Telemetry**: Nodes visited, relaxations, execution time
- ✅ **Dark-Mode Dashboard** with Tailwind CSS
- ✅ **Visual Route Timeline** with collapsible intermediate stops
- ✅ **Zero Dependencies**: Pure JDK 17+ standard library

---

## 📝 License

Built for academic purposes — PBL (Project-Based Learning) demonstration.
