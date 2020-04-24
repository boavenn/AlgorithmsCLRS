package _27_MultithreadedAlgorithms;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class MatrixVectorMultiply
{
    private static class MainLoop implements Callable<Object>
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
        public Object call() throws Exception {
            if (i == j) {
                for (int k = 0; k < size; k++)
                    result[i] += matrix[i][k] * vector[k];
            }
            else {
                ExecutorService executor = Executors.newSingleThreadExecutor();
                int mid = (i + j) / 2;
                executor.submit(new MainLoop(matrix, vector, result, size, i, mid)).get();
                executor.shutdown();
                new MainLoop(matrix, vector, result, size, mid + 1, j).call();
            }
            return null;
        }
    }

    public static int[] matrixVectorMultiply(int[][] matrix, int[] vector) throws ExecutionException, InterruptedException {
        int size = matrix.length;
        int[] result = new int[size];
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(new MainLoop(matrix, vector, result, size, 0, size - 1)).get();
        executor.shutdown();
        return result;
    }

    private static class Example
    {
        public static void main(String[] args) throws ExecutionException, InterruptedException {
            int[][] matrix = {
                    {1, 2, 3, 4},
                    {3, 2, 4, 1},
                    {1, 3, 2, 4},
                    {1, 4, 2, 3}
            };
            int[] vector = {1, 2, 3, 4};
            System.out.println(Arrays.toString(matrixVectorMultiply(matrix, vector)));
        }
    }
}
