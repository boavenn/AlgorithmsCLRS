package _02_GettingStarted;

import java.util.Arrays;
import java.util.Comparator;

/*
Time complexity: O(nlogn)
Auxiliary space: O(n)
In-place: no (but can be)
Stable: yes
 */

public abstract class MergeSort
{
    public static <T> void sort(T[] arr, Comparator<T> comparator) {
        mergeSort(arr, comparator, 0, arr.length - 1);
    }

    private static <T> void mergeSort(T[] arr, Comparator<T> comparator, int begin, int end) {
        if(begin < end) {
            int q = (begin + end) / 2;
            mergeSort(arr, comparator, begin, q);
            mergeSort(arr, comparator, q + 1, end);
            merge(arr, comparator, begin, q, end);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> void merge(T[] arr, Comparator<T> comparator, int begin, int q, int end) {
        int len1 = q - begin + 1;
        int len2 = end - q;
        T[] L = (T[]) new Object[len1];
        T[] R = (T[]) new Object[len2];

        for(int i = 0; i < len1; i++)
            L[i] = arr[begin + i];
        for(int i = 0; i < len2; i++)
            R[i] = arr[q + 1 + i];

        int i = 0;
        int j = 0;
        int k = begin;
        while (i < len1 && j < len2) {
            if (comparator.compare(L[i], R[j]) <= 0)
                arr[k++] = L[i++];
            else
                arr[k++] = R[j++];
        }

        while(i < len1)
            arr[k++] = L[i++];

        while(j < len2)
            arr[k++] = R[j++];
    }

    public static void main(String[] args) {
        Integer[] arr = {2, 6, 3, 3, 0, 19, 2, 3, 4, 5, 5};
        sort(arr, Integer::compareTo);
        System.out.println(Arrays.toString(arr));
    }
}
