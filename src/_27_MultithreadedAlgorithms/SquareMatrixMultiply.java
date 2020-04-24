package _27_MultithreadedAlgorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class SquareMatrixMultiply
{
    private static class OuterLoop implements Callable<Object>
    {
        private class InnerLoop implements Callable<Object>
        {
            private int j;

            public InnerLoop(int j) {
                this.j = j;
            }

            @Override
            public Object call() {
                for (int k = 0; k < size; k++)
                    res[i][j] += a[i][k] * b[k][j];
                return null;
            }
        }

        private int[][] a;
        private int[][] b;
        private int[][] res;
        private int size;
        private int i;

        public OuterLoop(int[][] a, int[][] b, int[][] res, int size, int i) {
            this.a = a;
            this.b = b;
            this.res = res;
            this.size = size;
            this.i = i;
        }

        @Override
        public Object call() throws Exception {
            ExecutorService executor = Executors.newFixedThreadPool(size);
            List<InnerLoop> pool = new LinkedList<>();
            for (int k = 0; k < size; k++)
                pool.add(new InnerLoop(k));
            executor.invokeAll(pool);
            executor.shutdown();
            return null;
        }
    }

    public static int[][] squareMatrixMultiply(int[][] a, int[][] b) throws InterruptedException {
        int size = a.length;
        int[][] res = new int[size][size];
        ExecutorService executor = Executors.newFixedThreadPool(size);

        List<OuterLoop> pool = new LinkedList<>();
        for (int k = 0; k < size; k++)
            pool.add(new OuterLoop(a, b, res, size, k));
        executor.invokeAll(pool);
        executor.shutdown();
        return res;
    }

    private static class Example
    {
        public static void main(String[] args) throws InterruptedException {
            int[][] a = {{1, 2}, {4, 3}};
            int[][] b = {{4, 1}, {2, 4}};
            printMatrix(squareMatrixMultiply(a, b));
        }
    }

    private static void printMatrix(int[][] matrix) {
        int len = matrix.length;
        for (int[] ints : matrix) {
            for (int j = 0; j < len; j++)
                System.out.print(ints[j] + " ");
            System.out.println();
        }
    }
}