package _02_GettingStarted;

import java.util.Arrays;
import java.util.Comparator;

/*
Time complexity: O(n^2)
Auxiliary space: O(1)
In-place: yes
Stable: yes
 */

public abstract class InsertionSort
{
    public static <T> void sort(T[] arr, Comparator<T> comparator) {
        for(int j = 1; j < arr.length; j++) {
            T key = arr[j];
            int i = j - 1;
            while(i >= 0 && comparator.compare(arr[i], key) > 0) {
                arr[i + 1] = arr[i];
                i--;
            }
            arr[i + 1] = key;
        }
    }

    public static void main(String[] args) {
        Integer[] arr = {2, 6, 3, 3, 0, 19, 2, 3, 4, 5, 5};
        sort(arr, Integer::compareTo);
        System.out.println(Arrays.toString(arr));
    }
}
