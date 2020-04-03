package _06_Heapsort;

/*
Time complexity: O(nlogn)
Auxiliary space: O(1)
In-place: yes
Stable: not in this implementation
 */

import java.util.Arrays;
import java.util.Comparator;

public abstract class HeapSort
{
    public static <T> void sort(T[] arr, Comparator<T> comparator) {
        int heapSize = arr.length;
        buildHeap(arr, comparator, heapSize);
        for(int i = arr.length - 1; i >= 1; i--) {
            swap(arr, 0, i);
            heapSize--;
            heapify(arr, comparator, heapSize, 0);
        }
    }

    /*
    parent(i) = (i - 1) / 2
    left(i) = 2 * i + 1
    right(i) = 2 * i + 2
     */

    private static <T> void heapify(T[] arr, Comparator<T> comparator, int heapSize, int i) {
        int l = 2 * i + 1;
        int r = l + 1;
        int largest;

        if(l < heapSize && comparator.compare(arr[l], arr[i]) > 0)
            largest = l;
        else
            largest = i;

        if(r < heapSize && comparator.compare(arr[r], arr[largest]) > 0)
            largest = r;

        if(largest != i) {
            swap(arr, i, largest);
            heapify(arr, comparator, heapSize, largest);
        }
    }

    private static <T> void buildHeap(T[] arr, Comparator<T> comparator, int heapSize) {
        for(int i = arr.length / 2 - 1; i >= 0; i--)
            heapify(arr, comparator, heapSize, i);
    }

    private static <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        Integer[] arr = {2, 6, 3, 3, 0, 19, 2, 3, 4, 5, 5};
        sort(arr, Integer::compareTo);
        System.out.println(Arrays.toString(arr));
    }
}
