import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class JohnsonTest {

    @Test
    void testJohnsonPositiveWeights() {
        // Graph with positive edge weights
        Graph graph = new Graph(4);
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 1);
        graph.addEdge(2, 1, 2);
        graph.addEdge(1, 3, 1);
        graph.addEdge(2, 3, 5);

        int[][] result = Johnson.johnson(graph);

        int[][] expected = {
            {0, 3, 1, 4},
            {Integer.MAX_VALUE, 0, Integer.MAX_VALUE, 1},
            {Integer.MAX_VALUE, 2, 0, 3},
            {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 0}
        };

        assertArrayEquals(expected, result);
    }

    @Test
    void testJohnsonNegativeWeights() {
        // Graph with negative edge weights (but no negative cycles)
        Graph graph = new Graph(4);
        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, -2);
        graph.addEdge(2, 3, 2);
        graph.addEdge(3, 1, 2);

        int[][] result = Johnson.johnson(graph);

        int[][] expected = {
            {0, 1, -1, 1},
            {Integer.MAX_VALUE, 0, -2, 0},
            {Integer.MAX_VALUE, 4, 0, 2},
            {Integer.MAX_VALUE, 2, 0, 0}
        };

        assertArrayEquals(expected, result);
    }

    @Test
    void testJohnsonDisconnectedGraph() {
        // Graph where some nodes are not reachable
        Graph graph = new Graph(4);
        graph.addEdge(0, 1, 3);
        graph.addEdge(2, 3, 1);

        int[][] result = Johnson.johnson(graph);

        int[][] expected = {
            {0, 3, Integer.MAX_VALUE, Integer.MAX_VALUE},
            {Integer.MAX_VALUE, 0, Integer.MAX_VALUE, Integer.MAX_VALUE},
            {Integer.MAX_VALUE, Integer.MAX_VALUE, 0, 1},
            {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 0}
        };

        assertArrayEquals(expected, result);
    }

    @Test
    void testJohnsonEmptyGraph() {
        // Empty graph with no vertices
        Graph graph = new Graph(0);

        int[][] result = Johnson.johnson(graph);

        int[][] expected = {}; // No vertices means an empty result matrix
        assertArrayEquals(expected, result);
    }

    @Test
    void testJohnsonCompleteCycleGraph() {
        // Graph where all vertices form a connected cycle
        Graph graph = new Graph(4);
        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 3, 1);
        graph.addEdge(3, 0, 1);

        int[][] result = Johnson.johnson(graph);

        int[][] expected = {
            {0, 1, 2, 3},
            {3, 0, 1, 2},
            {2, 3, 0, 1},
            {1, 2, 3, 0}
        };

        assertArrayEquals(expected, result);
    }
}