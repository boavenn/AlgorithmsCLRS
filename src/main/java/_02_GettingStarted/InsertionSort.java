package _02_GettingStarted;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public final class InsertionSort
{
    public static <T> void sort(T[] arr, Comparator<T> comp) {
        for(int j = 1; j < arr.length; j++) {
            T key = arr[j];
            int i = j - 1;
            while(i >= 0 && comp.compare(arr[i], key) > 0) {
                arr[i + 1] = arr[i];
                i--;
            }
            arr[i + 1] = key;
        }
    }

    private static class Example
    {
        public static void main(String[] args) {
            Integer[] arr = new Integer[50];
            Random r = new Random();
            for(int i = 0; i < arr.length; i++)
                arr[i] = r.nextInt(1000);

            sort(arr, Integer::compareTo);
            System.out.println(Arrays.toString(arr));
        }
    }
}
