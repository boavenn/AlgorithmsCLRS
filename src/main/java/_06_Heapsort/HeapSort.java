package _06_Heapsort;

import java.util.Comparator;

public final class HeapSort
{
    public static <T> void sort(T[] arr, Comparator<T> comp) {
        int heapSize = arr.length;
        buildHeap(arr, comp, heapSize);
        for (int i = arr.length - 1; i >= 1; i--) {
            swap(arr, 0, i);
            heapSize--;
            heapify(arr, comp, heapSize, 0);
        }
    }

    private static <T> void heapify(T[] arr, Comparator<T> comp, int heapSize, int i) {
        int left = 2 * i + 1;
        int right = left + 1;
        int largest;

        if (left < heapSize && comp.compare(arr[left], arr[i]) > 0)
            largest = left;
        else
            largest = i;

        if (right < heapSize && comp.compare(arr[right], arr[largest]) > 0)
            largest = right;

        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, comp, heapSize, largest);
        }
    }

    private static <T> void buildHeap(T[] arr, Comparator<T> comp, int heapSize) {
        for (int i = arr.length / 2 - 1; i >= 0; i--)
            heapify(arr, comp, heapSize, i);
    }

    private static <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
