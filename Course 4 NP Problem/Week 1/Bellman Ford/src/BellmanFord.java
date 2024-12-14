import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Edge {
    int src, dest, weight;
    public Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }
}
class Graph {
    int vertices;
    List<Edge> edges;

    public Graph(int vertices) {
        this.vertices = vertices;
        edges = new ArrayList<>();
    }
    public void addEdge(int src, int dest, int weight) {
        edges.add(new Edge(src, dest, weight));
    }
}

public class BellmanFord {

    public static int[] bellmanFord(int src, int vertices, List<Edge> edges) {
        int[] dist = new int[vertices];
        int[] prev = new int[vertices];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);
        dist[src] = 0;

        for(int i = 0; i < vertices; i++){
            for(Edge edge : edges) {
                int from = edge.src, to = edge.dest, weight = edge.weight;
                if(dist[from] != Integer.MAX_VALUE && dist[from] + weight < dist[to]) {
                    //Negative cycle detected
                    if(i == vertices - 1)return new int[]{-1};

                    dist[to] = dist[from] + weight;
                    prev[to] = from;
                }
            }
        }
        System.out.println(Arrays.toString(prev));
        return dist;
    }

    public static void main(String[] args) {
        Graph graph = new Graph(4);
        graph.addEdge(0, 1, 10);
        graph.addEdge(0, 2, 5);
        graph.addEdge(1, 2, 3);
        graph.addEdge(2, 3, 1);
        int[] result = bellmanFord(0, graph.vertices, graph.edges);
        System.out.println(Arrays.toString(result));
    }
}