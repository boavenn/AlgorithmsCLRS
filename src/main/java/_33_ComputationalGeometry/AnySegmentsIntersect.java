package _33_ComputationalGeometry;

import _13_RedBlackTrees.RedBlackTree;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public final class AnySegmentsIntersect
{
    private static class PointWrapper
    {
        private Point point;
        private Segment segment;
        private boolean isLeftEnd;
        private PointWrapper otherEnd;

        public PointWrapper(Point point, Segment segment, boolean isLeftEnd) {
            this.point = point;
            this.segment = segment;
            this.isLeftEnd = isLeftEnd;
        }
    }

    private static Comparator<PointWrapper> comp = (p1, p2) -> {
        if (p1.point.getX() != p2.point.getX())
            return Double.compare(p1.point.getX(), p2.point.getX());
        else if (p1.isLeftEnd != p2.isLeftEnd)
            return Boolean.compare(p1.isLeftEnd, p2.isLeftEnd);
        else
            return Double.compare(p1.point.getY(), p2.point.getY());
    };

    public static boolean check(List<Segment> segments) {
        RedBlackTree<PointWrapper> pointsByY = new RedBlackTree<>(Comparator.comparingDouble(p -> p.point.getY()));
        PriorityQueue<PointWrapper> queue = new PriorityQueue<>(comp);

        for (Segment s : segments) {
            PointWrapper left = new PointWrapper(s.getLeft(), s, true);
            PointWrapper right = new PointWrapper(s.getRight(), s, false);
            left.otherEnd = right;
            right.otherEnd = left;
            queue.add(left);
            queue.add(right);
        }

        while (!queue.isEmpty()) {
            PointWrapper p = queue.poll();
            PointWrapper above = pointsByY.successor(p);
            PointWrapper below = pointsByY.predecessor(p);
            if (p.isLeftEnd) {
                pointsByY.insert(p);
                if ((above != null && SegmentsIntersect.check(above.segment, p.segment)) ||
                        (below != null && SegmentsIntersect.check(below.segment, p.segment)))
                    return true;
            } else {
                if (above != null && below != null && SegmentsIntersect.check(above.segment, below.segment))
                    return true;
                pointsByY.remove(p);
                pointsByY.remove(p.otherEnd);
            }
        }
        return false;
    }
}
