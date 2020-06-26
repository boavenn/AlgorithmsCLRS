package _15_DynamicProgramming;

import java.util.LinkedList;
import java.util.List;

public final class RodCutting
{
    public static int cutRodMaxPrice(int[] prices, int n) {
        int[] results = new int[n + 1];
        results[0] = 0;
        for (int i = 1; i <= n; i++) {
            int max = Integer.MIN_VALUE;
            for (int j = 1; j <= i; j++) {
                max = Math.max(max, prices[j] + results[i - j]);
            }
            results[i] = max;
        }
        return results[n];
    }

    public static List<Integer> cutRodOptimalCuts(int[] prices, int n) {
        int[] results = new int[n + 1];
        int[] solutions = new int[n + 1];
        results[0] = 0;
        for (int i = 1; i <= n; i++) {
            int max = Integer.MIN_VALUE;
            for (int j = 1; j <= i; j++) {
                if (prices[j] + results[i - j] > max) {
                    max = prices[j] + results[i - j];
                    solutions[i] = j;
                }
            }
            results[i] = max;
        }

        List<Integer> result = new LinkedList<>();
        int i = n;
        while (solutions[i] != i) {
            result.add(solutions[i]);
            i -= solutions[i];
        }
        result.add(i);
        return result;
    }
}
