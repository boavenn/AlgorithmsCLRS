package _32_StringMatching;

import java.util.LinkedList;
import java.util.List;

public final class KMPMatcher
{
    private static int[] computePrefixFunction(String pattern) {
        int patternLen = pattern.length();
        int[] prefixFunction = new int[patternLen];
        int i = 0;
        for (int j = 1; j < patternLen; j++) {
            while (i > 0 && pattern.charAt(i) != pattern.charAt(j)) {
                i = prefixFunction[i - 1];
            }
            if (pattern.charAt(i) == pattern.charAt(j)) {
                i++;
            }
            prefixFunction[j] = i;
        }
        return prefixFunction;
    }

    public static List<Integer> match(String text, String pattern) {
        List<Integer> result = new LinkedList<>();
        int patternLen = pattern.length();
        int textLen = text.length();
        int[] prefixFunction = computePrefixFunction(pattern);
        int j = 0;
        for (int i = 0; i < textLen; i++) {
            while (j > 0 && pattern.charAt(j) != text.charAt(i)) {
                j = prefixFunction[j - 1];
            }
            if (pattern.charAt(j) == text.charAt(i)) {
                j++;
            }
            if (j == patternLen) {
                result.add(i - patternLen + 1);
                j = prefixFunction[j - 1];
            }
        }
        return result;
    }
}
