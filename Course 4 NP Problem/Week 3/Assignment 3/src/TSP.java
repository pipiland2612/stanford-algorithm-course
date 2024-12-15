import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TSP {
    private static double squaredEuclideanDistance(double[] city1, double[] city2) {
        double dx = city1[0] - city2[0];
        double dy = city1[1] - city2[1];
        return dx * dx + dy * dy;
    }

    public static double nearestNeighbor(double[][] cities, int n) {
        boolean[] visited = new boolean[n];
        int currentCity = 0;
        visited[currentCity] = true;
        double totalDistance = 0.0;

        for (int i = 1; i < n; i++) {
            int nearestCity = -1;
            double minDist = Double.MAX_VALUE;

            for (int j = 1; j < n; j++) {
                if (!visited[j]) {
                    double dist = squaredEuclideanDistance(cities[currentCity], cities[j]);
                    if (dist < minDist) {
                        minDist = dist;
                        nearestCity = j;
                    }
                }
            }

            // Move to the nearest city
            visited[nearestCity] = true;
            totalDistance += Math.sqrt(minDist);
            currentCity = nearestCity;
        }

        // Return to the first city
        totalDistance += Math.sqrt(squaredEuclideanDistance(cities[currentCity], cities[0]));

        return totalDistance;
    }

    public static void main(String[] args) {
        String inputFile = "/Users/batman/Desktop/Stanford Algorithm/Course 4 NP Problem/Week 3/Assignment 3/nn.txt";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            int n = Integer.parseInt(reader.readLine().trim());

            double[][] cities = new double[n][2];
            for (int i = 0; i < n; i++) {
                String[] line = reader.readLine().trim().split("\\s+");
                double x = Double.parseDouble(line[1]);
                double y = Double.parseDouble(line[2]);
                cities[i] = new double[]{x, y};
            }
            reader.close();

            double result = nearestNeighbor(cities, n);

            System.out.println(result);

        } catch (IOException e) {
            System.out.println("Error reading the input file: " + e.getMessage());
        }
    }
}
