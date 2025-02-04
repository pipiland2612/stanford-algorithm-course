import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Edge{
    int start, dest, weight;

    public Edge(int start, int dest, int weight){
        this.start = start;
        this.dest = dest;
        this.weight = weight;
    }

    @Override
    public String toString(){
        return "(" + start + ", " + dest + ", " + weight + ")";
    }
}

class Graph{
    int vertices;
    List<Edge> edges;
    int[] parents, rank;

    public Graph(int vertices){
        this.vertices = vertices;
        edges = new ArrayList<>();

        parents = new int[vertices]; rank = new int[vertices];
        for (int i = 0; i < vertices; i++) {
            parents[i] = i;
            rank[i] = 0;
        }
    }
    public void addEdge(int start, int dest, int weight){
        Edge e = new Edge(start, dest, weight);
        edges.add(e);
    }

    public int find(int vertex){
        if(vertex < 0 || vertex >= vertices)
            throw new IllegalArgumentException("Vertex out of bounds: " + vertex);
        while (vertex != parents[vertex]){
            // Path compression
            parents[vertex] = parents[parents[vertex]];
            vertex = parents[vertex];
        }
        return vertex;
    }

    public void union(int p, int q){
        int rootP = find(p);
        int rootQ = find(q);

        if(rootP == rootQ)return;
        //Optimization: merge smaller tree to the larger one
        if(rank[rootP] < rank[rootQ]){
            parents[rootP] = rootQ;
        }else{
            parents[rootQ] = rootP;
            if(rank[rootP] == rank[rootQ])
                rank[rootP]++;
        }
    }

    public void kruskalMST(){

        edges.sort(Comparator.comparingInt(a -> a.weight));
        int index = 0;
        List<Edge> mst = new ArrayList<>();
        while (mst.size() < vertices - 1){
            Edge edge = edges.get(index++);
            int src = find(edge.start);
            int dest = find(edge.dest);

            if(src != dest){
                mst.add(edge);
                union(src, dest);
            }
        }
        System.out.println(mst);
    }
}

public class Kruskals {
    public static void main(String[] args) {
        Graph g = new Graph(6);
        g.addEdge(0, 1, 4); g.addEdge(0, 2, 3); g.addEdge(1, 2, 1); g.addEdge(1, 3, 2);
        g.addEdge(2, 4, 4); g.addEdge(3, 4, 5); g.addEdge(4, 5, 1);
        g.kruskalMST();
    }

}

