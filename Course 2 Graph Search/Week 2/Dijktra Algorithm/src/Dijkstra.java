import java.util.*;
import java.io.*;

class Graph {
    private final int V;
    private final Map<Integer, List<Edge>> adjList;

    public Graph(int V) {
        this.V = V;
        adjList = new HashMap<>();
        for (int i = 1; i <= V; i++) {
            adjList.put(i, new ArrayList<>());
        }
    }

    public void addEdge(int src, int dest, int weight) {
        adjList.get(src).add(new Edge(dest, weight));
        adjList.get(dest).add(new Edge(src, weight));
    }

    public void dijkstra(int start, int[] dist) {
        PriorityQueue<Node> minHeap = new PriorityQueue<>(Comparator.comparingInt(value -> value.dist));
        minHeap.add(new Node(start, 0));

        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;

        while (!minHeap.isEmpty()) {
            Node node = minHeap.poll();
            int u = node.node;

            for (Edge edge : adjList.get(u)) {
                int v = edge.dest;
                int w = edge.weight;
                if (dist[v] > dist[u] + w) {
                    dist[v] = dist[u] + w;
                    minHeap.add(new Node(v, dist[v]));
                }
            }
        }
    }

    static class Edge {
        int dest, weight;

        public Edge(int dest, int weight) {
            this.dest = dest;
            this.weight = weight;
        }
    }

    static class Node {
        int node, dist;

        public Node(int node, int dist) {
            this.node = node;
            this.dist = dist;
        }
    }
}

public class Dijkstra {
    public static void main(String[] args) throws IOException {

        Graph graph = new Graph(200);

        try (BufferedReader br = new BufferedReader(new FileReader("/Users/batman/Desktop/Stanford Algorithm/Course 2 Graph Search/Week 2/Dijktra Algorithm/src/Dijkstra.txt"))) {
            String line;
            int vertex = 1;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s+");
                for (int i = 1; i < parts.length; i++) {
                    String[] edge = parts[i].split(",");
                    int dest = Integer.parseInt(edge[0]);
                    int weight = Integer.parseInt(edge[1]);
                    graph.addEdge(vertex, dest, weight);
                }
                vertex++;
            }
        }


        int[] dist = new int[201];

        graph.dijkstra(1, dist);

        int[] verticesToCheck = {7, 37, 59, 82, 99, 115, 133, 165, 188, 197};

        StringBuilder result = new StringBuilder();
        for (int v : verticesToCheck) {
            if (dist[v] == Integer.MAX_VALUE) {
                result.append("1000000");
            } else {
                result.append(dist[v]);
            }
            result.append(",");
        }

        result.deleteCharAt(result.length() - 1);
        System.out.println(result.toString());
    }
}
