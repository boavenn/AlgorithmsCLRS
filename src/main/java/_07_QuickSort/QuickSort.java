package _07_QuickSort;

import java.util.Comparator;
import java.util.Random;

public final class QuickSort
{
    public static <T> void sort(T[] arr, Comparator<T> comp) {
        quickSort(arr, comp, 0, arr.length - 1);
    }

    private static <T> void quickSort(T[] arr, Comparator<T> comp, int begin, int end) {
        if (begin < end) {
            int q = partition(arr, comp, begin, end);
            quickSort(arr, comp, begin, q - 1);
            quickSort(arr, comp, q + 1, end);
        }
    }

    private static <T> int partition(T[] arr, Comparator<T> comp, int begin, int end) {
        // randomization
        int r = new Random().nextInt(end - begin) + begin;
        swap(arr, r, end);

        T x = arr[end];
        int i = begin - 1;
        for (int j = begin; j < end; j++)
            if (comp.compare(arr[j], x) <= 0) {
                i++;
                swap(arr, i, j);
            }
        swap(arr, i + 1, end);
        return i + 1;
    }

    private static <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}


