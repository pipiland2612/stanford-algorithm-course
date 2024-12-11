public class InversionCounter {
    public static void main(String[] args) {
        System.out.println("Inversion Counter: " + inversionCounter(new int[]{2,4,6,1,3,5}));
    }
    public static int inversionCounter(int[] arr) {
        return mergeCount(arr, new int[arr.length], 0, arr.length - 1);
    }
    private static int mergeCount(int[] arr, int[] temp, int l, int r) {
        int count = 0;
        if(l < r){
            int mid = (l+r)/2;
            count += mergeCount(arr, temp, l, mid);
            count += mergeCount(arr, temp, mid + 1, r);
            count += inversionCounter(arr, temp, l, mid, r);
        }
        return count;
    }
    private static int inversionCounter(int[] arr, int[] temp, int l, int m, int r) {
        int i = l, j = m+1, k = l;
        int count = 0;
        while (i <= m && j <= r) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            }else{
                count += m - i + 1;
                temp[k++] = arr[j++];
            }
        }
        while (i <= m) {
            temp[k++] = arr[i++];
        }
        while (j <= r) {
            temp[k++] = arr[j++];
        }
        return count;
    }
}
