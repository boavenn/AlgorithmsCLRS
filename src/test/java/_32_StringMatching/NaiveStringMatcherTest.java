package _32_StringMatching;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NaiveStringMatcherTest
{
    @Test
    void returnCorrectOutput() {
        assertEquals(2, NaiveStringMatcher.match("abccbda", "ccb"));
        assertEquals(2, NaiveStringMatcher.match("abababacaba", "ababaca"));
        assertEquals(0, NaiveStringMatcher.match("abbabbaa", "abba"));
        assertEquals(9, NaiveStringMatcher.match("abbccabababcbabcbab", "abcb"));
    }
}