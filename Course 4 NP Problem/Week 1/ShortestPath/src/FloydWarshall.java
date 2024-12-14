import java.util.*;

public class FloydWarshall {
    private static int[][] convertListToMatrix(List<Edge> list, int vertices) {
        int[][] res = new int[vertices][vertices];
        for(int i = 0; i < vertices; i++){
            for(int j = 0; j < vertices; j++){
                if(i == j){
                    res[i][j] = 0;
                }else{
                    res[i][j] = Integer.MAX_VALUE;
                }
            }
        }
        for(Edge edge : list){
            res[edge.from][edge.to] = edge.weight;
        }
        return res;
    }

    public static int[][] floydWarshall(Graph graph) {
        int v = graph.vertices;
        int[][] dist = convertListToMatrix(graph.edges, v);

        for (int k = 0; k < v; k++) {
            for (int i = 0; i < v; i++) {
                for (int j = 0; j < v; j++) {
                    if (dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE) {
                        dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                    }
                }
            }
        }
        return dist;
    }
}
