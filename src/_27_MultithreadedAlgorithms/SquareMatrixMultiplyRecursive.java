package _27_MultithreadedAlgorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

public abstract class SquareMatrixMultiplyRecursive
{
    private static class Multiply implements Callable<Object>
    {
        private class OuterLoop implements Callable<Object>
        {
            private class InnerLoop implements Callable<Object>
            {
                private int j;

                public InnerLoop(int j) {
                    this.j = j;
                }

                @Override
                public Object call() {
                    c[i + rowC][j + colC] += t[i][j];
                    return null;
                }
            }

            private int[][] t;
            private int i;

            public OuterLoop(int[][] t, int i) {
                this.t = t;
                this.i = i;
            }

            @Override
            public Object call() throws Exception {
                ExecutorService executor = Executors.newFixedThreadPool(size);
                List<InnerLoop> innerLoops = new LinkedList<>();
                for (int j = 0; j < size; j++)
                    innerLoops.add(new InnerLoop(j));
                executor.invokeAll(innerLoops);
                executor.shutdown();
                return null;
            }
        }

        private int[][] c;
        private int[][] a;
        private int[][] b;
        private int rowC;
        private int colC;
        private int rowA;
        private int colA;
        private int rowB;
        private int colB;
        private int size;

        public Multiply(int[][] c, int[][] a, int[][] b, int rowC, int colC, int rowA, int colA, int rowB, int colB, int size) {
            this.c = c;
            this.a = a;
            this.b = b;
            this.rowC = rowC;
            this.colC = colC;
            this.rowA = rowA;
            this.colA = colA;
            this.rowB = rowB;
            this.colB = colB;
            this.size = size;
        }

        // looks a bit intimidating lol
        @Override
        public Object call() throws Exception {
            if (size == 1) {
                c[rowC][colC] = a[rowA][colA] * b[rowB][colB];
            }
            else {
                int[][] t = new int[size][size];
                int n = size / 2;
                ExecutorService executor = Executors.newFixedThreadPool(7);
                List<Callable<Object>> pool = new LinkedList<>();
                // c11, a11, b11
                pool.add(new Multiply(c, a, b, rowC, colC, rowA, colA, rowB, colB, n));
                // c12, a11, b12
                pool.add(new Multiply(c, a, b, rowC, colC + n, rowA, colA, rowB, colB + n, n));
                // c21, a21, b11
                pool.add(new Multiply(c, a, b, rowC + n, colC, rowA + n, colA, rowB, colB, n));
                // c22, a21, b12
                pool.add(new Multiply(c, a, b, rowC + n, colC + n, rowA + n, colA, rowB, colB + n, n));
                // t11, a12, b21
                pool.add(new Multiply(t, a, b, 0, 0, rowA, colA + n, rowB + n, colB, n));
                // t12, a12, b22
                pool.add(new Multiply(t, a, b, 0, n, rowA, colA + n, rowB + n, colB + n, n));
                // t21, a22, b21
                pool.add(new Multiply(t, a, b, n, 0, rowA + n, colA + n, rowB + n, colB, n));
                // t22, a22, b22
                new Multiply(t, a, b, n, n, rowA + n, colA + n, rowB + n, colB + n, n).call();
                executor.invokeAll(pool);

                pool.clear();
                for (int i = 0; i < size; i++)
                    pool.add(new OuterLoop(t, i));
                executor.invokeAll(pool);
                executor.shutdown();
            }
            return null;
        }
    }

    public static int[][] squareMatrixMultiplyRecursive(int[][] a, int[][] b) throws ExecutionException, InterruptedException {
        int size = a.length;
        int[][] res = new int[size][size];
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(new Multiply(res, a, b, 0, 0, 0, 0, 0, 0, size)).get();
        executor.shutdown();
        return res;
    }

    private static class Example
    {
        public static void main(String[] args) throws ExecutionException, InterruptedException {
            int[][] a = {
                    {1, 2, 2, 3},
                    {1, 2, 2, 1},
                    {3, 3, 1, 2},
                    {1, 2, 1, 1}
            };
            int[][] b = {
                    {1, 2, 3, 1},
                    {1, 1, 3, 2},
                    {2, 1, 2, 3},
                    {2, 3, 1, 1}
            };
            printMatrix(squareMatrixMultiplyRecursive(a, b));
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
