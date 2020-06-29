package _28_MatrixOperations;

public final class MatrixInversion
{
    public static double[][] inverse(double[][] A) {
        int n = A.length;
        int[] P = new int[n];
        double[][] B = new double[n][n];
        double[][] LU = LUP.decomposition(A, P);

        for (int i = 0; i < n; i++) {
            double[] temp = new double[n];
            temp[i] = 1;
            double[] res = LUP.solve(LU, P, temp);
            for (int j = 0; j < n; j++)
                B[j][i] = res[j];
        }

        return B;
    }
}
