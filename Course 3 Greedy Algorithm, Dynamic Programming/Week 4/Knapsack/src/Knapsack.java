import java.io.File;
import java.io.IOException;
import java.util.Scanner;

class Knapsack {

    public int knapsack(int[] weights, int[] values, int capacity) {
        int[][] dp = new int[weights.length + 1][capacity + 1];
        for (int i = 1; i <= weights.length; i++) {
            for (int j = 0; j <= capacity; j++) {
                if (j >= weights[i - 1]) {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weights[i - 1]] + values[i - 1]);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[weights.length][capacity];
    }

    public int knapsackOptimized(int[] weights, int[] values, int capacity){
        int[] dp = new int[capacity + 1];
        for (int i = 0; i < weights.length; i++) {
            for(int j = capacity; j >= weights[i]; j--){
                dp[j] = Math.max(dp[j], dp[j - weights[i]] + values[i]);
            }
        }
        return dp[capacity];
    }

    public static void main(String[] args) {
        String filePath = "/Users/batman/Desktop/Stanford Algorithm/Course 3 Greedy Algorithm/Week 4/Knapsack/src/knapsack_big.txt";

        try (Scanner scanner = new Scanner(new File(filePath))) {
            int knapsackSize = scanner.nextInt();
            int numberOfItems = scanner.nextInt();

            int[] values = new int[numberOfItems];
            int[] weights = new int[numberOfItems];

            for (int i = 0; i < numberOfItems; i++) {
                values[i] = scanner.nextInt();
                weights[i] = scanner.nextInt();
            }

            Knapsack knapsackSolver = new Knapsack();
            int maxValue = knapsackSolver.knapsackOptimized(weights, values, knapsackSize);

            System.out.println("Maximum value that can be obtained: " + maxValue);

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }
}