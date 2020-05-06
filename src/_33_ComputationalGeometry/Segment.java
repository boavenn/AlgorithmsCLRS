package _33_ComputationalGeometry;

public class Segment
{
    private Point start;
    private Point end;

    public Segment(double x1, double y1, double x2, double y2) {
        this(new Point(x1, y1), new Point(x2, y2));
    }

    public Segment(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public Point getLeft() {
        return start.getX() < end.getX() ? start : end;
    }

    public Point getRight() {
        return start.getX() < end.getX() ? end : start;
    }
}
