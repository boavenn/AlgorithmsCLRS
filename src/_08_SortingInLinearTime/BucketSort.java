package _08_SortingInLinearTime;

import java.util.*;

/*
Time complexity: O(n) if input is uniformly distributed, O(n^2) otherwise
Auxiliary space: O(n * k)
In-place: no
Stable: yes

k - number of buckets
 */

public abstract class BucketSort
{
    public static void sort(double[] arr) {
        int n = arr.length;
        List<List<Double>> buckets = new ArrayList<>();

        for (int i = 0; i < n; i++)
            buckets.add(new ArrayList<>());

        for (double value : arr)
            buckets.get((int) Math.floor(n * value)).add(value);

        for (List<Double> l : buckets) {
            insertionSort(l);
        }

        int idx = 0;
        for(List<Double> l : buckets)
            for(Double value : l)
                arr[idx++] = value;
    }

    private static void insertionSort(List<Double> list) {
        for (int j = 1; j < list.size(); j++) {
            double key = list.get(j);
            int i = j - 1;
            while (i >= 0 && list.get(i) > key) {
                list.set(i + 1, list.get(i));
                i--;
            }
            list.set(i + 1, key);
        }
    }

    public static void main(String[] args) {
        double[] arr = {0.78, 0.17, 0.39, 0.26, 0.72, 0.94, 0.21, 0.12, 0.23, 0.68};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
