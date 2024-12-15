import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Graph {
    int v;
    Stack<Integer> finishOrder;
    List<List<Integer>> adjList;
    List<List<Integer>> revAdjList;
    boolean[] visited;
    boolean[] visitedScc;
    int sccCount;
    int[] scc;

    public Graph(int v) {
        this.v = v;
        finishOrder = new Stack<>();
        adjList = new ArrayList<>();
        revAdjList = new ArrayList<>();
        visited = new boolean[v];
        visitedScc = new boolean[v];
        sccCount = 0;
        scc = new int[v];
        for (int i = 0; i < v; i++) {
            adjList.add(new ArrayList<>());
            revAdjList.add(new ArrayList<>());
        }
    }

    public void addEdge(int u, int v) {
        adjList.get(u).add(v);
        revAdjList.get(v).add(u);
    }

    public void dfs(int u) {
        if (visited[u]) return;
        visited[u] = true;
        for (int v : adjList.get(u)) {
            if (!visited[v]) {
                dfs(v);
            }
        }
        finishOrder.push(u);
    }

    public void dfsScc(int u, int sccNum) {
        if (visitedScc[u]) return;
        visitedScc[u] = true;
        scc[u] = sccNum;
        for (int v : revAdjList.get(u)) {
            if (!visitedScc[v]) {
                dfsScc(v, sccNum);
            }
        }
    }

}

public class TwoSAT {
    private final int n;
    private final Graph g;

    public TwoSAT(int n) {
        this.n = 2 * n;
        g = new Graph(2 * n);
    }

    private int var(int x) {
        return 2 * x;
    }

    private int neg(int x) {
        return 2 * x + 1;
    }

    public void addClause(int x, boolean isX, int y, boolean isY) {
        int xIndex = isX ? var(x) : neg(x);
        int notXIndex = isX ? neg(x) : var(x);
        int yIndex = isY ? var(y) : neg(y);
        int notYIndex = isY ? neg(y) : var(y);

        g.addEdge(notXIndex, yIndex);
        g.addEdge(notYIndex, xIndex);
    }

    public boolean isSatisfiable() {
        Arrays.fill(g.visited, false);
        for (int i = 0; i < n; i++) {
            if (g.visited[i]) continue;
            g.dfs(i);
        }

        Arrays.fill(g.visitedScc, false);
        int sccNum = 0;
        while (!g.finishOrder.isEmpty()) {
            int u = g.finishOrder.pop();
            if (!g.visitedScc[u]) {
                g.dfsScc(u, sccNum++);
            }
        }

        for (int i = 0; i < n / 2; i++) {
            if (g.scc[var(i)] == g.scc[neg(i)]) return false;
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        String[] input = new String[]{"2sat1.txt", "2sat2.txt", "2sat3.txt", "2sat4.txt", "2sat5.txt", "2sat6.txt"};
        StringBuilder result = new StringBuilder();
        for (String s : input) {
            String inputFilePath = "/Users/batman/Desktop/Stanford Algorithm/Course 4 NP Problem/Week 4/2SAT/" + s;
            BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));

            String line = reader.readLine();
            if (line == null) {
                throw new IOException("File " + s + " is empty or invalid.");
            }
            int n = Integer.parseInt(line.trim());
            TwoSAT twoSAT = new TwoSAT(n);
            for (int index = 0; index < n; index++) {
                line = reader.readLine();
                if (line == null) {
                    throw new IOException("File " + s + " has insufficient clauses.");
                }
                String[] clause = line.trim().split(" ");
                if (clause.length < 2) {
                    throw new IOException("Malformed clause in file " + s);
                }
                int firstLiteral = Integer.parseInt(clause[0]);
                int secondLiteral = Integer.parseInt(clause[1]);

                int x = Math.abs(firstLiteral) - 1;
                boolean isX = firstLiteral > 0;

                int y = Math.abs(secondLiteral) - 1;
                boolean isY = secondLiteral > 0;

                twoSAT.addClause(x, isX, y, isY);
            }

            if (twoSAT.isSatisfiable()) {
                result.append("1");
            } else {
                result.append("0");
            }

            reader.close();
        }
        System.out.println(result.toString());
    }
}