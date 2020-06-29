package _29_LinearProgramming;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimplexTest
{
    @Test
    void returnCorrectOutput() {
        double[][] A = {
                {1, 1, 3},
                {2, 2, 5},
                {4, 1, 2}
        };
        double[] b = {30, 24, 36};
        double[] c = {3, 1, 2};

        double[] expected = {8, 4, 0, 18, 0, 0};

        assertArrayEquals(expected, Simplex.solve(A, b, c));
    }
}