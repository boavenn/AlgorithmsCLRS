package _15_DynamicProgramming;

import java.util.Arrays;

public final class LongestCommonSubstring
{
    public static Result lcsLength(String X, String Y) {
        int m = X.length();
        int n = Y.length();
        Result res = new Result(m, n);
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (X.charAt(i - 1) == Y.charAt(j - 1)) {
                    res.c[i][j] = res.c[i - 1][j - 1] + 1;
                    res.b[i][j] = '\\';
                }
                else if (res.c[i - 1][j] >= res.c[i][j - 1]) {
                    res.c[i][j] = res.c[i - 1][j];
                    res.b[i][j] = '|';
                }
                else {
                    res.c[i][j] = res.c[i][j - 1];
                    res.b[i][j] = '-';
                }
            }
        }
        return res;
    }

    public static void printLcs(char[][] b, String X, int i, int j) {
        if(i == 0 || j == 0)
            return;
        if(b[i][j] == '\\') {
            printLcs(b, X, i - 1, j - 1);
            System.out.print(X.charAt(i - 1));
        }
        else if(b[i][j] == '|')
            printLcs(b, X, i - 1, j);
        else
            printLcs(b, X, i, j - 1);
    }

    private static class Result
    {
        private char[][] b;
        private int[][] c;

        public Result(int m, int n) {
            this.b = new char[m + 1][n + 1];
            this.c = new int[m + 1][n + 1];
            for (int i = 0; i <= m; i++)
                c[i][0] = 0;
            for (int i = 0; i <= n; i++)
                c[0][i] = 0;
        }
    }

    private static class Example
    {
        public static void main(String[] args) {
            String X = "10010101";
            String Y = "010110110";

            Result res = lcsLength(X, Y);
            for(int i = 0; i < res.c.length; i++) {
                System.out.print(Arrays.toString(res.c[i]) + "  ");
                System.out.println(Arrays.toString(res.b[i]));
            }
            printLcs(res.b, X, X.length(), Y.length());
        }
    }
}
