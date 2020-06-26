package _15_DynamicProgramming;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OptimalBinarySearchTreeTest
{
    @Test
    void returnCorrectSolution() {
        double[] p = {0, 0.15, 0.10, 0.05, 0.10, 0.20};
        double[] q = {0.05, 0.10, 0.05, 0.05, 0.05, 0.10};
        assertEquals(2.75, OptimalBinarySearchTree.minimumSearchCost(p, q));
    }
}