package _30_PolynomialsAndTheFFT;

public final class RecursiveFFT
{
    public static Complex[] fft(Complex[] a) {
        int n = a.length;
        if (n == 1)
            return a;

        Complex[] a0 = new Complex[n / 2];
        Complex[] a1 = new Complex[n / 2];
        for (int i = 0; i < n / 2; i++) {
            a0[i] = a[2 * i];
            a1[i] = a[2 * i + 1];
        }

        Complex[] y0 = fft(a0);
        Complex[] y1 = fft(a1);

        double angle = -2 * Math.PI / n;
        Complex w = new Complex(1, 0);
        Complex wn = new Complex(Math.cos(angle), Math.sin(angle));
        Complex[] y = new Complex[n];
        for (int i = 0; i < n / 2; i++) {
            y[i] = y0[i].add(w.mult(y1[i]));
            y[i + n / 2] = y0[i].sub(w.mult(y1[i]));
            w = w.mult(wn);
        }
        return y;
    }
}
