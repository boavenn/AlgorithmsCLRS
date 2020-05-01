package _31_NumberTheoreticAlgorithms;

import java.util.Arrays;

public final class ModularLinearEquationSolver
{
    public static int[] solve(int a, int b, int n) {
        int[] t = Euclid.extendedEuclid(a, n);
        int[] res = new int[t[0]];
        if (b % t[0] == 0) {
            int x0 = (t[1] * b / t[0]) % n;
            if (x0 < 0)
                x0 += n;
            for (int i = 0; i < t[0]; i++)
                res[i] = (x0 + i * n / t[0]) % n;
        }
        return res;
    }

    public static class Example
    {
        public static void main(String[] args) {
            System.out.println(Arrays.toString(ModularLinearEquationSolver.solve(14, 30, 100)));
        }
    }
}
