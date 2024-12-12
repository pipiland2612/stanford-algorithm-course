import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Edge {
    int vertex, weight;
    public Edge(int vertex, int weight){
        this.vertex = vertex;
        this.weight = weight;
    }
}
class Node {
    int vertex, minWeightIn;
    public Node(int vertex, int minWeightIn){
        this.vertex = vertex;
        this.minWeightIn = minWeightIn;
    }
}

public class PrimsAlgorithm {
    public static void MST(int numsNode, List<List<Edge>> graph){
        int total = 0;
        boolean[] visited = new boolean[numsNode];
        int[] minWeight = new int[numsNode];
        Arrays.fill(minWeight, Integer.MAX_VALUE);
        minWeight[0] = 0;
        PriorityQueue<Node> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a.minWeightIn));
        minHeap.add(new Node(0, 0));

        while (!minHeap.isEmpty()){
            Node curr = minHeap.poll();
            int u = curr.vertex;
            int w = curr.minWeightIn;

            if(visited[u])continue;

            visited[u] = true;
            total += w;

            for (Edge neighbor : graph.get(u)){
                int v = neighbor.vertex;
                int vw = neighbor.weight;
                if(!visited[v] && minWeight[v] > vw){
                    minWeight[v] = vw;
                    minHeap.add(new Node(v, vw));
                }
            }
        }
        System.out.println(total);
    }
    public static void main(String[] args) {

        String filePath = "/Users/batman/Desktop/Stanford Algorithm/Course 3 Greedy Algorithm/Week 1/Assignment 1/src/edges.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String[] firstLine = reader.readLine().split(" ");
            int numNodes = Integer.parseInt(firstLine[0]);
            int numEdges = Integer.parseInt(firstLine[1]);
            List<List<Edge>> graph = new ArrayList<>();
            for (int i = 0; i < numNodes; i++) {
                graph.add(new ArrayList<>());
            }

            for (int i = 0; i < numEdges; i++) {
                String[] edgeData = reader.readLine().split(" ");
                int node1 = Integer.parseInt(edgeData[0]) - 1;
                int node2 = Integer.parseInt(edgeData[1]) - 1;
                int cost = Integer.parseInt(edgeData[2]);

                graph.get(node1).add(new Edge(node2, cost));
                graph.get(node2).add(new Edge(node1, cost));
            }


            MST(numNodes, graph);

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }
}
