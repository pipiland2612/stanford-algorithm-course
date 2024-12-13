import java.io.File;
import java.io.IOException;
import java.util.*;

class Edge {
    int from;
    int to;
    int weight;
    public Edge(int from, int to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
}
class UnionFind{
    int[] parent;
    int[] rank;
    public UnionFind(int n){
        parent = new int[n];
        rank = new int[n];
        for(int i=0;i<n;i++){
            parent[i] = i;
            rank[i] = 0;
        }
    }
    public int find(int x){
        if(parent[x] != x){
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }
    public void union(int x, int y){
        int rootX = find(x);
        int rootY = find(y);
        if(rootX != rootY){
            if(rank[rootX] < rank[rootY]){
                parent[rootX] = rootY;
            }else if(rank[rootX] > rank[rootY]){
                parent[rootY] = rootX;
            }else{
                parent[rootY] = rootX;
                rank[rootX] += 1;
            }
        }
    }
    public boolean connected(int x, int y){
        return find(x) == find(y);
    }
}

public class Cluster {
    public static int maximumSpacing(int vertices, List<Edge> edges, int k) {
        edges.sort(Comparator.comparingInt(edge -> edge.weight));
        UnionFind uf = new UnionFind(vertices);
        int clusterCount = vertices;
        for(Edge edge : edges){
            if(uf.connected(edge.from, edge.to))continue;
            uf.union(edge.from, edge.to);
            clusterCount--;
            if(clusterCount == k){
                break;
            }
        }
        for(Edge edge : edges){
            if(uf.connected(edge.from, edge.to))continue;
            return edge.weight;
        }
        return -1;
    }

    public static void main(String[] args) {
        String inputFile = "/Users/batman/Desktop/Stanford Algorithm/Course 3 Greedy Algorithm/Week 2/ClusteringAlgorithm/src/clustering1.txt";

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
