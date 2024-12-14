import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BellmanFordTest {

    @Test
    void testSimpleGraph() {
        // Test for a simple graph with no negative weights
        Graph graph = new Graph(5);
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 1);
        graph.addEdge(2, 1, 2);
        graph.addEdge(1, 3, 1);
        graph.addEdge(3, 4, 3);

        BellmanFord bellmanFord = new BellmanFord();
        int[] distances = bellmanFord.bellmanShortest(graph, 0);

        // Assert the expected distances
        assertArrayEquals(new int[]{0, 3, 1, 4, 7}, distances);
    }

    @Test
    void testDisconnectedGraph() {
        // Test for a graph where some nodes are disconnected
        Graph graph = new Graph(5);
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 1);

        BellmanFord bellmanFord = new BellmanFord();
        int[] distances = bellmanFord.bellmanShortest(graph, 0);

        // Nodes 3 and 4 are disconnected, so their distances should remain Integer.MAX_VALUE
        assertTrue(distances[3] == Integer.MAX_VALUE);
        assertTrue(distances[4] == Integer.MAX_VALUE);

        // Assert calculated distances
        assertArrayEquals(new int[]{0, 4, 1, Integer.MAX_VALUE, Integer.MAX_VALUE}, distances);
    }

    @Test
    void testGraphWithNegativeWeights() {
        // Test for a graph with negative edge weights (but no negative cycle)
        Graph graph = new Graph(4);
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 5);
        graph.addEdge(2, 1, -6); // Negative weight edge
        graph.addEdge(1, 3, 3);

        BellmanFord bellmanFord = new BellmanFord();
        int[] distances = bellmanFord.bellmanShortest(graph, 0);

        // Assert calculated distances
        assertArrayEquals(new int[]{0, -1, 5, 2}, distances);
    }

    @Test
    void testGraphWithNegativeCycle() {
        // Test for a graph with a negative cycle
        Graph graph = new Graph(3);
        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 0, -3); // Creates a negative cycle

        BellmanFord bellmanFord = new BellmanFord();

        // Expect a RuntimeException due to negative cycle detection
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bellmanFord.bellmanShortest(graph, 0);
        });

        assertEquals("Negative Cycle Found", exception.getMessage());
    }

    @Test
    void testGraphWithSingleNode() {
        // Test for a graph with a single node (no edges)
        Graph graph = new Graph(1);

        BellmanFord bellmanFord = new BellmanFord();
        int[] distances = bellmanFord.bellmanShortest(graph, 0);

        // Assert the distances for a single node (0 distance to itself)
        assertArrayEquals(new int[]{0}, distances);
    }

    @Test
    void testGraphWithMultipleComponents() {
        // Test for a graph with multiple disconnected components
        Graph graph = new Graph(6);
        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 2);
        graph.addEdge(3, 4, -2);
        graph.addEdge(4, 5, 1);

        BellmanFord bellmanFord = new BellmanFord();
        int[] distances = bellmanFord.bellmanShortest(graph, 0);

        // Nodes 3, 4, and 5 are in a different component, so their distances should remain Integer.MAX_VALUE
        assertArrayEquals(new int[]{0, 1, 3, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE}, distances);
    }
}