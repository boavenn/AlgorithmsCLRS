package _04_DivideAndConquer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FindMaxSubarrayTest
{
    private int[] arr;

    @BeforeEach
    void setUp() {
        arr = new int[]{13, -3, -25, 20, -3, -16, -23, 18, 20, -7, 12, -5, -22, 15, -4, 7};
    }

    @Test
    void recursive() {
        assertEquals(43, FindMaxSubarray.recursive(arr));
    }

    @Test
    void iterative() {
        assertEquals(43, FindMaxSubarray.iterative(arr));
    }
}