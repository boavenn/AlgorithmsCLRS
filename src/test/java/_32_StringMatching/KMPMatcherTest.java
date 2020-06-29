package _32_StringMatching;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KMPMatcherTest
{
    @Test
    void returnCorrectOutput() {
        assertIterableEquals(List.of(2), KMPMatcher.match("abccbda", "ccb"));
        assertIterableEquals(List.of(2), KMPMatcher.match("abababacaba", "ababaca"));
        assertIterableEquals(List.of(0, 3), KMPMatcher.match("abbabbaaca", "abba"));
        assertIterableEquals(List.of(9, 13), KMPMatcher.match("abbccabababcbabcbab", "abcb"));
    }
}