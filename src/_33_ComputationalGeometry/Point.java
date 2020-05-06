package _33_ComputationalGeometry;

public class Point
{
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distanceTo(Point p) {
        return Point.distance(this, p);
    }

    public static double distance(Point p1, Point p2) {
        double dx = p1.x - p2.x;
        double dy = p1.y - p2.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double orientation(Point p1, Point p2) {
        return Point.orientation(this, p1, p2);
    }

    public static double orientation(Point p0, Point p1, Point p2) {
        return (p2.getX() - p0.getX()) * (p1.getY() - p0.getY()) - (p1.getX() - p0.getX()) * (p2.getY() - p0.getY());
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "x = " + x + ", y = " + y;
    }
}
