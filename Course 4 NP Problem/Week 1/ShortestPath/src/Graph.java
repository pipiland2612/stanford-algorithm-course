import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    public int vertices;
    public Map<Integer, List<Edge>> adjacencyList;
    public List<Edge> edges;

    public Graph(int vertices) {
        this.vertices = vertices;
        this.adjacencyList = new HashMap<>();
        this.edges = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            adjacencyList.put(i, new ArrayList<>());
        }
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
        adjacencyList.get(edge.from).add(edge);
    }

    public void addEdge(int from, int to, int weight) {
        Edge edge = new Edge(from, to, weight);
        edges.add(edge);
        adjacencyList.get(edge.from).add(edge);
    }
}
