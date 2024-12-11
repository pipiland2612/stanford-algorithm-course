import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.PriorityQueue;

class MedianFinder {
    PriorityQueue<Integer> minHeap;
    PriorityQueue<Integer> maxHeap;

    public MedianFinder() {
        minHeap = new PriorityQueue<>();
        maxHeap = new PriorityQueue<>(Collections.reverseOrder());
    }

    public void addNum(int num) {
        if(minHeap.isEmpty() || num >= minHeap.peek()) {
            minHeap.add(num);
        } else {
            maxHeap.add(num);
        }

        while(Math.abs(maxHeap.size() - minHeap.size()) > 1) {
            if(maxHeap.size() > minHeap.size()) {
                minHeap.add(maxHeap.poll());
            } else {
                maxHeap.add(minHeap.poll());
            }
        }
    }

    public int findMedian() {
        if(minHeap.size() == maxHeap.size()) {
            return maxHeap.peek(); // As per problem statement, choose the "k/2-th" smallest for even k
        } else {
            return minHeap.size() > maxHeap.size() ? minHeap.peek() : maxHeap.peek();
        }
    }
}

public class FindMedian {
    public static void main(String[] args) {
        String filePath = "numbers.txt"; // Update the path to your file if needed
        MedianFinder medianFinder = new MedianFinder();
        long sumOfMedians = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                int number = Integer.parseInt(line.trim());
                medianFinder.addNum(number);
                int currentMedian = medianFinder.findMedian();
                sumOfMedians += currentMedian;
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }

        long result = sumOfMedians % 10000;
        System.out.println("The sum of the medians modulo 10000 is: " + result);
    }
}
