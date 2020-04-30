package _30_PolynomialsAndTheFFT;

import java.util.Arrays;

public final class IterativeFFT
{
    private static int rev(int x, int n) {
        int log2n = (int) (Math.log(n) / Math.log(2));
        int b = 0;
        for (int i = 0; i < log2n; i++) {
            b <<= 1;
            b |= (x & 1);
            x >>= 1;
        }
        return b;
    }

    private static Complex[] bitReverseCopy(Complex[] a) {
        int n = a.length;
        Complex[] A = new Complex[n];
        for (int i = 0; i < n; i++)
            A[rev(i, n)] = a[i];
        return A;
    }

    public static Complex[] fft(Complex[] a) {
        Complex[] A = bitReverseCopy(a);
        int n = a.length;
        int log2n = (int) (Math.log(n) / Math.log(2));
        for (int i = 1; i <= log2n; i++) {
            int m = 1 << i; // 2^i
            double angle = -2 * Math.PI / m;
            Complex wm = new Complex(Math.cos(angle), Math.sin(angle));
            for (int j = 0; j < n; j += m) {
                Complex w = new Complex(1, 0);
                for (int k = 0; k < m / 2; k++) {
                    Complex t = w.mult(A[j + k + m / 2]);
                    Complex u = A[j + k];
                    A[j + k] = u.add(t);
                    A[j + k + m / 2] = u.sub(t);
                    w = w.mult(wm);
                }
            }
        }
        return A;
    }
}
