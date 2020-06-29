package _32_StringMatching;

public final class RabinKarpMatcher
{
    public static int match(String text, String pattern, int alphLength, int q) {
        int n = text.length();
        int m = pattern.length();
        int h = 1;
        for (int i = 0; i < m - 1; i++) {
            h = (h * alphLength) % q;
        }
        int p = 0;
        int t = 0;
        for (int i = 0; i < m; i++) {
            p = (alphLength * p + pattern.charAt(i)) % q;
            t = (alphLength * t + text.charAt(i)) % q;
        }
        for (int i = 0; i < n - m; i++) {
            if (p == t) {
                if (pattern.substring(0, m).equals(text.substring(i, i + m)))
                    return i;
            }
            if (i < n - m) {
                t = (alphLength * (t - text.charAt(i) * h) + text.charAt(i + m)) % q;
                if (t < 0)
                    t += q;
            }
        }
        return -1;
    }
}
