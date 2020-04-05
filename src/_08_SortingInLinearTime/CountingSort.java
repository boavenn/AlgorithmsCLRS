package _08_SortingInLinearTime;

/*
Time complexity: O(n + k)
Auxiliary space: O(n + k)
In-place: no
Stable: yes

k - maximum number in the array
 */

import java.util.Arrays;

public abstract class CountingSort
{
    public static void sort(int[] arr, int k) {
        int[] C = new int[k + 1];
        int[] res = new int[arr.length];

        Arrays.fill(C, 0);
        for (int v : arr)
            C[v]++;
        for (int i = 1; i <= k; i++)
            C[i] += C[i - 1];
        for (int i = arr.length - 1; i >= 0; i--) {
            res[C[arr[i]] - 1] = arr[i];
            C[arr[i]]--;
        }
        System.arraycopy(res, 0, arr, 0, arr.length);
    }

    public static void main(String[] args) {
        int[] arr = {2, 6, 3, 3, 0, 19, 2, 3, 4, 5, 5};
        sort(arr, 19);
        System.out.println(Arrays.toString(arr));
    }
}
