import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class TwoSum {

    public static void main(String[] args) {
        String filename = "/Users/batman/Desktop/Stanford Algorithm/Course 2 Graph Search/Week 4/TwoSum/src/2sum.txt";
        try {
            int result = countDistinctTargetSums(filename);
            System.out.println("Number of target values with distinct x, y: " + result);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public static int countDistinctTargetSums(String filename) throws IOException {
        HashSet<Long> numbers = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                long num = Long.parseLong(line.trim());
                numbers.add(num);
            }
        }
        HashSet<Integer> foundTargets = new HashSet<>();
        for (long x : numbers) {
            for (int t = -10000; t <= 10000; t++) {
                long y = t - x;
                if (y != x && numbers.contains(y)) {
                    foundTargets.add(t);
                }
            }
        }

        return foundTargets.size();
    }
}
