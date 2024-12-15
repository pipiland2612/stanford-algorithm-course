import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

class Node{
    int frequency;
    char data;
    Node left, right;

    public Node(char data, int frequency) {
        this.data = data;
        this.frequency = frequency;
    }
    public Node(int frequency) {
        this.frequency = frequency;
    }
    public Node(int frequency, Node left, Node right) {
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }
}

class HuffmanCode {
    public static Node createTree(PriorityQueue<Node> pq){
        while(pq.size() > 1){
            Node left = pq.poll();
            Node right = pq.poll();
            Node parent = new Node(left.frequency + right.frequency, left, right);
            pq.add(parent);
        }
        return pq.poll();
    }
    public static void buildCodes(Node root, String code, HashMap<Character, String> codes){
        if(root == null) return;
        if(root.left == null && root.right == null){
            codes.put(root.data, code);
            return;
        }
        buildCodes(root.left, code + "0", codes);
        buildCodes(root.right, code + "1", codes);
    }
    public static String encode(String text, HashMap<Character, String> codes){
        StringBuilder sb = new StringBuilder();
        for(char c : text.toCharArray()){
            sb.append(codes.get(c));
        }
        return sb.toString();
    }
    public static String decode(String code, Node root){
        StringBuilder sb = new StringBuilder();
        Node curr = root;
        for(char c : code.toCharArray()){
            curr = c == '0' ? curr.left : curr.right;
            if(curr.left == null && curr.right == null){
                sb.append(curr.data);
                curr = root;
            }
        }
        return sb.toString();
    }

    public static int getMaxDepth(Node root) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) return 0;
        int leftDepth = getMaxDepth(root.left);
        int rightDepth = getMaxDepth(root.right);
        return 1 + Math.max(leftDepth, rightDepth);
    }

    public static int getMinDepth(Node root) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) return 0; // Leaf node
        int leftDepth = (root.left == null) ? Integer.MAX_VALUE : getMinDepth(root.left);
        int rightDepth = (root.right == null) ? Integer.MAX_VALUE : getMinDepth(root.right);
        return 1 + Math.min(leftDepth, rightDepth);
    }

    public static void main(String[] args) {
        try {
            String inputFile = "/Users/batman/Desktop/Stanford Algorithm/Course 3 Greedy Algorithm/Week 3/HuffmanCode/src/huffman.txt";

            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            int numberOfSymbols = Integer.parseInt(br.readLine().trim());
            PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.frequency));

            for (int i = 0; i < numberOfSymbols; i++) {
                int weight = Integer.parseInt(br.readLine().trim());
                pq.add(new Node(weight));
            }
            br.close();

            Node root = createTree(pq);

            int maxCodewordLength = getMaxDepth(root);
            int minCodewordLength = getMinDepth(root);
            System.out.println("Maximum length of a codeword: " + maxCodewordLength);
            System.out.println("Minimum length of a codeword: " + minCodewordLength);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}