package _02_GettingStarted;

import java.util.Comparator;

public final class BubbleSort
{
    public static <T> void sort(T[] arr, Comparator<T> comp) {
        boolean swapped;
        for (int i = 0; i < arr.length - 1; i++) {
            swapped = false;
            for (int j = arr.length - 1; j > i; j--) {
                if (comp.compare(arr[j], arr[j - 1]) < 0) {
                    swap(arr, j, j - 1);
                    swapped = true;
                }
            }

            if (!swapped)
                break;
        }
    }

    private static <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
