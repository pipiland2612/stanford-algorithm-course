import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Dijkstra {
    public static int[] dijkstraShortest(Graph graph, int source) {
        int vertices = graph.vertices;

        int[] dist = new int[vertices];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        // int[] contains: the node, the min distance
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparing(a -> a[1]));
        minHeap.add(new int[]{source, 0});


        while (!minHeap.isEmpty()) {
            int[] node = minHeap.poll();
            int nodeIndex = node[0];

            for (Edge edge : graph.adjacencyList.get(nodeIndex)) {
                int to = edge.to;
                int weight = edge.weight;
                if (dist[nodeIndex] != Integer.MAX_VALUE && dist[to] > dist[nodeIndex] + weight) {
                    dist[to] = dist[nodeIndex] + weight;
                    minHeap.add(new int[]{to, dist[to]});
                }
            }
        }
        return dist;
    }
}
