import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

class Node{
    int frequency;
    char data;
    Node left, right;
    public Node(char data, int frequency) {
        this.frequency = frequency;
        this.data = data;
    }
    public Node(int frequency, Node left, Node right) {
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }
}

class HuffmanCode {
    public static Node buildTree(HashMap<Character, Integer> frequencyMap){
        PriorityQueue<Node> minHeap = new PriorityQueue<>(Comparator.comparing(node -> node.frequency));

        for(Map.Entry<Character, Integer> entry: frequencyMap.entrySet()){
            minHeap.add(new Node(entry.getKey(), entry.getValue()));
        }

        while(minHeap.size()>1){
            Node left = minHeap.poll();
            Node right = minHeap.poll();
            Node parent = new Node(left.frequency+right.frequency, left, right);
            minHeap.add(parent);
        }
        return minHeap.poll();
    }
    public static void buildCode(Node root, HashMap<Character, String> map, String code){
        if(root==null) return;
        if(root.left==null && root.right==null){
            map.put(root.data, code);
        }

        buildCode(root.left, map, code + "0");
        buildCode(root.right, map, code + "1");
    }

    public static String encode(String str, HashMap<Character, String> map){
        StringBuilder sb = new StringBuilder();
        for(char c: str.toCharArray()){
            sb.append(map.get(c));
        }
        return sb.toString();
    }

    public static String decode(String code, Node root){
        StringBuilder sb = new StringBuilder();
        Node curr = root;
        for(char c: code.toCharArray()){
            curr = c=='0'?curr.left:curr.right;
            if(curr.left==null && curr.right==null){
                sb.append(curr.data);
                curr = root;
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String str = "Hello World";
        HashMap<Character, Integer> frequencyMap = new HashMap<>();
        for(char c: str.toCharArray()){
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0)+1);
        }
        Node root = buildTree(frequencyMap);
        HashMap<Character, String> map = new HashMap<>();
        buildCode(root, map, "");
        System.out.println(encode(str, map));
        System.out.println(decode(encode(str, map), root));
    }
}