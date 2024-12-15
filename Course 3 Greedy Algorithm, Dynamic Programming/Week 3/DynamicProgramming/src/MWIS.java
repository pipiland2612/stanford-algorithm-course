import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MWIS {

    public static Set<Integer> MWIS(List<Integer> arr) {
        int n = arr.size();
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = arr.get(0);
        for (int i = 2; i <= n; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + arr.get(i - 1));
        }

        Set<Integer> res = new HashSet<>();
        int i = n;
        while (i >= 0) {
            if(i == 1 || dp[i - 1] < dp[i - 2] + arr.get(i - 1)){
                res.add(i);
                i -= 2;
            }else {
                i --;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        List<Integer> weights = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("/Users/batman/Desktop/Stanford Algorithm/Course 3 Greedy Algorithm/Week 3/DynamicProgramming/src/mwis.txt"))) {
            int numVertices = Integer.parseInt(br.readLine().trim());
            for (int i = 0; i < numVertices; i++) {
                weights.add(Integer.parseInt(br.readLine().trim()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        int[] checked = new int[]{1, 2, 3, 4, 17, 117, 517, 997};
        Set<Integer> res = MWIS(weights);
        StringBuilder sb = new StringBuilder();
        for (int j : checked) {
            if (res.contains(j)) {
                sb.append("1");
            } else {
                sb.append("0");
            }
        }
        System.out.println(sb);
    }
}
