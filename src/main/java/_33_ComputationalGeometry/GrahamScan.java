package _33_ComputationalGeometry;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public final class GrahamScan
{
    private static class Comp implements Comparator<Point>
    {
        private Point p0;

        public Comp(Point p0) {
            this.p0 = p0;
        }

        @Override
        public int compare(Point p1, Point p2) {
            double dx1 = p1.getX() - p0.getX();
            double dy1 = p1.getY() - p0.getY();
            double dx2 = p2.getX() - p0.getX();
            double dy2 = p2.getY() - p0.getY();

            if (dy1 >= 0 && dy2 < 0)
                return -1;
            else if (dy2 >= 0 && dy1 < 0)
                return 1;
            else if (dy1 == 0 && dy2 == 0) {
                if (dx1 >= 0 && dx2 < 0)
                    return -1;
                else if (dx2 >= 0 && dx1 < 0)
                    return 1;
                else
                    return 0;
            } else
                // counter-clockwise order
                return (int) p0.orientation(p1, p2);
        }
    }

    public static List<Point> grahamScan(List<Point> l) {
        List<Point> res = new LinkedList<>();
        Point[] points = new Point[l.size()];
        l.toArray(points);

        swap(points, 0, minYPointIndex(points));
        Point min = points[0];
        Arrays.sort(points, 1, points.length, new Comp(min));
        int m = points.length - 1;

        if (m >= 2) {
            res.add(0, points[0]);
            res.add(0, points[1]);
            res.add(0, points[2]);
            for (int i = 3; i <= m; i++) {
                while (res.get(0).orientation(res.get(1), points[i]) <= 0)
                    res.remove(0);
                res.add(0, points[i]);
            }
        }
        return res;
    }

    private static int minYPointIndex(Point[] points) {
        Point minY = points[0];
        int minIdx = 0;
        int n = points.length;
        for (int i = 0; i < n; i++) {
            Point p = points[i];
            if (p.getY() < minY.getY() || (p.getY() == minY.getY() && p.getX() < minY.getX())) {
                minY = p;
                minIdx = i;
            }
        }
        return minIdx;
    }

    private static <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static class Example
    {
        public static void main(String[] args) {
            int[] x = {-7, -4, 2, 6, 8, 7, 4, 8, 0, 3, 6, 0, -9, -8, -8, -10, -2, -10};
            int[] y = {8, 6, 6, 4, 6, -2, -6, -7, 0, -2, -10, -6, -5, -2, 0, 3, 2, 4};
            List<Point> l = new LinkedList<>();
            for (int i = 0; i < x.length; i++)
                l.add(new Point(x[i], y[i]));
            System.out.println(grahamScan(l));
        }
    }
}
