package _27_MultithreadedAlgorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

public final class SquareMatrixMultiply
{
    private static class OuterLoop extends RecursiveAction
    {
        private class InnerLoop extends RecursiveAction
        {
            private int j;

            public InnerLoop(int j) {
                this.j = j;
            }

            @Override
            protected void compute() {
                for (int k = 0; k < size; k++)
                    res[i][j] += a[i][k] * b[k][j];
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
        protected void compute() {
            List<RecursiveAction> loops = new LinkedList<>();
            for (int k = 0; k < size; k++)
                loops.add(new InnerLoop(k));
            ForkJoinTask.invokeAll(loops);
        }
    }

    public static int[][] squareMatrixMultiply(int[][] a, int[][] b) {
        int size = a.length;
        int[][] res = new int[size][size];

        List<OuterLoop> loops = new LinkedList<>();
        for (int k = 0; k < size; k++)
            loops.add(new OuterLoop(a, b, res, size, k));
        ForkJoinTask.invokeAll(loops);
        return res;
    }

    private static class Example
    {
        public static void main(String[] args) {
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