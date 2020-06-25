package _32_StringMatching;

public final class RabinKarpMatcher
{
    public static int match(String T, String P, int d, int q) {
        int n = T.length();
        int m = P.length();
        int h = 1;
        for (int i = 0; i < m - 1; i++) {
            h = (h * d) % q;
        }
        int p = 0;
        int t = 0;
        for (int i = 0; i < m; i++) {
            p = (d * p + P.charAt(i)) % q;
            t = (d * t + T.charAt(i)) % q;
        }
        for (int i = 0; i < n - m; i++) {
            if (p == t) {
                if (P.substring(0, m).equals(T.substring(i, i + m)))
                    return i;
            }
            if (i < n - m) {
                t = (d * (t - T.charAt(i) * h) + T.charAt(i + m)) % q;
                if (t < 0)
                    t += q;
            }
        }
        return -1;
    }

    public static class Example
    {
        public static void main(String[] args) {
            String T = "abababacaba";
            String P = "ababaca";
            char[] a = {'a', 'b', 'c'};
            int q = 11;
            System.out.println(RabinKarpMatcher.match(T, P, a.length, q));
        }
    }
}
