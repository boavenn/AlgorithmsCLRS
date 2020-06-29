package _33_ComputationalGeometry;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnySegmentsIntersectTest
{
    @Test
    void returnCorrectOutput() {
        List<Segment> segments = new LinkedList<>();
        segments.add(new Segment(1, 8, 4, 8));
        segments.add(new Segment(2, 1, 12, 10));
        segments.add(new Segment(3, 4, 7, 6));
        segments.add(new Segment(5, 7, 11, 5));
        assertTrue(AnySegmentsIntersect.check(segments));
    }
}