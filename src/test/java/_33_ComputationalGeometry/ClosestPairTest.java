package _33_ComputationalGeometry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClosestPairTest
{
    @Test
    void returnCorrectOutput() {
        int[] x = {3, 12, 40, 10, 12, 5, -5, -10, 10, -5, -2, -5, 10, 2, 20, 10, -15, -15};
        int[] y = {2, 30, 50, -5, 10, 10, 5, 15, 3, 25, 15, -10, -15, -8, 5, 20, 0, -10};
        Point[] points = new Point[x.length];
        for (int i = 0; i < x.length; i++)
            points[i] = new Point(x[i], y[i]);

        Point p1 = new Point(5, 10);
        Point p2 = new Point(12, 10);
        Point[] expected = {p1, p2};

        assertArrayEquals(expected, ClosestPair.find(points));
    }
}