package _04_DivideAndConquer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecursiveMatrixMultiplicationTest
{
    @Test
    void calcSmallMatrices() {
        int[][] a = {
                {1, 2, 2, 3},
                {1, 2, 2, 1},
                {3, 3, 1, 2},
                {1, 2, 1, 1}
        };
        int[][] b = {
                {1, 2, 3, 1},
                {1, 1, 3, 2},
                {2, 1, 2, 3},
                {2, 3, 1, 1}
        };
        int[][] expected = {
                {13, 15, 16, 14},
                {9, 9, 14, 12},
                {12, 16, 22, 14},
                {7, 8, 12, 9},
        };

        int[][] result = RecursiveMatrixMultiplication.calc(a, b);

        for (int i = 0; i < 4; i++) {
            assertArrayEquals(expected[i], result[i]);
        }
    }

    @Test
    void calcBigMatrices() {
        int[][] a = {
                {2, 0, 0, 0, 1, 0, 4, 1},
                {0, 2, 3, 2, 2, 0, 2, 0},
                {1, 1, 1, 3, 4, 1, 0, 3},
                {1, 5, 2, 1, 4, 1, 2, 0},
                {2, 4, 3, 0, 2, 0, 4, 4},
                {0, 3, 2, 3, 0, 1, 3, 0},
                {1, 4, 2, 6, 0, 4, 0, 1},
                {3, 3, 2, 1, 3, 3, 2, 2}
        };

        int[][] b = {
                {0, 1, 0, 0, 1, 0, 1, 0},
                {3, 1, 3, 2, 0, 2, 2, 2},
                {2, 1, 1, 2, 1, 0, 2, 1},
                {0, 0, 3, 0, 4, 2, 0, 0},
                {1, 2, 0, 2, 4, 2, 0, 2},
                {6, 2, 0, 1, 0, 0, 1, 0},
                {0, 0, 5, 2, 0, 2, 0, 1},
                {1, 0, 0, 2, 0, 0, 1, 0}
        };

        int[][] expected = {
                {2, 4, 20, 12, 6, 10, 3, 6},
                {14, 9, 25, 18, 19, 16, 10, 13},
                {18, 13, 13, 19, 30, 16, 9, 11},
                {29, 18, 30, 27, 23, 24, 16, 22},
                {24, 13, 35, 34, 13, 20, 20, 19},
                {19, 7, 35, 17, 14, 18, 11, 11},
                {41, 15, 32, 18, 27, 20, 18, 10},
                {36, 20, 24, 27, 21, 18, 18, 16}
        };

        int[][] result = RecursiveMatrixMultiplication.calc(a, b);

        for (int i = 0; i < 4; i++) {
            assertArrayEquals(expected[i], result[i]);
        }
    }
}