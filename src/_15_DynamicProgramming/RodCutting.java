package _15_DynamicProgramming;

import java.util.Arrays;

public final class RodCutting
{
    // recursion with memoization
    public static int memoizedCutRod(int[] p, int n) {
        int[] r = new int[n + 1];
        Arrays.fill(r, Integer.MIN_VALUE);
        return memoizedCutRodAux(p, n, r);
    }

    private static int memoizedCutRodAux(int[] p, int n, int[] r) {
        if (r[n] >= 0)
            return r[n];
        int q;
        if (n == 0)
            q = 0;
        else {
            q = Integer.MIN_VALUE;
            for (int i = 1; i <= n; i++)
                q = Math.max(q, p[i] + memoizedCutRodAux(p, n - i, r));
            r[n] = q;
        }
        return q;
    }

    // dynamic programming
    public static int bottomUpCutRod(int[] p, int n) {
        int[] r = new int[n + 1];
        r[0] = 0;
        for (int j = 1; j <= n; j++) {
            int q = Integer.MIN_VALUE;
            for (int i = 1; i <= j; i++)
                q = Math.max(q, p[i] + r[j - i]);
            r[j] = q;
        }
        return r[n];
    }

    public static Result extendedBottomUpCutRod(int[] p, int n) {
        Result res = new Result(n);
        res.r[0] = 0;
        for (int j = 1; j <= n; j++) {
            int q = Integer.MIN_VALUE;
            for (int i = 1; i <= j; i++) {
                if (q < p[i] + res.r[j - i]) {
                    q = p[i] + res.r[j - i];
                    res.s[j] = i;
                }
            }
            res.r[j] = q;
        }
        return res;
    }

    public static void printCutRodSolution(int[] p, int n) {
        Result res = extendedBottomUpCutRod(p, n);
        while(n > 0) {
            System.out.print(res.s[n] + " ");
            n -= res.s[n];
        }
    }

    private static class Result
    {
        private int[] r;
        private int[] s;

        public Result(int n) {
            this.r = new int[n + 1];
            this.s = new int[n + 1];
        }
    }

    private static class Example
    {
        public static void main(String[] args) {
            int[] p = {0, 1, 5, 8, 9, 10, 17, 17, 20, 24, 30};
            System.out.println(memoizedCutRod(p, 8));
            System.out.println(bottomUpCutRod(p, 8));
            printCutRodSolution(p, 8);
        }
    }
}
