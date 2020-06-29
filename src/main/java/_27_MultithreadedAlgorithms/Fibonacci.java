package _27_MultithreadedAlgorithms;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

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

    public static int parallel(int n) {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        return pool.invoke(new Fib(n));
    }
}
