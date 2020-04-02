package _04_DivideAndConquer;

/*
Works only if size of matrix A and B is n*n where n = 2^k (k > 1)
 */
public abstract class RecursiveMatrixMultiplication
{
    public static int[][] calc(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n];

        if(n == 1)
            C[0][0] = A[0][0] * B[0][0];
        else {
            int size = n / 2;

            int[][] a11 = divideMatrix(A, size, 0, 0);
            int[][] a12 = divideMatrix(A, size, 0, size);
            int[][] a21 = divideMatrix(A, size, size, 0);
            int[][] a22 = divideMatrix(A, size, size, size);

            int[][] b11 = divideMatrix(B, size, 0, 0);
            int[][] b12 = divideMatrix(B, size, 0, size);
            int[][] b21 = divideMatrix(B, size, size, 0);
            int[][] b22 = divideMatrix(B, size, size, size);

            addMatrices(C, calc(a11, b11), calc(a12, b21), 0, 0);
            addMatrices(C, calc(a11, b12), calc(a12, b22), 0, size);
            addMatrices(C, calc(a21, b11), calc(a22, b21), size, 0);
            addMatrices(C, calc(a21, b12), calc(a22, b22), size, size);
        }
        return C;
    }

    // we can get rid of this method and do operations on indexes but lets keep it for simplicity
    private static int[][] divideMatrix(int[][] src, int n, int y, int x) {
        int[][] temp = new int[n][n];
        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++)
                temp[i][j] = src[y + i][x + j];
        return temp;
    }

    private static void addMatrices(int[][] dest, int[][] A, int[][] B, int y, int x) {
        int n = A.length;
        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++)
                dest[y + i][x + j] = A[i][j] + B[i][j];
    }

    private static void printMatrix(int[][] matrix) {
        int len = matrix.length;
        for(int i = 0; i < len; i++) {
            for(int j = 0; j < len; j++)
                System.out.print(matrix[i][j] + " ");
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] A = {{1, 2}, {4, 3}};
        int[][] B = {{4, 1}, {2, 4}};
        printMatrix(calc(A, B));
    }
}
