package _30_PolynomialsAndTheFFT;

public class Complex
{
    private final double re;
    private final double im;

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    @Override
    public String toString() {
        return "(" + re + ", " + im + ")";
    }

    public Complex add(Complex c) {
        double r = this.re + c.re;
        double i = this.im + c.im;
        return new Complex(r, i);
    }

    public Complex sub(Complex c) {
        double r = this.re - c.re;
        double i = this.im - c.im;
        return new Complex(r, i);
    }

    public Complex mult(Complex c) {
        double r = this.re * c.re - this.im * c.im;
        double i = this.re * c.im + this.im * c.re;
        return new Complex(r, i);
    }

    public double re() {
        return re;
    }

    public double im() {
        return im;
    }
}
