package _27_MultithreadedAlgorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

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

    public static int[][] multiply(int[][] a, int[][] b) {
        int size = a.length;
        int[][] res = new int[size][size];

        List<OuterLoop> loops = new LinkedList<>();
        for (int k = 0; k < size; k++)
            loops.add(new OuterLoop(a, b, res, size, k));
        ForkJoinTask.invokeAll(loops);
        return res;
    }
}