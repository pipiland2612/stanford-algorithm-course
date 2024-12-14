import java.util.Arrays;

public class BellmanFord {
    public static int[] bellmanShortest(Graph graph, int source) {
        int[] dist = new int[graph.vertices];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        for (int i = 0; i < graph.vertices; i++) {
            for (Edge edge : graph.edges) {
                int from = edge.from;
                int to = edge.to;
                int weight = edge.weight;

                if (dist[from] != Integer.MAX_VALUE && dist[from] + weight < dist[to]) {
                    if(i == graph.vertices - 1) throw new RuntimeException("Negative Cycle Found");
                    dist[to] = dist[from] + weight;
                }
            }
        }
        return dist;
    }
}
