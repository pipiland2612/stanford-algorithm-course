import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DijkstraTest {

    private Graph graph;

    @BeforeEach
    void setUp() {
        graph = new Graph(6);
        graph.addEdge(0, 1, 2);
        graph.addEdge(0, 2, 4);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 7);
        graph.addEdge(2, 3, 3);
        graph.addEdge(3, 4, 1);
        graph.addEdge(4, 5, 2);
        graph.addEdge(2, 5, 10);
    }

    // ✅ Test 1: Standard graph, shortest paths from node 0
    @Test
    void testDijkstraShortestPathFromStartNode() {
        int[] distances = Dijkstra.dijkstraShortest(graph, 0);
        int[] expectedDistances = {0, 2, 3, 6, 7, 9};
        assertArrayEquals(expectedDistances, distances, "The shortest path distances should match");
    }

    // ✅ Test 2: Dijkstra from a different start node
    @Test
    void testDijkstraShortestPathFromDifferentStartNode() {
        int[] distances = Dijkstra.dijkstraShortest(graph, 3);
        int[] expectedDistances = {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 0, 1, 3};
        assertArrayEquals(expectedDistances, distances, "The shortest path distances should match");
    }

    // ✅ Test 3: Disconnected graph
    @Test
    void testDisconnectedGraph() {
        Graph disconnectedGraph = new Graph(4);
        disconnectedGraph.addEdge(0, 1, 2);
        disconnectedGraph.addEdge(2, 3, 5);
        int[] distances = Dijkstra.dijkstraShortest(disconnectedGraph, 0);
        int[] expectedDistances = {0, 2, Integer.MAX_VALUE, Integer.MAX_VALUE};
        assertArrayEquals(expectedDistances, distances, "The shortest path distances for a disconnected graph should be correct");
    }

    // ✅ Test 4: Single node graph
    @Test
    void testSingleNodeGraph() {
        Graph singleNodeGraph = new Graph(1);
        int[] distances = Dijkstra.dijkstraShortest(singleNodeGraph, 0);
        int[] expectedDistances = {0};
        assertArrayEquals(expectedDistances, distances, "The shortest path in a single-node graph should be 0 to itself");
    }

    // ✅ Test 5: Two connected nodes
    @Test
    void testTwoNodesDirectConnection() {
        Graph twoNodeGraph = new Graph(2);
        twoNodeGraph.addEdge(0, 1, 7);
        int[] distances = Dijkstra.dijkstraShortest(twoNodeGraph, 0);
        int[] expectedDistances = {0, 7};
        assertArrayEquals(expectedDistances, distances, "The shortest path in a two-node graph should be direct");
    }

    // ✅ Test 6: No edges graph
    @Test
    void testGraphWithNoEdges() {
        Graph noEdgesGraph = new Graph(4);
        int[] distances = Dijkstra.dijkstraShortest(noEdgesGraph, 0);
        int[] expectedDistances = {0, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE};
        assertArrayEquals(expectedDistances, distances, "Distances should be infinity for unconnected nodes");
    }

    // ✅ Test 7: Negative weights
    @Test
    void testGraphWithNegativeWeights() {
        Graph negativeWeightGraph = new Graph(3);
        negativeWeightGraph.addEdge(0, 1, -5); // Negative weight
        negativeWeightGraph.addEdge(1, 2, 3);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Dijkstra.dijkstraShortest(negativeWeightGraph, 0);
        });
        assertEquals("Negative edge weight detected", exception.getMessage(),
            "Dijkstra should throw an error for negative edge weight");
    }

    // ✅ Test 8: Large graph performance
    @Test
    void testLargeGraphPerformance() {
        int nodes = 1000;
        Graph largeGraph = new Graph(nodes);
        for (int i = 0; i < nodes - 1; i++) {
            largeGraph.addEdge(i, i + 1, 1);
        }
        int[] distances = Dijkstra.dijkstraShortest(largeGraph, 0);
        for (int i = 0; i < nodes; i++) {
            assertEquals(i, distances[i], "Distance from node 0 to node " + i + " should be " + i);
        }
    }
}
