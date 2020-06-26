package _15_DynamicProgramming;

public final class LongestCommonSubstring
{
    public static String calc(String a, String b) {
        int n = a.length();
        int m = b.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        StringBuilder str = new StringBuilder();
        int i = n;
        int j = m;
        while (i != 0 && j != 0) {
            if (dp[i - 1][j] == dp[i][j]) {
                do {
                    i--;
                } while (dp[i - 1][j] == dp[i][j]);

                while (dp[i][j - 1] == dp[i][j]) {
                    j--;
                }
            } else {
                while (dp[i][j - 1] == dp[i][j]) {
                    j--;
                }
                while (dp[i - 1][j] == dp[i][j]) {
                    i--;
                }
            }
            str.insert(0, a.charAt(i - 1));
            i--;
            j--;
        }

        return str.toString();
    }
}
