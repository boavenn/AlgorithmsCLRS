package _14_AugmentingDataStructures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static _14_AugmentingDataStructures.IntervalTree.Interval;

class IntervalTreeTest
{
    private IntervalTree<Integer> it;

    @BeforeEach
    void setUp() {
        it = new IntervalTree<>();
        int[][] intervals = {
                {16, 21}, {8, 9}, {25, 30}, {5, 8}, {15, 23},
                {17, 19}, {26, 26}, {0, 3}, {6, 10}, {19, 20}
        };
        for (int[] i : intervals) {
            it.insert(new Interval(i[0], i[1]), 0);
        }
    }

    @Test
    void intervalSearchWorkingCorrectly() {
        Interval i = it.intervalSearch(new Interval(15, 23));
        assertEquals(16, i.getLow());
        assertEquals(21, i.getHigh());

        i = it.intervalSearch(new Interval(6, 10));
        assertEquals(8, i.getLow());
        assertEquals(9, i.getHigh());
    }

    @Test
    void contains() {
        assertTrue(it.contains(new Interval(19, 20)));
        assertTrue(it.contains(new Interval(15, 23)));
        assertFalse(it.contains(new Interval(3, 9)));
    }
}