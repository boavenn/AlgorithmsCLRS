package _28_MatrixOperations;

import java.util.Arrays;

public final class MatrixInversion
{
    public static double[][] inverseMatrix(double[][] A) {
        int n = A.length;
        int[] P = new int[n];
        double[][] B = new double[n][n];
        double[][] LU = LUP.LUPDecomposition(A, P);

        for (int i = 0; i < n; i++) {
            double[] temp = new double[n];
            temp[i] = 1;
            double[] res = LUP.LUPSolve(LU, P, temp);
            for (int j = 0; j < n; j++)
                B[j][i] = res[j];
        }

        return B;
    }

    private static class Example
    {
        public static void main(String[] args) {
            double[][] A = {
                    {1, 1},
                    {1, 4}
            };

            System.out.println(Arrays.deepToString(inverseMatrix(A)));
        }
    }
}
