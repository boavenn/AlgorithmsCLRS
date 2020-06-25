package _33_ComputationalGeometry;

import _12_BinarySearchTrees.BinarySearchTree;
import _13_RedBlackTrees.RedBlackTree;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import static _33_ComputationalGeometry.SegmentsIntersect.segmentsIntersect;

public final class AnySegmentsIntersect
{
    private static class PointWrapper
    {
        private Point point;
        private Segment segment;
        private boolean isLeft;
        private PointWrapper otherEnd;

        public PointWrapper(Point point, Segment segment, boolean isLeft) {
            this.point = point;
            this.segment = segment;
            this.isLeft = isLeft;
        }
    }

    private static Comparator<PointWrapper> comp = (p1, p2) -> {
        if (p1.point.getX() != p2.point.getX())
            return Double.compare(p1.point.getX(), p2.point.getX());
        else if (p1.isLeft != p2.isLeft)
            return Boolean.compare(p1.isLeft, p2.isLeft);
        else
            return Double.compare(p1.point.getY(), p2.point.getY());
    };

    public static boolean anySegmentsIntersect(List<Segment> segments) {
        RedBlackTree<PointWrapper> rbt = new RedBlackTree<>(Comparator.comparingDouble(p -> p.point.getY()));
        PriorityQueue<PointWrapper> points = new PriorityQueue<>(comp);

        for (Segment s : segments) {
            PointWrapper left = new PointWrapper(s.getLeft(), s, true);
            PointWrapper right = new PointWrapper(s.getRight(), s, false);
            left.otherEnd = right;
            right.otherEnd = left;
            points.add(left);
            points.add(right);
        }

        while (!points.isEmpty()) {
            PointWrapper p = points.poll();
            PointWrapper above = rbt.successor(p);
            PointWrapper below = rbt.predecessor(p);
            if (p.isLeft) {
                rbt.insert(p);
                if ((above != null && segmentsIntersect(above.segment, p.segment)) ||
                        (below != null && segmentsIntersect(below.segment, p.segment)))
                    return true;
            } else {
                if (above != null && below != null && segmentsIntersect(above.segment, below.segment))
                    return true;
                rbt.remove(p);
                rbt.remove(p.otherEnd);
            }
        }
        return false;
    }

    private static class Example
    {
        public static void main(String[] args) {
            List<Segment> s = new LinkedList<>();
            s.add(new Segment(1, 8, 4, 8));
            s.add(new Segment(2, 1, 12, 10));
            s.add(new Segment(3, 4, 7, 6));
            s.add(new Segment(4, 8, 11, 5));
            s.add(new Segment(5, 9, 10, 7));
            s.add(new Segment(6, 3, 9, 2));
            System.out.println(anySegmentsIntersect(s));
        }
    }
}
