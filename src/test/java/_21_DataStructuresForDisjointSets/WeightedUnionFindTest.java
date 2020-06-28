package _21_DataStructuresForDisjointSets;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeightedUnionFindTest
{
    @Test
    void constructsSetsCorrectly() {
        WeightedUnionFind<Character> sets = new WeightedUnionFind<>();
        for (Character i : new Character[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j'})
            sets.makeSet(i);

        sets.union('a', 'd');
        sets.union('g', 'h');
        sets.union('b', 'i');
        sets.union('e', 'h');
        sets.union('e', 'g');
        sets.union('c', 'j');
        sets.union('a', 'b');
        sets.union('b', 'd');
        sets.union('d', 'i');
        sets.union('b', 'j');

        assertTrue(sets.connected('a', 'j'));
        assertTrue(sets.connected('e', 'h'));
        assertFalse(sets.connected('f', 'c'));
    }
}