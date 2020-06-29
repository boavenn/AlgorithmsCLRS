package _33_ComputationalGeometry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SegmentsIntersectTest
{
    @Test
    void returnCorrectOutput() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(3, 5);
        Point p3 = new Point(1, 3);
        Point p4 = new Point(4, 0);

        assertTrue(SegmentsIntersect.check(p1, p2, p3, p4));
    }
}