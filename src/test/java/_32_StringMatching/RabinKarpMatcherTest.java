package _32_StringMatching;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RabinKarpMatcherTest
{
    @Test
    void returnCorrectOutput() {
        char[] alphabet = {'a', 'b', 'c'};
        int q = 11;
        assertEquals(2, RabinKarpMatcher.match("abccbda", "ccb", alphabet.length, q));
        assertEquals(2, RabinKarpMatcher.match("abababacaba", "ababaca", alphabet.length, q));
        assertEquals(0, RabinKarpMatcher.match("abbabbaaca", "abba", alphabet.length, q));
        assertEquals(9, RabinKarpMatcher.match("abbccabababcbabcbab", "abcb", alphabet.length, q));
    }
}