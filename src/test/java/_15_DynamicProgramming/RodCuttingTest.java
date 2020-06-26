package _15_DynamicProgramming;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RodCuttingTest
{
    @Test
    void returnCorrectSolutions() {
        int[] prices = {0, 1, 5, 8, 9, 10, 17, 17, 20, 24, 30};
        assertEquals(22, RodCutting.cutRodMaxPrice(prices, 8));
        assertEquals(10, RodCutting.cutRodMaxPrice(prices, 4));

        assertIterableEquals(List.of(2, 6), RodCutting.cutRodOptimalCuts(prices, 8));
        assertIterableEquals(List.of(2, 2), RodCutting.cutRodOptimalCuts(prices, 4));
    }
}