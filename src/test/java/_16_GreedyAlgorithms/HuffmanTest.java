package _16_GreedyAlgorithms;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HuffmanTest
{
    @Test
    void returnCorrectCodes() {
        int[] freq = {45, 13, 12, 16, 9, 5};
        char[] c = {'a', 'b', 'c', 'd', 'e', 'f'};
        Map<Character, String> result = Huffman.calcHuffmanCodes(freq, c);
        assertEquals("0", result.get('a'));
        assertEquals("101", result.get('b'));
        assertEquals("100", result.get('c'));
        assertEquals("111", result.get('d'));
        assertEquals("1101", result.get('e'));
        assertEquals("1100", result.get('f'));
    }
}