public class Johnson {
    public static int[][] johnson(Graph graph) {
        int v = graph.vertices;
        Graph g = new Graph(v + 1);
        for(int i = 0; i < v; i++){
            for(Edge edge : graph.adjacencyList.get(i)){
                g.addEdge(edge.from, edge.to, edge.weight);
            }
            g.addEdge(v, i, 0);
        }
        int[] bellford = BellmanFord.bellmanShortest(g, 0);
        for(int i = 0; i < v; i++){
            for(Edge edge : graph.adjacencyList.get(i)){
                edge.weight += bellford[edge.from] - bellford[edge.to];
            }
        }

        int[][] res = new int[v][v];
        for(int i = 0; i < v; i++){
            int[] dist = Dijkstra.dijkstraShortest(graph, i);
            for(int j = 0; j < v; j++){
                if (dist[j] == Integer.MAX_VALUE) {
                    res[i][j] = Integer.MAX_VALUE;
                } else {
                    res[i][j] = dist[j] + bellford[j] - bellford[i];
                }
            }
        }
        return res;

    }
}