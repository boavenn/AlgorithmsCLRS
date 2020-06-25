package _08_SortingInLinearTime;

import _02_GettingStarted.InsertionSort;

import java.util.*;

public final class BucketSort
{
    public static void sort(Integer[] arr, Integer max) {
        int numOfBuckets = (int) Math.sqrt(arr.length) + 1;
        List<List<Integer>> buckets = new ArrayList<>(numOfBuckets);
        for (int i = 0; i < numOfBuckets; i++)
            buckets.add(new LinkedList<>());

        for (Integer v : arr) {
            int idx = v * (numOfBuckets - 1) / max;
            buckets.get(idx).add(v);
        }

        int i = 0;
        for(List<Integer> l : buckets) {
            for(Integer v : l)
                arr[i++] = v;
        }

        InsertionSort.sort(arr, Integer::compareTo);
    }

    private static class Example
    {
        public static void main(String[] args) {
            Integer[] arr = new Integer[50];
            Random r = new Random();
            for(int i = 0; i < arr.length; i++)
                arr[i] = r.nextInt(1000);

            sort(arr, 999);
            System.out.println(Arrays.toString(arr));
        }
    }
}
