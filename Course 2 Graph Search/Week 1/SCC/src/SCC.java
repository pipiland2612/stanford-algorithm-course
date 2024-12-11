import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class SCC {

    public static void main(String[] args) {
        String filePath = "/Users/batman/Desktop/Cpp/Scc.txt";
        int numVertices = 875714; // Number of vertices

        Graph graph = new Graph(numVertices);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                int src = Integer.parseInt(parts[0]);
                int dest = Integer.parseInt(parts[1]);
                graph.addEdge(src, dest);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<List<Integer>> sccSizes = graph.SCC();
        int[] top5 = new int[5];
        for (int i = 0; i < 5; i++) {
            if (i < sccSizes.size()) {
                top5[i] = sccSizes.get(i).size();
            } else {
                top5[i] = 0;
            }
        }
        System.out.println(top5[0] + "," + top5[1] + "," + top5[2] + "," + top5[3] + "," + top5[4]);
    }
}
