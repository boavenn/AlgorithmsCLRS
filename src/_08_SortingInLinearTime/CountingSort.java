package _08_SortingInLinearTime;

import java.util.Arrays;
import java.util.Random;

public final class CountingSort
{
    // k - maximum number in the array
    public static void sort(int[] arr, int k) {
        int[] util = new int[k + 1];
        int[] res = new int[arr.length];

        for (int v : arr)
            util[v]++;
        for (int i = 1; i <= k; i++)
            util[i] += util[i - 1];
        for (int i = arr.length - 1; i >= 0; i--) {
            res[util[arr[i]] - 1] = arr[i];
            util[arr[i]]--;
        }
        System.arraycopy(res, 0, arr, 0, arr.length);
    }

    private static class Example
    {
        public static void main(String[] args) {
            int[] arr = new int[50];
            Random r = new Random();
            for(int i = 0; i < arr.length; i++)
                arr[i] = r.nextInt(1000);

            sort(arr, 999);
            System.out.println(Arrays.toString(arr));
        }
    }
}
