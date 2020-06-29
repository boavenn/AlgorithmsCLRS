package _29_LinearProgramming;

/**
 * Based on <a href=https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/LinearProgramming.java>algs4</a>
 */
public final class Simplex
{
    /*
     * Represents simplex tableaux
     * A I b
     * c 0 0
     * of size (m + 1) x (n + m + 1)
     */
    private static double[][] t;
    private static int n; // Number of variables
    private static int m; // Number of constraints
    private static int[] util; // To return solution

    /**
     * @param A coefficients of the constraints in the slack form (their negative values)
     * @param b constants in equality contraints
     * @param c coefficients of the objective function
     */
    private static void initTableaux(double[][] A, double[] b, double[] c) {
        m = b.length;
        n = c.length;
        t = new double[m + 1][n + m + 1];
        util = new int[m];
        for (int i = 0; i < n; i++)
            util[i] = i + n;

        // initialize A
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                t[i][j] = A[i][j];

        // initialize I
        for (int i = n; i < m + n; i++)
            t[i - n][i] = 1;

        // initialize b
        for (int i = 0; i < m; i++)
            t[i][m + n] = b[i];

        // initialize c
        for (int i = 0; i < n; i++)
            t[m][i] = c[i];
    }

    /**
     * @return index of the first column whose objective function coefficient is positive
     */
    private static int bland() {
        for (int q = 0; q < m + n; q++)
            if (t[m][q] > 0)
                return q;
        return -1;
    }

    /**
     * @param q column index
     * @return index of the leaving row
     */
    private static int minRatio(int q) {
        int p = -1;
        for (int i = 0; i < m; i++) {
            if (t[i][q] <= 0) {
                continue;
            }
            if (p == -1)
                p = i;
            else if ((t[i][m + n] / t[i][q]) < (t[p][m + n] / t[p][q]))
                p = i;
        }
        return p;
    }

    /**
     * Pivot on element row p, column q, it's simply
     * doing a gaussian elimination on row p
     *
     * @param p index of the leaving row
     * @param q index of the column
     */
    private static void pivot(int p, int q) {
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= m + n; j++) {
                if (i != p && j != q)
                    t[i][j] -= t[p][j] * t[i][q] / t[p][q];
            }
        }
        for (int i = 0; i <= m; i++) {
            if (i != p)
                t[i][q] = 0;
        }
        for (int i = 0; i <= m + n; i++) {
            if (i != q)
                t[p][i] /= t[p][q];
        }
        t[p][q] = 1;
    }

    /**
     * Executes simplex aglorithm
     */
    private static void solve() {
        while (true) {
            int q = bland();
            if (q == -1)
                break;
            int p = minRatio(q);
            if (p == -1)
                throw new ArithmeticException("Linear program is unbounded");
            pivot(p, q);
            util[p] = q;
        }
    }

    /**
     * @param A coefficients of the constraints in the slack form (negatives)
     * @param b constants in equality contraints
     * @param c coefficients of the objective function
     * @return result in array [x_1, x_2, ..., x_m, x_m+1, ..., x_m+n]
     */
    public static double[] solve(double[][] A, double[] b, double[] c) {
        initTableaux(A, b, c);
        solve();

        double[] solution = new double[m + n];
        for (int i = 0; i < m; i++)
            solution[util[i]] = t[i][n + m];
        return solution;
    }
}
