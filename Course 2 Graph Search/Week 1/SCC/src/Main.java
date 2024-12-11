import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    // Add edge from source to destination in the adjacency list
    public static void addEdge(List<Integer>[] adj, int s, int d) {
        adj[s].add(d);
    }

    // Print the graph for debugging purposes
    public static void printGraph(List<Integer>[] adj, int V) {
        for (int d = 0; d < V; d++) {
            System.out.print("\nVertex " + d + ":");
            for (int x : adj[d]) {
                System.out.print(" -> " + x);
            }
            System.out.println();
        }
    }

    // Depth-First Search to fill the stack with finishing times
    public static void DFS(List<Integer>[] adj, int s, int V, boolean[] visited, Stack<Integer> stack) {
        visited[s] = true;
        for (int x : adj[s]) {
            if (!visited[x]) {
                DFS(adj, x, V, visited, stack);
            }
        }
        stack.push(s);
    }

    // Second DFS to calculate the size of a strongly connected component
    public static int DFS2(List<Integer>[] adj, int s, int V, boolean[] visited) {
        visited[s] = true;
        int size = 1;
        for (int x : adj[s]) {
            if (!visited[x]) {
                size += DFS2(adj, x, V, visited);
            }
        }
        return size;
    }

    public static void main(String[] args) {
        int V = 875714; // Number of vertices
        List<Integer>[] adj = new ArrayList[V];
        List<Integer>[] adj2 = new ArrayList[V];

        // Initialize adjacency lists
        for (int i = 0; i < V; i++) {
            adj[i] = new ArrayList<>();
            adj2[i] = new ArrayList<>();
        }

        // Read the file and load the edges
        try (BufferedReader br = new BufferedReader(new FileReader("/Users/batman/Desktop/Java DSA/SortingAlgo/src/Scc.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                int a = Integer.parseInt(parts[0]) - 1;
                int b = Integer.parseInt(parts[1]) - 1;
                addEdge(adj, a, b);
                addEdge(adj2, b, a);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Print the graph (for debugging purposes)
         printGraph(adj, V);
         printGraph(adj2, V);

        boolean[] visited = new boolean[V];
        Stack<Integer> stack = new Stack<>();

        // Perform first DFS to calculate finishing times
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                DFS(adj, i, V, visited, stack);
            }
        }

        // Reset visited array for the second DFS pass
        Arrays.fill(visited, false);

        PriorityQueue<Integer> pq = new PriorityQueue<>();

        // Second pass: calculate the sizes of strongly connected components
        while (!stack.isEmpty()) {
            int s = stack.pop();
            if (!visited[s]) {
                System.out.println("Visiting node " + s);
                int size = DFS2(adj2, s, V, visited);

                if (pq.size() < 5) {
                    pq.add(size);
                } else if (size > pq.peek()) {
                    pq.poll();
                    pq.add(size);
                }
            }
        }

        // Print the sizes of the five largest strongly connected components
        List<Integer> sccSizes = new ArrayList<>();
        while (!pq.isEmpty()) {
            sccSizes.add(pq.poll());
        }
        Collections.reverse(sccSizes);
        for (int size : sccSizes) {
            System.out.print(size + " ");
        }
        System.out.println();
    }
}
