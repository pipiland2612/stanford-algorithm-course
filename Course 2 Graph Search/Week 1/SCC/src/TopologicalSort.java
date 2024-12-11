import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TopologicalSort {
    public static void main(String[] args) {

    }
    public static List<Integer> topologicalSort(Graph graph) {
        boolean[] visited = new boolean[graph.V];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < graph.V; i++) {
            if (!visited[i]) {
                dfs(graph, stack, i, visited);
            }
        }
        List<Integer> result = new ArrayList<>();
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        return result;
    }
    private static void dfs(Graph graph, Stack<Integer> stack, int curr, boolean[] visited) {
        visited[curr] = true;
        for (int neighbor : graph.adj.get(curr)) {
            if (!visited[neighbor]) {
                dfs(graph, stack, neighbor, visited);
            }
        }
        stack.push(curr);
    }
}
