package _27_MultithreadedAlgorithms;

import java.util.concurrent.*;

public abstract class Fibonacci
{
    private static class CalcFib implements Callable<Integer>
    {
        private final int n;

        public CalcFib(int n) {
            this.n = n;
        }

        @Override
        public Integer call() throws Exception {
            if (n <= 1)
                return n;

            ExecutorService executor = Executors.newSingleThreadExecutor();
            int x = executor.submit(new CalcFib(n - 1)).get();
            int y = new CalcFib(n - 2).call();
            executor.shutdown();
            return x + y;
        }
    }

    public static int parallelFibonacci(int n) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> x = executor.submit(new CalcFib(n));
        executor.shutdown();
        return x.get();
    }

    private static class Example
    {
        public static void main(String[] args) throws ExecutionException, InterruptedException {
            long s = System.currentTimeMillis();
            for (int i = 0; i < 20; i++)
                System.out.println(parallelFibonacci(i));
            long f = System.currentTimeMillis();
            System.out.println("Time: " + (f - s) + " ms");
        }
    }
}
