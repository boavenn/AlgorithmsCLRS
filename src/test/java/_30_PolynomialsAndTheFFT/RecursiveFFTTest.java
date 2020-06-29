package _30_PolynomialsAndTheFFT;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RecursiveFFTTest
{
    private final double epsilon = 1e-8;

    @Test
    void returnCorrectOutput() {
        Complex[] input = {
                new Complex(1, 2),
                new Complex(2, 0),
                new Complex(3, 2),
                new Complex(4, 1)
        };

        Complex[] expected = {
                new Complex(10, 5),
                new Complex(-3, 2),
                new Complex(-2, 3),
                new Complex(-1, -2)
        };

        Complex[] result = RecursiveFFT.fft(input);
        for (int i = 0; i < result.length; i++) {
            assertTrue(result[i].re() < expected[i].re() + epsilon);
            assertTrue(result[i].re() > expected[i].re() - epsilon);
            assertTrue(result[i].im() < expected[i].im() + epsilon);
            assertTrue(result[i].im() > expected[i].im() - epsilon);
        }
    }
}