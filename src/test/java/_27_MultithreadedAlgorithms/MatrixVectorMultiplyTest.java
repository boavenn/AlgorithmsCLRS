package _27_MultithreadedAlgorithms;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatrixVectorMultiplyTest
{
    @Test
    void returnCorrectOutput() {
        int[][] matrix = {
                {1, 2, 3, 4},
                {3, 2, 4, 1},
                {1, 3, 2, 4},
                {1, 4, 2, 3}
        };
        int[] vector = {1, 2, 3, 4};

        int[] expected = {30, 23, 29, 27};

        assertArrayEquals(expected, MatrixVectorMultiply.multiply(matrix, vector));
    }
}