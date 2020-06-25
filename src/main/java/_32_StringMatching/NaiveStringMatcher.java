package _32_StringMatching;

public final class NaiveStringMatcher
{
    public static int match(String T, String P) {
        int n = T.length();
        int m = P.length();
        for (int i = 0; i <= n - m; i++) {
            if (P.equals(T.substring(i, i + m)))
                return i;
        }
        return -1;
    }

    public static class Example
    {
        public static void main(String[] args) {
            System.out.println(NaiveStringMatcher.match("abccbda", "ccb"));
        }
    }
}
