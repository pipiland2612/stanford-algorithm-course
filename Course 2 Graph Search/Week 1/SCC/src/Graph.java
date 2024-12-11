import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Graph {
    int V;
    List<List<Integer>> adj;

    public Graph(int V) {
        this.V = V;
        adj = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }
    }

    public void addEdge(int v, int w) {
        adj.get(v - 1).add(w - 1); // Convert 1-indexed to 0-indexed
    }

    public Graph reverse() {
        Graph g = new Graph(V);
        for (int i = 0; i < V; i++) {
            for (int neighbor : adj.get(i)) {
                g.addEdge(neighbor + 1, i + 1); // Shift back to 1-indexed for addEdge
            }
        }
        return g;
    }

    public Stack<Integer> getFinishTime() {
        boolean[] visited = new boolean[V];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                helper(visited, stack, i);
            }
        }
        return stack;
    }

    private void helper(boolean[] visited, Stack<Integer> stack, int v) {
        Stack<Integer> dfsStack = new Stack<>();
        dfsStack.push(v);

        while (!dfsStack.isEmpty()) {
            int current = dfsStack.peek();

            if (!visited[current]) {
                visited[current] = true;
            }

            boolean allNeighborsVisited = true;
            for (int w : adj.get(current)) {
                if (!visited[w]) {
                    dfsStack.push(w);
                    allNeighborsVisited = false;
                    break; // Simulate recursive call
                }
            }

            if (allNeighborsVisited) {
                dfsStack.pop();
                stack.push(current);
            }
        }
    }

    private void helperForSCC(int curr, List<Integer> temp, boolean[] visited) {
        Stack<Integer> dfsStack = new Stack<>();
        dfsStack.push(curr);

        while (!dfsStack.isEmpty()) {
            int current = dfsStack.pop();

            if (!visited[current]) {
                visited[current] = true;
                temp.add(current);

                for (int neighbor : adj.get(current)) {
                    if (!visited[neighbor]) {
                        dfsStack.push(neighbor);
                    }
                }
            }
        }
    }

    public List<List<Integer>> SCC() {
        boolean[] visited = new boolean[V];
        Stack<Integer> finishTime = getFinishTime();
        Graph g = reverse();

        List<List<Integer>> res = new ArrayList<>();
        while (!finishTime.isEmpty()) {
            int curr = finishTime.pop();
            if (!visited[curr]) {
                List<Integer> temp = new ArrayList<>();
                g.helperForSCC(curr, temp, visited);
                res.add(temp);
            }
        }
        res.sort((a, b) -> b.size() - a.size());
        return res;
    }
}
