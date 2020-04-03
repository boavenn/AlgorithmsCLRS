package _07_QuickSort;

/*
Time complexity: O(nlogn)
Auxiliary space: O(1)
In-place: yes
Stable: no
 */

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public abstract class QuickSort
{
    public static <T> void sort(T[] arr, Comparator<T> comparator) {
        quickSort(arr, comparator, 0, arr.length - 1);
    }

    private static <T> void quickSort(T[] arr, Comparator<T> comparator, int begin, int end) {
        if(begin < end) {
            int q = partition(arr, comparator, begin, end);
            quickSort(arr, comparator, begin, q - 1);
            quickSort(arr, comparator, q + 1, end);
        }
    }

    private static <T> int partition(T[] arr, Comparator<T> comparator, int begin, int end) {
        // randomization
        int r = new Random().nextInt(end - begin) + begin;
        swap(arr, r, end);

        T x = arr[end];
        int i = begin - 1;
        for(int j = begin; j < end; j++)
            if(comparator.compare(arr[j], x) <= 0) {
                i++;
                swap(arr, i ,j);
            }
        swap(arr, i + 1, end);
        return i + 1;
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


