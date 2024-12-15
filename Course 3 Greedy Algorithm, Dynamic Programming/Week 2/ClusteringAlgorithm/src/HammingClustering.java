import java.io.File;
import java.io.IOException;
import java.util.*;

class UnionFinds {
    private final Map<Integer, Integer> parent;
    private final Map<Integer, Integer> rank;

    public UnionFinds(Set<Integer> vertices) {
        parent = new HashMap<>();
        rank = new HashMap<>();
        for (int vertex : vertices) {
            parent.put(vertex, vertex);
            rank.put(vertex, 0);
        }
    }

    // Find with path compression
    public int find(int x) {
        if (parent.get(x) != x) {
            parent.put(x, find(parent.get(x))); // Path compression
        }
        return parent.get(x);
    }

    // Union by rank
    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX != rootY) {
            if (rank.get(rootX) < rank.get(rootY)) {
                parent.put(rootX, rootY);
            } else if (rank.get(rootX) > rank.get(rootY)) {
                parent.put(rootY, rootX);
            } else {
                parent.put(rootY, rootX);
                rank.put(rootX, rank.get(rootX) + 1);
            }
        }
    }

    public int countClusters() {
        Set<Integer> uniqueClusters = new HashSet<>();
        for (int node : parent.keySet()) {
            uniqueClusters.add(find(node));
        }
        return uniqueClusters.size();
    }
}

public class HammingClustering {
    public static void main(String[] args) {
        String inputFilePath = "/Users/batman/Desktop/Stanford Algorithm/Course 3 Greedy Algorithm/Week 2/ClusteringAlgorithm/src/clustering_big.txt";

        try {
            List<Integer> vertices = new ArrayList<>();
            int numBits;
            try (Scanner scanner = new Scanner(new File(inputFilePath))) {
                int numNodes = scanner.nextInt();
                numBits = scanner.nextInt();
                scanner.nextLine();

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().trim();
                    if (!line.isEmpty()) {
                        int label = binaryStringToInt(line);
                        vertices.add(label);
                    }
                }
            }

            // Perform clustering
            int numClusters = hammingClustering(vertices, numBits);
            System.out.println("Number of clusters with spacing at least 3: " + numClusters);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error reading the input file.");
        }
    }

    public static int hammingClustering(List<Integer> vertices, int numBits) {
        // Step 1: Remove duplicates for efficiency
        Set<Integer> uniqueVertices = new HashSet<>(vertices);

        // Step 2: Initialize Union-Find structure with unique vertices
        UnionFinds unionFind = new UnionFinds(uniqueVertices);

        // Step 3: Find neighbors with Hamming distance = 1 and 2
        for (int vertex : uniqueVertices) {
            // Hamming Distance = 1: Flip one bit
            for (int i = 0; i < numBits; i++) {
                int neighbor = vertex ^ (1 << i); // Flip the i-th bit
                if (uniqueVertices.contains(neighbor)) {
                    unionFind.union(vertex, neighbor);
                }
            }

            // Hamming Distance = 2: Flip two bits
            for (int i = 0; i < numBits; i++) {
                for (int j = i + 1; j < numBits; j++) {
                    int neighbor = vertex ^ (1 << i) ^ (1 << j); // Flip the i-th and j-th bits
                    if (uniqueVertices.contains(neighbor)) {
                        unionFind.union(vertex, neighbor);
                    }
                }
            }
        }

        // Step 4: Count and return the number of clusters
        return unionFind.countClusters();
    }

    // Utility: Convert binary string (with spaces) to an integer
    private static int binaryStringToInt(String binaryString) {
        String[] parts = binaryString.split(" ");
        int result = 0;
        for (String part : parts) {
            result = (result << 1) | Integer.parseInt(part);
        }
        return result;
    }
}