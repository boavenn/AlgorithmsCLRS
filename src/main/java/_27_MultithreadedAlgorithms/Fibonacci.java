package _27_MultithreadedAlgorithms;

import java.util.concurrent.*;

public final class Fibonacci
{
    private static class Fib extends RecursiveTask<Integer>
    {
        private int n;

        public Fib(int n) {
            this.n = n;
        }

        @Override
        protected Integer compute() {
            if (n <= 1)
                return n;

            Fib left = new Fib(n - 1);
            Fib right = new Fib(n - 2);

            left.fork();

            int res2 = right.compute();
            int res1 = left.join();

            return res1 + res2;
        }
    }

    public static int parallelFibonacci(int n) {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        return pool.invoke(new Fib(n));
    }

    private static class Example
    {
        public static void main(String[] args) {
            long s = System.currentTimeMillis();
            for (int i = 0; i < 30; i++)
                System.out.println(parallelFibonacci(i));
            long f = System.currentTimeMillis();
            System.out.println("Time: " + (f - s) + " ms");
        }
    }
}
