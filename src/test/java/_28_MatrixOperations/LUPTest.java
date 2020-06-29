package _28_MatrixOperations;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LUPTest
{
    private final double epsilon = 1e-8;

    @Test
    void returnCorrectOutput() {
        double[][] A = {
                {5, 6, 3},
                {0, 0.8, -0.6},
                {0, 0, 2.5}
        };
        double[] B = {8, 1.4, 1.5};
        double[] expected = {-1.4, 2.2, 0.6};
        double[] result = LUP.solve(A, B);
        for (int i = 0; i < expected.length; i++) {
            assertTrue(result[i] < expected[i] + epsilon);
            assertTrue(result[i] > expected[i] - epsilon);
        }

        A = new double[][]{
                {1, 0, 0},
                {4, 1, 0},
                {-6, 5, 1}
        };
        B = new double[]{3, 14, -7};
        expected = new double[]{3.0, 2.0, 1.0};
        result = LUP.solve(A, B);
        for (int i = 0; i < expected.length; i++) {
            assertTrue(result[i] < expected[i] + epsilon);
            assertTrue(result[i] > expected[i] - epsilon);
        }

        A = new double[][]{
                {2, 2},
                {1, 3}
        };
        B = new double[]{8, 6};
        expected = new double[]{3.0, 1.0};
        result = LUP.solve(A, B);
        for (int i = 0; i < expected.length; i++) {
            assertTrue(result[i] < expected[i] + epsilon);
            assertTrue(result[i] > expected[i] - epsilon);
        }
    }
}