package _28_MatrixOperations;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatrixInversionTest
{
    private final double epsilon = 1e-8;

    @Test
    void returnCorrectOutput() {
        double[][] matrix = {
                {2, 1, 4},
                {5, 1, 3},
                {1, 3, 0}
        };

        double[][] expected = {
                {-9, 12, -1},
                {3, -4, 14},
                {14, -5, -3},
        };
        for (int i = 0; i < expected.length; i++) {
            for (int j = 0; j < expected[i].length; j++) {
                expected[i][j] /= 41d;
            }
        }

        double[][] result = MatrixInversion.inverse(matrix);

        for (int i = 0; i < expected.length; i++) {
            for (int j = 0; j < expected[i].length; j++) {
                assertTrue(result[i][j] < expected[i][j] + epsilon);
                assertTrue(result[i][j] > expected[i][j] - epsilon);
            }
        }
    }
}