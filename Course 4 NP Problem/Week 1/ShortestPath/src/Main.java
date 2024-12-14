import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) {

        int shortestShortestPath = Integer.MAX_VALUE;
        boolean hasValidGraph = false;

        String[] graphFiles = {"g1.txt", "g2.txt", "g3.txt"};

        for (int graphIdx = 0; graphIdx < 3; graphIdx++) {
            String filePath = "/Users/batman/Desktop/Stanford Algorithm/Course 4 NP Problem/Week 1/ShortestPath/" + graphFiles[graphIdx];

            System.out.println("Processing graph from file: " + filePath);

            try (Scanner scanner = new Scanner(new File(filePath))) {
                // Input: Read the number of vertices and edges
                int vertices = scanner.nextInt();
                int edges = scanner.nextInt();

                // Create the graph
                Graph graph = new Graph(vertices);

                for (int i = 0; i < edges; i++) {
                    int from = scanner.nextInt();
                    int to = scanner.nextInt();
                    int weight = scanner.nextInt();
                    graph.addEdge(from - 1, to - 1, weight); // Use zero-based indexing
                }

                // Apply Johnson's algorithm
                int[][] distances = Johnson.johnson(graph);
                if (distances == null) {
                    System.out.println("Graph from file " + graphFiles[graphIdx] + " has a negative cost cycle.");
                } else {
                    // Calculate the shortest path in this graph
                    int graphSSP = Integer.MAX_VALUE;
                    for (int i = 0; i < vertices; i++) {
                        for (int j = 0; j < vertices; j++) {
                            if (i != j && distances[i][j] != Integer.MAX_VALUE) {
                                graphSSP = Math.min(graphSSP, distances[i][j]);
                            }
                        }
                    }

                    System.out.println("Shortest path for graph from file " + graphFiles[graphIdx] + ": " + graphSSP);
                    shortestShortestPath = Math.min(shortestShortestPath, graphSSP);
                    hasValidGraph = true;
                }
            } catch (FileNotFoundException e) {
                System.out.println("The file was not found: " + filePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Final output
        if (!hasValidGraph) {
            System.out.println("NULL");
        } else {
            System.out.println("Shortest shortest path across all graphs: " + shortestShortestPath);
        }
    }
}