import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class KargerMinCut {

    static class Graph {
        private final Map<Integer, List<Integer>> adjacencyList;

        public Graph() {
            this.adjacencyList = new HashMap<>();
        }

        public void addEdge(int vertex, int adjacentVertex) {
            this.adjacencyList.putIfAbsent(vertex, new ArrayList<>());
            this.adjacencyList.get(vertex).add(adjacentVertex);
        }

        public void addVertex(int vertex) {
            this.adjacencyList.putIfAbsent(vertex, new ArrayList<>());
        }

        public List<Integer> getEdges(int vertex) {
            return this.adjacencyList.getOrDefault(vertex, new ArrayList<>());
        }

        public Set<Integer> getVertices() {
            return this.adjacencyList.keySet();
        }

        public void contract(int vertexA, int vertexB) {
            List<Integer> edgesA = this.getEdges(vertexA);
            List<Integer> edgesB = this.getEdges(vertexB);
            edgesA.addAll(edgesB);
            for (int vertex : edgesB) {
                List<Integer> edges = this.getEdges(vertex);
                for (int i = 0; i < edges.size(); i++) {
                    if (edges.get(i) == vertexB) {
                        edges.set(i, vertexA);
                    }
                }
            }
            edgesA.removeIf(vertex -> vertex == vertexA);
            this.adjacencyList.remove(vertexB);
        }

        public int getRandomEdge(Random random) {
            List<Integer> vertices = new ArrayList<>(this.adjacencyList.keySet());
            int vertexA = vertices.get(random.nextInt(vertices.size()));
            List<Integer> edges = this.getEdges(vertexA);
            int vertexB = edges.get(random.nextInt(edges.size()));
            return vertexA * 1000 + vertexB;
        }

        public int getNumberOfEdges() {
            int count = 0;
            for (List<Integer> edges : this.adjacencyList.values()) {
                count += edges.size();
            }
            return count / 2;
        }

        public Graph deepCopy() {
            Graph newGraph = new Graph();
            for (Map.Entry<Integer, List<Integer>> entry : this.adjacencyList.entrySet()) {
                newGraph.adjacencyList.put(entry.getKey(), new ArrayList<>(entry.getValue()));
            }
            return newGraph;
        }

        public int getVertexCount() {
            return this.adjacencyList.size();
        }

        @Override
        public String toString() {
            return "Graph: " + adjacencyList.toString();
        }
    }

    public static Graph loadGraph(String filePath) throws IOException {
        Graph graph = new Graph();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                int vertex = Integer.parseInt(parts[0]);
                graph.addVertex(vertex);
                for (int i = 1; i < parts.length; i++) {
                    int adjacentVertex = Integer.parseInt(parts[i]);
                    graph.addEdge(vertex, adjacentVertex);
                }
            }
        }
        return graph;
    }

    public static int runKargerMinCut(Graph graph) {
        Random random = new Random();
        while (graph.getVertexCount() > 2) {
            int edge = graph.getRandomEdge(random);
            int vertexA = edge / 1000;
            int vertexB = edge % 1000;
            graph.contract(vertexA, vertexB);
        }
        return graph.getNumberOfEdges();
    }

    public static int getMinCut(String filePath, int iterations) throws IOException {
        int minCut = Integer.MAX_VALUE;
        for (int i = 0; i < iterations; i++) {
            Graph graph = loadGraph(filePath).deepCopy();
            int cut = runKargerMinCut(graph);
            minCut = Math.min(minCut, cut);
            System.out.println("Iteration " + (i + 1) + ": Current min cut = " + cut);
        }
        return minCut;
    }

    public static void main(String[] args) {
        String filePath = "/Users/batman/Desktop/Java DSA/SortingAlgo/src/kagerMinCut.txt";
        int iterations = 100;
        try {
            int minCut = getMinCut(filePath, iterations);
            System.out.println("Minimum cut found: " + minCut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
