package _09_MediansAndOrderStatistics;

import java.util.Comparator;
import java.util.Random;

public final class RandomizedSelect
{
    // returns i-th smallest element
    public static <T> T randomizedSelect(T[] arr, Comparator<T> comp, int i) {
        return randomizedSelect(arr, comp, 0, arr.length - 1, i);
    }

    private static <T> T randomizedSelect(T[] arr, Comparator<T> comp, int begin, int end, int i) {
        if (i < 1 || i > arr.length - 1)
            throw new IllegalArgumentException();

        if (begin == end)
            return arr[begin];

        int q = randomizedPartition(arr, comp, begin, end);
        int k = q - begin + 1;
        if (i == k)
            return arr[q];
        else if (i < k)
            return randomizedSelect(arr, comp, begin, q - 1, i);
        else
            return randomizedSelect(arr, comp, q + 1, end, i - k);
    }

    private static <T> int randomizedPartition(T[] arr, Comparator<T> comp, int begin, int end) {
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
