package _27_MultithreadedAlgorithms;

import java.util.Comparator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/*
It's a lot slower than normal merge sort in this implementation, normally
you'd want to have a base case where if the size of the array is sufficiently
small you switch to some other sorting algorithm like insertion or quick sort
 */

public final class MergeSort
{
    private static class MSort<T> extends RecursiveAction
    {
        private T[] array;
        private int begin;
        private int end;
        private T[] resultArray;
        private int resultArrayBegin;
        private Comparator<T> comp;

        public MSort(T[] array, int begin, int end, T[] resultArray, int resultArrayBegin, Comparator<T> comp) {
            this.array = array;
            this.begin = begin;
            this.end = end;
            this.resultArray = resultArray;
            this.resultArrayBegin = resultArrayBegin;
            this.comp = comp;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void compute() {
            int n = end - begin + 1;
            if (n == 1)
                resultArray[resultArrayBegin] = array[begin];
            else {
                T[] auxArray = (T[]) new Object[n];
                int mid = (begin + end) / 2;
                int auxBegin = mid - begin + 1;

                MSort<T> left = new MSort<>(array, begin, mid, auxArray, 0, comp);
                MSort<T> right = new MSort<>(array, mid + 1, end, auxArray, auxBegin, comp);

                right.fork();
                left.compute();

                right.join();

                Merge<T> merge = new Merge<>(auxArray, 0, auxBegin - 1, auxBegin, n - 1,
                        resultArray, resultArrayBegin, comp);
                merge.compute();
                System.arraycopy(resultArray, resultArrayBegin, array, begin, n);
            }
        }
    }

    private static class Merge<T> extends RecursiveAction
    {
        private T[] array;
        private int begin1;
        private int end1;
        private int begin2;
        private int end2;
        private T[] resultArray;
        private int beginRes;
        private Comparator<T> comp;

        public Merge(T[] array, int begin1, int end1, int begin2, int end2,
                     T[] resultArray, int beginRes, Comparator<T> comp) {
            this.array = array;
            this.begin1 = begin1;
            this.end1 = end1;
            this.begin2 = begin2;
            this.end2 = end2;
            this.resultArray = resultArray;
            this.beginRes = beginRes;
            this.comp = comp;
        }

        @Override
        protected void compute() {
            int n1 = end1 - begin1 + 1;
            int n2 = end2 - begin2 + 1;
            // consider only the bigger subarray
            if (n1 < n2) {
                int temp = begin1;
                begin1 = begin2;
                begin2 = temp;
                temp = end1;
                end1 = end2;
                end2 = temp;
                n1 = n2;
            }
            if (n1 != 0) {
                int mid1 = (begin1 + end1) / 2;
                int mid2 = binarySearch(array[mid1], array, begin2, end2, comp);
                int mid3 = beginRes + (mid1 - begin1) + (mid2 - begin2);

                resultArray[mid3] = array[mid1];

                Merge<T> left = new Merge<>(array, begin1, mid1 - 1, begin2, mid2 - 1, resultArray, beginRes, comp);
                Merge<T> right = new Merge<>(array, mid1 + 1, end1, mid2, end2, resultArray, mid3 + 1, comp);

                left.fork();
                right.compute();

                left.join();
            }
        }
    }

    private static <T> int binarySearch(T searchedValue, T[] array, int begin, int end, Comparator<T> comp) {
        int low = begin;
        int high = Math.max(begin, end + 1);
        while (low < high) {
            int mid = (low + high) / 2;
            if (comp.compare(searchedValue, array[mid]) <= 0)
                high = mid;
            else
                low = mid + 1;
        }
        return high;
    }

    @SuppressWarnings("unchecked")
    public static <T> void sort(T[] array, Comparator<T> comp) {
        T[] auxArray = (T[]) new Object[array.length];
        ForkJoinPool pool = ForkJoinPool.commonPool();
        pool.invoke(new MSort<>(array, 0, array.length - 1, auxArray, 0, comp));
    }
}
