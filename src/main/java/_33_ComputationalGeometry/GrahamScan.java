package _33_ComputationalGeometry;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public final class GrahamScan
{
    private static class PolarOrder implements Comparator<Point>
    {
        private Point p0;

        public PolarOrder(Point p0) {
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

    public static List<Point> scan(List<Point> pointList) {
        List<Point> result = new LinkedList<>();
        Point[] points = pointList.toArray(new Point[0]);
        moveMinYPointToFront(points);

        Point min = points[0];
        Arrays.sort(points, 1, points.length, new PolarOrder(min));
        int m = points.length - 1;

        if (m >= 2) {
            result.add(0, points[0]);
            result.add(0, points[1]);
            result.add(0, points[2]);
            for (int i = 3; i <= m; i++) {
                while (result.get(0).orientation(result.get(1), points[i]) <= 0)
                    result.remove(0);
                result.add(0, points[i]);
            }
        }
        return result;
    }

    private static void moveMinYPointToFront(Point[] points) {
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

        Point temp = points[0];
        points[0] = points[minIdx];
        points[minIdx] = temp;
    }
}
