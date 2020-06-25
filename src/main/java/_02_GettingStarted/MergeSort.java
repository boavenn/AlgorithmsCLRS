package _02_GettingStarted;

import java.util.Comparator;

public final class MergeSort
{
    public static <T> void sort(T[] arr, Comparator<T> comp) {
        mergeSort(arr, comp, 0, arr.length - 1);
    }

    private static <T> void mergeSort(T[] arr, Comparator<T> comp, int begin, int end) {
        if (begin < end) {
            int q = (begin + end) / 2;
            mergeSort(arr, comp, begin, q);
            mergeSort(arr, comp, q + 1, end);
            merge(arr, comp, begin, q, end);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> void merge(T[] arr, Comparator<T> comp, int begin, int q, int end) {
        int len1 = q - begin + 1;
        int len2 = end - q;
        T[] L = (T[]) new Object[len1];
        T[] R = (T[]) new Object[len2];

        for (int i = 0; i < len1; i++)
            L[i] = arr[begin + i];
        for (int i = 0; i < len2; i++)
            R[i] = arr[q + 1 + i];

        int i = 0;
        int j = 0;
        int k = begin;
        while (i < len1 && j < len2) {
            if (comp.compare(L[i], R[j]) <= 0)
                arr[k++] = L[i++];
            else
                arr[k++] = R[j++];
        }

        while (i < len1)
            arr[k++] = L[i++];

        while (j < len2)
            arr[k++] = R[j++];
    }
}
