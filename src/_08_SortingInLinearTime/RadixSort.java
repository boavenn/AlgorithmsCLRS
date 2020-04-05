package _08_SortingInLinearTime;

import java.util.Arrays;

/*
Time complexity: O(d(n + k))
Auxiliary space: O(n + k)
In-place: no
Stable: yes

d - digits of a number
k - number of distinct values every digit can have
 */

public abstract class RadixSort
{
    public static void sort(int[] arr, int d) {
        for(int i = 0, exp = 1; i < d; i++, exp *= 10)
            countingSortRadix(arr, exp);
    }

    private static void countingSortRadix(int[] arr, int exp) {
        int[] C = new int[10];
        int[] res = new int[arr.length];

        Arrays.fill(C, 0);
        for (int value : arr)
            C[(value / exp) % 10]++;
        for (int i = 1; i < 10; i++)
            C[i] += C[i - 1];
        for (int i = arr.length - 1; i >= 0; i--) {
            res[C[(arr[i] / exp) % 10] - 1] = arr[i];
            C[(arr[i] / exp) % 10]--;
        }
        System.arraycopy(res, 0, arr, 0, arr.length);
    }

    public static void main(String[] args) {
        int[] arr = {2, 6, 3, 3, 0, 19, 2, 3, 4, 5, 5};
        sort(arr, 2);
        System.out.println(Arrays.toString(arr));
    }
}
