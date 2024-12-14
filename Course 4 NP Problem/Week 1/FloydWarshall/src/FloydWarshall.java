import java.util.ArrayList;
import java.util.List;

class Edge {
    int src, dest, weight;
    Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }
}

class Graph{
    int vertices;
    List<Edge> edges;
    public Graph(int vertices){
        this.vertices = vertices;
        edges = new ArrayList<>();
    }
    public void addEdge(int src, int dest, int weight){
        edges.add(new Edge(src, dest, weight));
    }
}

public class FloydWarshall {
    private static final int INF = 9999999;

    public static int[][] convertGraphToMatrix(int vertices, List<Edge> edges){
        int[][] graph = new int[vertices][vertices];
        for(int i = 0; i < vertices; i++){
            for(int j = 0; j < vertices; j++){
                if(i == j){
                    graph[i][j] = 0;
                }else{
                    graph[i][j] = INF;
                }
            }
        }

        for(Edge edge : edges){
            graph[edge.src][edge.dest] = edge.weight;
        }
        return graph;
    }

    public static int[][] floydWarshall(Graph graph){
        int v = graph.vertices;
        List<Edge> edges = graph.edges;

        int[][] graphMatrix = convertGraphToMatrix(v, edges);
        int[][] dp = new int[v][v];

        for(int i = 0; i < v; i++){
            System.arraycopy(graphMatrix[i], 0, dp[i], 0, v);
        }

        for(int k = 0; k < v; k++){
            for(int i = 0; i < v; i++){
                for(int j = 0; j < v; j++){
                    dp[i][j] = Math.min(dp[i][j] ,dp[i][k] + dp[k][j]);
                }
            }
        }
        return dp;
    }
    public static void printMatrix(int[][] matrix){
        for (int[] ints : matrix) {
            for (int anInt : ints) {
                if (anInt != INF) {
                    System.out.print(anInt + " ");
                } else {
                    System.out.print("I ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Graph graph = new Graph(4);
        graph.addEdge(0, 1, 10);
        graph.addEdge(0, 2, 5);
        graph.addEdge(1, 2, 3);
        graph.addEdge(2, 3, 1);
        int[][] matrix = floydWarshall(graph);
        printMatrix(matrix);
    }
}
