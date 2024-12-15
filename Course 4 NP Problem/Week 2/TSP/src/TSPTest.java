import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TSPTest {

    /**
     * Test Case 1: Single city TSP.
     * There is only one city, so the shortest path is 0 since there are no other cities to visit.
     */
    @Test
    public void testSingleCity() {
        double[][] distanceMatrix = {{0}};
        long result = TSP.findShortestPath(distanceMatrix);
        assertEquals(0, result, "A single city should have a shortest path of 0.");
    }

    /**
     * Test Case 2: Two cities.
     * With two cities, the salesman just goes to the second city and returns, so the result should be twice the distance.
     */
    @Test
    public void testTwoCities() {
        double[][] distanceMatrix = {
            {0, 10},
            {10, 0}
        };
        long result = TSP.findShortestPath(distanceMatrix);
        assertEquals(20, result, "The shortest path for two cities should be twice the distance between them.");
    }

    /**
     * Test Case 3: Three cities forming a triangle.
     * The optimal path should be the perimeter of the triangle.
     */
    @Test
    public void testThreeCities() {
        double[][] distanceMatrix = {
            {0, 10, 15},
            {10, 0, 20},
            {15, 20, 0}
        };
        long result = TSP.findShortestPath(distanceMatrix);
        assertEquals(45, result, "The shortest path for three cities forming a triangle should be the perimeter of the triangle.");
    }

    /**
     * Test Case 4: Four cities in a square (clockwise traversal).
     * The optimal path is to follow the perimeter of the square.
     */
    @Test
    public void testFourCitiesSquare() {
        double[][] distanceMatrix = {
            {0, 10, 20, 10},
            {10, 0, 10, 20},
            {20, 10, 0, 10},
            {10, 20, 10, 0}
        };
        long result = TSP.findShortestPath(distanceMatrix);
        assertEquals(40, result, "The shortest path for four cities forming a square should be the perimeter of the square.");
    }

    /**
     * Test Case 5: Five cities (complex graph)
     * The optimal path is calculated by the dynamic programming TSP algorithm.
     */
    @Test
    public void testFiveCities() {
        double[][] distanceMatrix = {
            {0, 2, 9, 10, 7},
            {1, 0, 6, 4, 3},
            {9, 6, 0, 8, 5},
            {10, 4, 8, 0, 2},
            {7, 3, 5, 2, 0}
        };
        long result = TSP.findShortestPath(distanceMatrix);
        assertTrue(result > 0, "The path should be positive.");
        System.out.println("Shortest path for five cities: " + result);
    }

    /**
     * Test Case 6: Edge Case - No Cities
     * If there are no cities, the function should return 0 or handle it gracefully.
     */
    @Test
    public void testNoCities() {
        double[][] distanceMatrix = {};
        Exception exception = assertThrows(Exception.class, () -> TSP.findShortestPath(distanceMatrix));
        assertNotNull(exception, "An exception should be thrown for an empty distance matrix.");
    }

    /**
     * Test Case 7: Edge Case - Large input
     * Test with a larger number of cities to ensure the algorithm handles bigger inputs.
     * This case will only work if the heap size is large enough to run the algorithm.
     */
    @Test
    public void testLargeInput() {
        int n = 10; // For larger inputs, increase n, but note it might cause memory issues.
        double[][] distanceMatrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    distanceMatrix[i][j] = Math.random() * 100; // Random distance between 0 and 100
                } else {
                    distanceMatrix[i][j] = 0; // Distance to itself is 0
                }
            }
        }
        assertDoesNotThrow(() -> TSP.findShortestPath(distanceMatrix), "The algorithm should handle larger inputs without throwing exceptions.");
    }
}
