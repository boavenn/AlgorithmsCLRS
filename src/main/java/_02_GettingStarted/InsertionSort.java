package _02_GettingStarted;

import java.util.Comparator;

public final class InsertionSort
{
    public static <T> void sort(T[] arr, Comparator<T> comp) {
        for (int j = 1; j < arr.length; j++) {
            T key = arr[j];
            int i = j - 1;
            while (i >= 0 && comp.compare(arr[i], key) > 0) {
                arr[i + 1] = arr[i];
                i--;
            }
            arr[i + 1] = key;
        }
    }
}
