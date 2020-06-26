package _16_GreedyAlgorithms;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActivitySelectorTest
{
    @Test
    void returnCorrectSolution() {
        int[] s = {1, 3, 0, 5, 3, 5, 6, 8, 8, 2, 12};
        int[] f = {4, 5, 6, 7, 9, 9, 10, 11, 12, 14, 16};
        assertIterableEquals(List.of(0, 3, 7, 10), ActivitySelector.greedyActivitySelector(s, f));
    }
}