package _32_StringMatching;

public final class FiniteAutomatonMatcher
{
    private static void computeTransitionFunction(String P, char[] a, int[][] tf) {
        int m = P.length();
        for (int q = 0; q <= m; q++) {
            int i = 0;
            for (char c : a) {
                int k = Math.min(m + 1, q + 2);
                do {
                    k--;
                } while (!(P.substring(0, q) + c).endsWith(P.substring(0, k)));
                tf[q][i++] = k;
            }
        }
    }

    private static int charPosInAlphabet(char[] a, char c) {
        int i = 0;
        while (a[i] != c)
            i++;
        return i;
    }

    public static int match(String T, String P, char[] a) {
        int n = T.length();
        int m = P.length();
        int q = 0;
        int[][] tf = new int[m + 1][a.length];
        computeTransitionFunction(P, a, tf);
        for (int i = 0; i < n; i++) {
            q = tf[q][charPosInAlphabet(a, T.charAt(i))];
            if (q == m)
                return i - m + 1;
        }
        return -1;
    }

    public static class Example
    {
        public static void main(String[] args) {
            String T = "abababacaba";
            String P = "ababaca";
            char[] alphabet = {'a', 'b', 'c'};
            System.out.println(FiniteAutomatonMatcher.match(T, P, alphabet));
        }
    }
}
