package _09_MediansAndOrderStatistics;

import java.util.Comparator;
import java.util.Random;

public abstract class RandomizedSelect
{
    public static <T> T randomizedSelect(T[] arr, Comparator<T> comparator, int begin, int end, int i) {
        if(begin == end)
            return arr[begin];

        int q = randomizedPartition(arr, comparator, begin, end);
        int k = q - begin + 1;
        if(i == k)
            return arr[q];
        else if (i < k)
            return randomizedSelect(arr, comparator, begin, q - 1, i);
        else
            return randomizedSelect(arr, comparator, q + 1, end, i - k);
    }

    private static <T> int randomizedPartition(T[] arr, Comparator<T> comparator, int begin, int end) {
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
        Integer[] arr = {2, 0, 5, 9, 6, 3, 1, 4, 7, 8};
        System.out.println(randomizedSelect(arr, Integer::compareTo, 0, arr.length - 1, 7));
    }
}
