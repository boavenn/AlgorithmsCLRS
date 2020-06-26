package _15_DynamicProgramming;

public final class OptimalBinarySearchTree
{
    private double[][] searchCosts;
    private int[][] roots;

    public void createOptimalBST(double[] successProbabilites, double[] failureProbabilites) {
        if (failureProbabilites.length != successProbabilites.length)
            throw new IllegalArgumentException();

        int n = successProbabilites.length - 1;
        double[][] probabilitiesSum = new double[n + 2][n + 1];
        searchCosts = new double[n + 2][n + 1];
        roots = new int[n + 1][n + 1];

        for (int i = 1; i <= n + 1; i++) {
            probabilitiesSum[i][i - 1] = failureProbabilites[i - 1];
            searchCosts[i][i - 1] = failureProbabilites[i - 1];
        }
        for (int k = 1; k <= n; k++) {
            for (int i = 1; i <= n - k + 1; i++) {
                int j = k + i - 1;
                searchCosts[i][j] = Double.MAX_VALUE;
                probabilitiesSum[i][j] = probabilitiesSum[i][j - 1] + successProbabilites[j] + failureProbabilites[j];
                for (int r = i; r <= j; r++) {
                    double t = searchCosts[i][r - 1] + searchCosts[r + 1][j] + probabilitiesSum[i][j];
                    if (t < searchCosts[i][j]) {
                        searchCosts[i][j] = t;
                        roots[i][j] = r;
                    }
                }
            }
        }
    }

    public static double minimumSearchCost(double[] successProbabilites, double[] failureProbabilites) {
        OptimalBinarySearchTree obst = new OptimalBinarySearchTree();
        obst.createOptimalBST(successProbabilites, failureProbabilites);
        return obst.searchCosts[1][successProbabilites.length - 1];
    }

    public static void constructOptimalBST(int[][] roots) {
        constructOptimalBST(roots, 1, roots.length - 1, 0);
    }

    private static void constructOptimalBST(int[][] roots, int begin, int end, int last) {
        if (begin > end) {
            if (begin - end == 1) {
                if (end < last)
                    System.out.println("d" + end + " is the left child of k" + last);
                else
                    System.out.println("d" + end + " is the right child of k" + last);
            }
            return;
        }

        if (last == 0)
            System.out.println("k" + roots[begin][end] + " is the root");
        else if (end < last)
            System.out.println("k" + roots[begin][end] + " is the left child of k" + last);
        else
            System.out.println("k" + roots[begin][end] + " is the right child of k" + last);

        constructOptimalBST(roots, begin, roots[begin][end] - 1, roots[begin][end]);
        constructOptimalBST(roots, roots[begin][end] + 1, end, roots[begin][end]);
    }
}
