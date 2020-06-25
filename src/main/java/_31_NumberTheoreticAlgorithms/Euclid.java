package _31_NumberTheoreticAlgorithms;

import java.util.Arrays;

public final class Euclid
{
    public static int euclid(int a, int b) {
        if (b == 0)
            return a;
        return euclid(b, a % b);
    }

    public static int[] extendedEuclid(int a, int b) {
        if (b == 0)
            return new int[]{a, 1, 0};
        int[] t = extendedEuclid(b, a % b);
        return new int[]{t[0], t[2], t[1] - a / b * t[2]};
    }

    private static class Example
    {
        public static void main(String[] args) {
            System.out.println(euclid(12, 15));
            System.out.println(Arrays.toString(extendedEuclid(99, 78)));
        }
    }
}
