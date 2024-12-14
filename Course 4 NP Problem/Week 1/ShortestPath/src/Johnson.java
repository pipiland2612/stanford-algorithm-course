public class Johnson {
    public static int[][] johnson(Graph graph) {
        //Step 1: Create vertex S that connects to other vertex
        int v = graph.vertices;
        Graph g = new Graph(v + 1);
        for(int i = 0; i < v; i++){
            for(Edge edge : graph.adjacencyList.get(i)){
                g.addEdge(edge.from, edge.to, edge.weight);
            }
            g.addEdge(v, i, 0);
        }
        //Step 2: Run Bellman Ford to compute constant weight, apply to the graph
        int[] bellford = BellmanFord.bellmanShortest(g, 0);
        for(int i = 0; i < v; i++){
            for(Edge edge : graph.adjacencyList.get(i)){
                edge.weight += bellford[edge.from] - bellford[edge.to];
            }
        }

        //Step 3: Run dijkstra on non-negative graph
        int[][] res = new int[v][v];
        for(int i = 0; i < v; i++){
            int[] dist = Dijkstra.dijkstraShortest(graph, i);
            for(int j = 0; j < v; j++){
                if (dist[j] == Integer.MAX_VALUE) {
                    res[i][j] = Integer.MAX_VALUE;
                } else {
                    // Trace back
                    res[i][j] = dist[j] + bellford[j] - bellford[i];
                }
            }
        }
        return res;

    }
}