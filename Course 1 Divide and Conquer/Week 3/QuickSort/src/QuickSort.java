import java.util.Arrays;

public class QuickSort {
    public static void main(String[] args) {
        int[] arr = new int[]{1,3,5,2,4};
        quickSort(arr, 0,arr.length-1);
        System.out.println(Arrays.toString(arr));
    }
    public static void quickSort(int[] arr, int left, int right) {
        if (left < right) {
            int pivot = partition(arr, left, right);
            quickSort(arr, left, pivot - 1);
            quickSort(arr, pivot + 1, right);
        }
    }
    private static int medianOfThree(int[] arr, int left, int right) {
        int mid = (left + right) / 2;
        int first = arr[left];
        int middle = arr[mid];
        int last = arr[right];

        if ((first < middle && middle < last) || (last < middle && middle < first)) {
            return mid;
        } else if ((middle < first && first < last) || (last < first && first < middle)) {
            return left;
        } else {
            return right;
        }
    }
    private static int partition(int[] arr, int left, int right) {
        int pivotIndex = medianOfThree(arr, left, right);
        swap(arr, left, pivotIndex);
        int pivot = arr[left];
        int i = left + 1;

        for (int j = left + 1; j <= right; j++) {
            if(arr[j] < pivot) {
                swap(arr, i, j);
                i++;
            }
        }
        swap(arr, i - 1, left);
        return i - 1;
    }
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
