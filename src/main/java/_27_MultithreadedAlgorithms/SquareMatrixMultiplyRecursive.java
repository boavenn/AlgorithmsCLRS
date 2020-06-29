package _27_MultithreadedAlgorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public final class SquareMatrixMultiplyRecursive
{
    private static class Multiply extends RecursiveAction
    {
        private class OuterLoop extends RecursiveAction
        {
            private class InnerLoop extends RecursiveAction
            {
                private int j;

                public InnerLoop(int j) {
                    this.j = j;
                }

                @Override
                protected void compute() {
                    c[i + rowC][j + colC] += t[i][j];
                }
            }

            private int[][] t;
            private int i;

            public OuterLoop(int[][] t, int i) {
                this.t = t;
                this.i = i;
            }

            @Override
            protected void compute() {
                List<InnerLoop> loops = new LinkedList<>();
                for (int j = 0; j < size; j++)
                    loops.add(new InnerLoop(j));
                RecursiveTask.invokeAll(loops);
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
        protected void compute() {
            if (size == 1) {
                c[rowC][colC] = a[rowA][colA] * b[rowB][colB];
            } else {
                int[][] t = new int[size][size];
                int n = size / 2;
                List<RecursiveAction> pool = new LinkedList<>();
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
                pool.add(new Multiply(t, a, b, n, n, rowA + n, colA + n, rowB + n, colB + n, n));
                RecursiveTask.invokeAll(pool);

                pool.clear();
                for (int i = 0; i < size; i++)
                    pool.add(new OuterLoop(t, i));
                RecursiveTask.invokeAll(pool);
            }
        }
    }

    public static int[][] multiply(int[][] a, int[][] b) {
        int size = a.length;
        int[][] res = new int[size][size];
        ForkJoinPool pool = ForkJoinPool.commonPool();
        pool.invoke(new Multiply(res, a, b, 0, 0, 0, 0, 0, 0, size));
        return res;
    }
}
