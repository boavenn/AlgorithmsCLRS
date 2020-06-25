package _32_StringMatching;

import java.util.LinkedList;
import java.util.List;

public final class KMPMatcher
{
    private static int[] computePrefixFunction(String P) {
        int m = P.length();
        int[] p = new int[m];
        int k = 0;
        for (int q = 1; q < m; q++) {
            while (k > 0 && P.charAt(k) != P.charAt(q))
                k = p[k - 1];
            if (P.charAt(k) == P.charAt(q))
                k++;
            p[q] = k;
        }
        return p;
    }

    public static List<Integer> match(String T, String P) {
        List<Integer> res = new LinkedList<>();

        int n = T.length();
        int m = P.length();
        int[] p = computePrefixFunction(P);
        int q = 0;
        for (int i = 0; i < n; i++) {
            while (q > 0 && P.charAt(q) != T.charAt(i))
                q = p[q - 1];
            if (P.charAt(q) == T.charAt(i))
                q++;
            if (q == m) {
                res.add(i - m + 1);
                q = p[q - 1];
            }
        }
        return res;
    }

    public static class Example
    {
        public static void main(String[] args) {
            String T = "abbabbaa";
            String P = "abba";
            System.out.println(KMPMatcher.match(T, P));
        }
    }
}
