package _32_StringMatching;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class FiniteAutomatonMatcher
{
    private static void computeTransitionFunction(String pattern, char[] alphabet, int[][] transitionFunction) {
        int m = pattern.length();
        for (int q = 0; q <= m; q++) {
            int i = 0;
            for (char c : alphabet) {
                int k = Math.min(m + 1, q + 2);
                do {
                    k--;
                } while (!(pattern.substring(0, q) + c).endsWith(pattern.substring(0, k)));
                transitionFunction[q][i++] = k;
            }
        }
    }

    public static int match(String text, String pattern, char[] alphabet) {
        Map<Character, Integer> charPositions = new HashMap<>();
        for (int i = 0; i < alphabet.length; i++) {
            charPositions.put(alphabet[i], i);
        }

        int n = text.length();
        int m = pattern.length();
        int q = 0;
        int[][] tf = new int[m + 1][alphabet.length];
        computeTransitionFunction(pattern, alphabet, tf);
        for (int i = 0; i < n; i++) {
            q = tf[q][charPositions.get(text.charAt(i))];
            if (q == m)
                return i - m + 1;
        }
        return -1;
    }
}
