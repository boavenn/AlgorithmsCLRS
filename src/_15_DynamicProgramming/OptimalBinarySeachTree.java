package _15_DynamicProgramming;

import java.util.Formatter;

public abstract class OptimalBinarySeachTree
{
    public static Result optimalBST(double[] p, double[] q, int n) {
        Result res = new Result(n);
        double[][] w = new double[n + 2][n + 1];
        for (int i = 1; i <= n + 1; i++) {
            res.e[i][i - 1] = q[i - 1];
            w[i][i - 1] = q[i - 1];
        }
        for (int l = 1; l <= n; l++) {
            for (int i = 1; i <= n - l + 1; i++) {
                int j = i + l - 1;
                res.e[i][j] = Double.MAX_VALUE;
                w[i][j] = w[i][j - 1] + p[j] + q[j];
                for (int r = i; r <= j; r++) {
                    double t = res.e[i][r - 1] + res.e[r + 1][j] + w[i][j];
                    if (t < res.e[i][j]) {
                        res.e[i][j] = t;
                        res.root[i][j] = r;
                    }
                }
            }
        }
        return res;
    }

    public static void constructOptimalBST(int[][] root, int i, int j, int last) {
        if(i > j) {
            if(i - j == 1) {
                if(j < last)
                    System.out.println("d" + j + " is the left child of k" + last);
                else
                    System.out.println("d" + j + " is the right child of k" + last);
            }
            return;
        }

        if(last == 0)
            System.out.println("k" + root[i][j] + " is the root");
        else if(j < last)
            System.out.println("k" + root[i][j] + " is the left child of k" + last);
        else
            System.out.println("k" + root[i][j] + " is the right child of k" + last);
        constructOptimalBST(root, i, root[i][j] - 1, root[i][j]);
        constructOptimalBST(root, root[i][j] + 1, j, root[i][j]);
    }

    private static class Result
    {
        double[][] e;
        int[][] root;

        public Result(int n) {
            this.e = new double[n + 2][n + 1];
            this.root = new int[n + 2][n + 1];
        }
    }

    private static class Example
    {
        public static void main(String[] args) {
            double[] p = {0, 0.15, 0.10, 0.05, 0.10, 0.20};
            double[] q = {0.05, 0.10, 0.05, 0.05, 0.05, 0.10};
            Result res = optimalBST(p, q, 5);
            for (int i = 0; i < res.e.length; i++)
                printArr(res.e[i]);
            System.out.println();
            constructOptimalBST(res.root, 1, 5, 0);
        }
    }

    private static void printArr(double[] arr) {
        StringBuilder str = new StringBuilder();
        str.append("[");
        Formatter fmt = new Formatter();
        for (double d : arr)
            fmt.format("%-4.4s, ", d);
        str.append(fmt);
        int i = str.lastIndexOf(",");
        str.replace(i, i + 2, "]");
        System.out.println(str);
    }
}
