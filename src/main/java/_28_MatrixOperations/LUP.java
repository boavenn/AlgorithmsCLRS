package _28_MatrixOperations;

public final class LUP
{
    public static double[] solve(double[][] A, double[] B) {
        int[] P = new int[A.length];
        double[][] LU = decomposition(A, P);
        return solve(LU, P, B);
    }

    public static double[] solve(double[][] LU, int[] P, double[] B) {
        int n = LU.length;
        double[] Y = new double[n];
        double[] X = new double[n];

        for (int i = 0; i < n; i++) {
            double sum = 0;
            for (int j = 0; j <= i; j++) {
                sum += LU[i][j] * Y[j];
            }
            Y[i] = B[P[i]] - sum;
        }
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0;
            for (int j = i + 1; j <= n - 1; j++) {
                sum += LU[i][j] * X[j];
            }
            X[i] = (Y[i] - sum) / LU[i][i];
        }
        return X;
    }

    public static double[][] decomposition(double[][] A, int[] P) {
        int n = A.length;
        double[][] temp = new double[n][n];
        for (int i = 0; i < n; i++)
            System.arraycopy(A[i], 0, temp[i], 0, n);

        for (int i = 0; i < n; i++)
            P[i] = i;
        for (int k = 0; k < n; k++) {
            int kk = 0;
            double p = 0;
            for (int i = k; i < n; i++) {
                if (Math.abs(temp[i][k]) > p) {
                    p = Math.abs(temp[i][k]);
                    kk = i;
                }
            }
            if (p == 0)
                throw new IllegalArgumentException("Singular matrix");
            swap(P, k, kk);
            for (int i = 0; i < n; i++) {
                swap(temp, k, i, kk, i);
            }
            for (int i = k + 1; i < n; i++) {
                temp[i][k] /= temp[k][k];
                for (int j = k + 1; j < n; j++) {
                    temp[i][j] -= temp[i][k] * temp[k][j];
                }
            }
        }

        return temp;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static void swap(double[][] matrix, int i, int j, int k, int l) {
        double temp = matrix[i][j];
        matrix[i][j] = matrix[k][l];
        matrix[k][l] = temp;
    }
}
