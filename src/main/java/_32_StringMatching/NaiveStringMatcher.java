package _32_StringMatching;

public final class NaiveStringMatcher
{
    public static int match(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();
        for (int i = 0; i <= n - m; i++) {
            if (pattern.equals(text.substring(i, i + m)))
                return i;
        }
        return -1;
    }
}
