package _15_DynamicProgramming;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LongestCommonSubstringTest
{
    @Test
    void returnCorrectSolutions() {
        String X = "10010101";
        String Y = "010110110";
        assertEquals("101010", LongestCommonSubstring.calc(X, Y));

        X = "bdcaba";
        Y = "abcbdab";
        assertEquals("bdab", LongestCommonSubstring.calc(X, Y));

        X = "abaabbaaa";
        Y = "babab";
        assertEquals("abab", LongestCommonSubstring.calc(X, Y));
    }
}