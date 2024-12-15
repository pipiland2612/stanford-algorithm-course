import java.util.*;
import java.io.*;

public class TSP {

    public static long findShortestPath(double[][] dist) {
        int n = dist.length;
        int MAX_MASK = 1 << n;
        double[][] dp = new double[MAX_MASK][n];
        for (double[] row : dp) {
            Arrays.fill(row, Double.POSITIVE_INFINITY);
        }

        dp[1][0] = 0; // Starting at city 0 with only city 0 visited

        for (int mask = 1; mask < MAX_MASK; mask++) {
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) == 0) continue; // City i is not in the current subset

                for (int j = 0; j < n; j++) {
                    if (i == j || (mask & (1 << j)) == 0) continue; // City j is not in the subset or it's the same as i

                    dp[mask][i] = Math.min(dp[mask][i], dp[mask ^ (1 << i)][j] + dist[j][i]);
                }
            }
        }

        // Calculate the minimum cost to return to the start city (city 0) from any other city
        double minCost = Double.POSITIVE_INFINITY;
        int finalMask = (1 << n) - 1; // All cities visited
        for (int i = 1; i < n; i++) {
            minCost = Math.min(minCost, dp[finalMask][i] + dist[i][0]);
        }

        return (long) Math.floor(minCost);
    }

    public static double[][] readInputFile(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        int n = Integer.parseInt(br.readLine().trim());
        double[][] points = new double[n][2];

        for (int i = 0; i < n; i++) {
            String[] parts = br.readLine().split("\\s+");
            points[i][0] = Double.parseDouble(parts[0]); // x-coordinate
            points[i][1] = Double.parseDouble(parts[1]); // y-coordinate
        }

        double[][] dist = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dist[i][j] = euclideanDistance(points[i], points[j]);
            }
        }

        return dist;
    }


    public static double euclideanDistance(double[] p1, double[] p2) {
        double dx = p1[0] - p2[0];
        double dy = p1[1] - p2[1];
        return Math.sqrt(dx * dx + dy * dy);
    }


    public static void main(String[] args) {
        String filePath = args[0];
        try {
            double[][] dist = readInputFile(filePath);
            long result = findShortestPath(dist);
            System.out.println("The minimum cost of the traveling salesman tour is: " + result);
        } catch (IOException e) {
            System.err.println("Error reading the input file: " + e.getMessage());
        }
    }
}
