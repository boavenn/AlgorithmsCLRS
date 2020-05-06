package _33_ComputationalGeometry;

public final class SegmentsIntersect
{
    private static boolean onSegment(Point p0, Point p1, Point p2) {
        return Math.min(p0.getX(), p1.getX()) <= p2.getX() && p2.getX() <= Math.max(p0.getX(), p1.getX()) &&
                Math.min(p0.getY(), p1.getY()) <= p2.getY() && p2.getY() <= Math.max(p0.getY(), p1.getY());
    }

    public static boolean segmentsIntersect(Point p1, Point p2, Point p3, Point p4) {
        double d1 = p3.orientation(p4, p1);
        double d2 = p3.orientation(p4, p2);
        double d3 = p1.orientation(p2, p3);
        double d4 = p1.orientation(p2, p4);
        if (((d1 > 0 && d2 < 0) || (d1 < 0 && d2 > 0)) && ((d3 > 0 && d4 < 0) || (d3 < 0 && d4 > 0)))
            return true;
        else if (d1 == 0 && onSegment(p3, p4, p1))
            return true;
        else if (d2 == 0 && onSegment(p3, p4, p2))
            return true;
        else if (d3 == 0 && onSegment(p1, p2, p3))
            return true;
        else
            return d4 == 0 && onSegment(p1, p2, p4);
    }

    public static boolean segmentsIntersect(Segment a, Segment b) {
        return segmentsIntersect(a.getStart(), a.getEnd(), b.getStart(), b.getEnd());
    }

    private static class Example
    {
        public static void main(String[] args) {
            Point p1 = new Point(0, 0);
            Point p2 = new Point(3, 5);
            Point p3 = new Point(1, 3);
            Point p4 = new Point(4, 0);

            System.out.println(segmentsIntersect(p1, p2, p3, p4));
        }
    }
}
