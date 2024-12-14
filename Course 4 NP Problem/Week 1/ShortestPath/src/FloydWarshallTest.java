import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class FloydWarshallTest {

    @Test
    void testGraphWithoutNegativeWeights() {
        // Create a graph with no negative weights
        Graph graph = new Graph(4);
        graph.addEdge(0, 1, 3);
        graph.addEdge(0, 2, 1);
        graph.addEdge(1, 2, 7);
        graph.addEdge(2, 3, 2);
        graph.addEdge(0, 3, 10);

        int[][] shortestDistances = FloydWarshall.floydWarshall(graph);

        // Expected output matrix
        int[][] expected = {
            {0, 3, 1, 3},
            {Integer.MAX_VALUE, 0, 7, 9},
            {Integer.MAX_VALUE, Integer.MAX_VALUE, 0, 2},
            {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 0}
        };

        // Validate the shortest distance matrix
        assertArrayEquals(expected, shortestDistances);
    }

    @Test
    void testDisconnectedGraph() {
        // Create a graph where vertices are disconnected
        Graph graph = new Graph(4);
        graph.addEdge(0, 1, 4);
        graph.addEdge(1, 2, 6);

        int[][] shortestDistances = FloydWarshall.floydWarshall(graph);

        // Expected output matrix
        int[][] expected = {
            {0, 4, 10, Integer.MAX_VALUE},
            {Integer.MAX_VALUE, 0, 6, Integer.MAX_VALUE},
            {Integer.MAX_VALUE, Integer.MAX_VALUE, 0, Integer.MAX_VALUE},
            {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 0}
        };

        // Validate the shortest distance matrix
        assertArrayEquals(expected, shortestDistances);
    }

    @Test
    void testGraphWithNegativeWeights() {
        // Create a graph with some negative-weight edges
        Graph graph = new Graph(3);
        graph.addEdge(0, 1, 2);
        graph.addEdge(1, 2, -5);
        graph.addEdge(0, 2, 4);

        int[][] shortestDistances = FloydWarshall.floydWarshall(graph);

        // Expected output matrix
        int[][] expected = {
            {0, 2, -3},
            {Integer.MAX_VALUE, 0, -5},
            {Integer.MAX_VALUE, Integer.MAX_VALUE, 0}
        };

        // Validate the shortest distance matrix
        assertArrayEquals(expected, shortestDistances);
    }

    @Test
    void testGraphWithSelfLoops() {
        // Create a graph where there are self-loops
        Graph graph = new Graph(3);
        graph.addEdge(0, 0, 0); // Self-loop
        graph.addEdge(0, 1, 4);
        graph.addEdge(1, 1, 0); // Self-loop
        graph.addEdge(1, 2, 3);
        graph.addEdge(2, 2, 0); // Self-loop

        int[][] shortestDistances = FloydWarshall.floydWarshall(graph);

        // Expected output matrix
        int[][] expected = {
            {0, 4, 7},
            {Integer.MAX_VALUE, 0, 3},
            {Integer.MAX_VALUE, Integer.MAX_VALUE, 0}
        };

        // Validate the shortest distance matrix
        assertArrayEquals(expected, shortestDistances);
    }

    @Test
    void testEmptyGraph() {
        // Test an empty graph (no vertices and no edges)
        Graph graph = new Graph(0);

        int[][] shortestDistances = FloydWarshall.floydWarshall(graph);

        // Expected output matrix is empty
        int[][] expected = {};

        assertArrayEquals(expected, shortestDistances);
    }

    @Test
    void testAllDisconnectedGraph() {
        // Create a graph where all nodes are disconnected
        Graph graph = new Graph(4);

        int[][] shortestDistances = FloydWarshall.floydWarshall(graph);

        // Expected output matrix
        int[][] expected = {
            {0, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE},
            {Integer.MAX_VALUE, 0, Integer.MAX_VALUE, Integer.MAX_VALUE},
            {Integer.MAX_VALUE, Integer.MAX_VALUE, 0, Integer.MAX_VALUE},
            {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 0}
        };

        // Validate the shortest distance matrix
        assertArrayEquals(expected, shortestDistances);
    }
}