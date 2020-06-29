package _32_StringMatching;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FiniteAutomatonMatcherTest
{
    @Test
    void returnCorrectOutput() {
        char[] alphabet = {'a', 'b', 'c'};
        assertEquals(2, FiniteAutomatonMatcher.match("abccbda", "ccb", alphabet));
        assertEquals(2, FiniteAutomatonMatcher.match("abababacaba", "ababaca", alphabet));
        assertEquals(0, FiniteAutomatonMatcher.match("abbabbaaca", "abba", alphabet));
        assertEquals(9, FiniteAutomatonMatcher.match("abbccabababcbabcbab", "abcb", alphabet));
    }
}