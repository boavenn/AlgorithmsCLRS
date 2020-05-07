package _08_SortingInLinearTime;

import java.util.Arrays;
import java.util.Random;

public final class RadixSort
{
    // d - maximum digits of a number
    public static void sort(int[] arr, int d) {
        for(int i = 0, exp = 1; i < d; i++, exp *= 10)
            countingSortRadix(arr, exp);
    }

    private static void countingSortRadix(int[] arr, int exp) {
        int[] util = new int[10];
        int[] res = new int[arr.length];

        for (int value : arr)
            util[(value / exp) % 10]++;
        for (int i = 1; i < 10; i++)
            util[i] += util[i - 1];
        for (int i = arr.length - 1; i >= 0; i--) {
            res[util[(arr[i] / exp) % 10] - 1] = arr[i];
            util[(arr[i] / exp) % 10]--;
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

            sort(arr, 3);
            System.out.println(Arrays.toString(arr));
        }
    }
}
