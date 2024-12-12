import java.io.File;
import java.io.IOException;
import java.util.*;

class Edge {
    int from, to;
    int weight;

    public Edge(int from, int to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
}

class UnionFind {
    int[] parent;
    int[] rank;

    public UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    public int find(int x) {
        if (x != parent[x]) {
            parent[x] = find(parent[x]); // Path compression
        }
        return parent[x];
    }

    public void union(int x, int y) {
        int xRoot = find(x);
        int yRoot = find(y);
        if (xRoot == yRoot) return;

        // Union by rank
        if (rank[xRoot] < rank[yRoot]) {
            parent[xRoot] = yRoot;
        } else if (rank[xRoot] > rank[yRoot]) {
            parent[yRoot] = xRoot;
        } else {
            parent[yRoot] = xRoot;
            rank[xRoot]++;
        }
    }

    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }

    public int countLeaders() {
        Set<Integer> leaders = new HashSet<>();
        for (int i = 0; i < parent.length; i++) {
            leaders.add(find(i));
        }
        return leaders.size();
    }
}

public class Cluster {
    public static int maximumSpacing(int vertices, List<Edge> edges, int k) {
        edges.sort(Comparator.comparingInt(edge -> edge.weight));
        UnionFind uf = new UnionFind(vertices);

        for (Edge edge : edges) {
            if (!uf.connected(edge.from, edge.to)) {
                uf.union(edge.from, edge.to);
            }
            if (uf.countLeaders() <= k) {
                return edge.weight;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        String inputFile = "/Users/batman/Desktop/Stanford Algorithm/Course 3 Greedy Algorithm/Week 2/ClusteringAlgorithm/src/test.txt";

        try (Scanner scanner = new Scanner(new File(inputFile))) {
            if (!scanner.hasNextInt()) {
                System.err.println("Invalid input file format.");
                return;
            }

            int vertices = scanner.nextInt();
            List<Edge> edges = new ArrayList<>();

            while (scanner.hasNextInt()) {
                if (scanner.hasNextInt()) {
                    int from = scanner.nextInt() - 1;
                    int to = scanner.nextInt() - 1;
                    int weight = scanner.nextInt();
                    edges.add(new Edge(from, to, weight));
                }
            }

            int k = 4;
            int maxSpacing = maximumSpacing(vertices, edges, k);
            System.out.println("Maximum Spacing: " + maxSpacing);

        } catch (IOException e) {
            System.err.println("Error reading the input file: " + e.getMessage());
        }
    }
}
