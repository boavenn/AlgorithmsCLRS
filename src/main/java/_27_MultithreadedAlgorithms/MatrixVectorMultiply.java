package _27_MultithreadedAlgorithms;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public final class MatrixVectorMultiply
{
    private static class MainLoop extends RecursiveAction
    {
        private int[][] matrix;
        private int[] vector;
        private int[] result;
        private int size;
        private int i;
        private int j;

        public MainLoop(int[][] matrix, int[] vector, int[] result, int size, int i, int j) {
            this.matrix = matrix;
            this.vector = vector;
            this.result = result;
            this.size = size;
            this.i = i;
            this.j = j;
        }

        @Override
        protected void compute() {
            if (i == j) {
                for (int k = 0; k < size; k++)
                    result[i] += matrix[i][k] * vector[k];
            } else {
                int mid = (i + j) / 2;

                MainLoop left = new MainLoop(matrix, vector, result, size, i, mid);
                MainLoop right = new MainLoop(matrix, vector, result, size, mid + 1, j);

                left.fork();
                right.compute();

                left.join();
            }
        }
    }

    public static int[] multiply(int[][] matrix, int[] vector) {
        int size = matrix.length;
        int[] result = new int[size];
        ForkJoinPool pool = ForkJoinPool.commonPool();
        pool.invoke(new MainLoop(matrix, vector, result, size, 0, size - 1));
        return result;
    }
}
