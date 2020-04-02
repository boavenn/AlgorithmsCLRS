package _02_GettingStarted;

import java.util.Arrays;
import java.util.Comparator;

/*
Time complexity: O(n^2)
Auxiliary space: O(1)
In-place: yes
Stable: yes
 */

public abstract class BubbleSort
{
    public static <T> void sort(T[] arr, Comparator<T> comparator) {
        for(int i = 0; i < arr.length - 1; i++)
            for(int j = arr.length - 1; j > i; j--)
                if(comparator.compare(arr[j], arr[j - 1]) < 0)
                    swap(arr, j, j - 1);
        /*
        for(int i = 0; i < arr.length - 1; i++)
            for(int j = 0; j < arr.length - 1 - i; j++)
                if(comparator.compare(arr[j], arr[j + 1]) > 0)
                    swap(arr, j, j + 1);
         */
    }

    private static <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        Integer[] arr = {2, 6, 3, 3, 0, 19, 2, 3, 4, 5, 5};
        sort(arr, Integer::compareTo);
        System.out.println(Arrays.toString(arr));
    }
}
