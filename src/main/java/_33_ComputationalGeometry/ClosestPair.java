package _33_ComputationalGeometry;

import java.util.Arrays;

public final class ClosestPair
{
    public static Point[] find(Point[] points) {
        int n = points.length;
        if (n <= 1)
            return null;

        Point[] sortedByX = new Point[n];
        Point[] sortedByY = new Point[n];
        Point[] util = new Point[n];

        for (int i = 0; i < n; i++) {
            sortedByX[i] = points[i];
            sortedByY[i] = points[i];
        }

        Arrays.sort(sortedByX, (p1, p2) -> {
            if (p1.getX() == p2.getX())
                return Double.compare(p1.getY(), p2.getY());
            return Double.compare(p1.getX(), p2.getX());
        });

        Arrays.sort(sortedByY, (p1, p2) -> {
            if (p1.getY() == p2.getY())
                return Double.compare(p1.getX(), p2.getX());
            return Double.compare(p1.getY(), p2.getY());
        });

        return closest(sortedByX, sortedByY, util, 0, n - 1);
    }

    private static Point[] closest(Point[] sortedByX, Point[] sortedByY, Point[] util, int start, int end) {
        int n = end - start + 1;
        if (n <= 3) {
            return bruteForce(sortedByX);
        }

        int pivot = end - n / 2;

        Point[] left = closest(sortedByX, sortedByY, util, start, pivot);
        Point[] right = closest(sortedByX, sortedByY, util, pivot + 1, end);
        Point[] min;
        double minDist;
        if (left[0].distanceTo(left[1]) < right[0].distanceTo(right[1])) {
            min = left;
            minDist = left[0].distanceTo(left[1]);
        } else {
            min = right;
            minDist = right[0].distanceTo(right[1]);
        }

        Point mid = sortedByX[pivot];
        int m = 0;
        for (int i = start; i <= end; i++) {
            if (Math.abs(sortedByY[i].getX() - mid.getX()) < minDist)
                util[m++] = sortedByY[i];
        }

        double delta = Double.MAX_VALUE;
        for (int i = 0; i < m; i++) {
            for (int j = i + 1; j < m; j++) {
                if (util[j].getY() - util[i].getY() >= delta)
                    break;
                double dist = util[i].distanceTo(util[j]);
                if (dist < delta) {
                    delta = dist;
                    if (dist < minDist) {
                        minDist = dist;
                        min[0] = util[i];
                        min[1] = util[j];
                    }
                }
            }
        }

        return min;
    }

    private static Point[] bruteForce(Point[] points) {
        int n = points.length;
        double min = Double.MAX_VALUE;
        int a = 0;
        int b = 0;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double dist = points[i].distanceTo(points[j]);
                if (dist < min) {
                    min = dist;
                    a = i;
                    b = j;
                }
            }
        }

        return new Point[]{points[a], points[b]};
    }
}
