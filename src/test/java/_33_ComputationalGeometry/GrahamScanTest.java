package _33_ComputationalGeometry;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GrahamScanTest
{
    @Test
    void returnCorrectOutput() {
        int[] x = {-7, -4, 2, 6, 8, 7, 4, 8, 0, 3, 6, 0, -9, -8, -8, -10, -2, -10};
        int[] y = {8, 6, 6, 4, 6, -2, -6, -7, 0, -2, -10, -6, -5, -2, 0, 3, 2, 4};
        List<Point> points = new LinkedList<>();
        for (int i = 0; i < x.length; i++)
            points.add(new Point(x[i], y[i]));

        List<Point> expected = List.of(new Point(-9, -5), new Point(-10, 3), new Point(-10, 4),
                new Point(-7, 8), new Point(8, 6), new Point(8, -7), new Point(6, -10));

        assertIterableEquals(expected, GrahamScan.scan(points));
    }
}