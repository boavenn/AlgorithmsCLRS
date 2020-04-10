package _14_AugmentingDataStructures;

public class IntervalTree<T extends Comparable<T>>
{
    public static class Interval implements Comparable<Interval>
    {
        private int low;
        private int high;

        public Interval(int low, int high) {
            this.low = low;
            this.high = high;
        }

        public boolean intersects(Interval i) {
            return i.low <= high && low <= i.high;
        }

        @Override
        public int compareTo(Interval o) {
            return Integer.compare(low, o.low);
        }
    }

    private class Node
    {
        public static final int BLACK = 1;
        public static final int RED = 0;

        private Interval interval;
        private int max;
        private T value;

        private Node left = sentinel;
        private Node right = sentinel;
        private Node parent = sentinel;
        private int color = RED;

        public Node(Interval interval, T value) {
            this.interval = interval;
            this.max = interval.high;
            this.value = value;
        }
    }

    private Node sentinel;
    private Node root;

    public IntervalTree() {
        sentinel = new Node(new Interval(0, 0), null);
        sentinel.left = sentinel;
        sentinel.right = sentinel;
        sentinel.parent = sentinel;
        sentinel.color = Node.BLACK;
        root = sentinel;
    }

    public void insert(Interval interval, T value) {
        Node z = new Node(interval, value);
        Node y = sentinel;
        Node x = root;
        while (x != sentinel) {
            y = x;
            x.max = Math.max(x.max, z.max);
            if (z.interval.compareTo(y.interval) < 0)
                x = x.left;
            else
                x = x.right;
        }
        z.parent = y;
        if (y == sentinel)
            root = z;
        else if (z.interval.compareTo(y.interval) < 0)
            y.left = z;
        else
            y.right = z;
        insertFixup(z);
    }

    public void remove(Interval interval) {
        Node z = intervalSearch(interval);
        if (z == sentinel)
            return;
        Node y = z;
        Node temp = z.parent;
        int yOriginalColor = y.color;
        Node x;
        if (z.left == sentinel) {
            x = z.right;
            transplant(z, z.right);
        }
        else if (z.right == sentinel) {
            x = z.left;
            transplant(z, z.left);
        }
        else {
            y = minimum(z.right);
            yOriginalColor = y.color;
            x = y.right;
            if (y.parent == z)
                x.parent = y;
            else {
                temp = y.parent;
                transplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }
        while(temp != sentinel) {
            temp.max = Math.max(Math.max(temp.left.max, temp.right.max), temp.interval.high);
            temp = temp.parent;
        }
        if (yOriginalColor == Node.BLACK)
            removeFixup(x);
    }

    public boolean contains(Interval interval) {
        Node z = intervalSearch(interval);
        return z != sentinel;
    }

    public void printInOrder() {
        printInOrder(root);
    }

    private void printInOrder(Node node) {
        if (node == sentinel)
            return;
        printInOrder(node.left);
        System.out.print(node.value + " ");
        printInOrder(node.right);
    }

    private Node intervalSearch(Interval interval) {
        Node x = root;
        while (x != sentinel && !interval.intersects(x.interval)) {
            if (x.left != sentinel && x.interval.compareTo(interval) >= 0)
                x = x.left;
            else
                x = x.right;
        }
        return x;
    }

    private Node minimum(Node n) {
        if (n == sentinel)
            return null;
        Node temp = n;
        while (temp.left != sentinel) {
            temp = temp.left;
        }
        return temp;
    }

    private Node maximum(Node n) {
        if (n == sentinel)
            return null;
        Node temp = n;
        while (temp.right != sentinel) {
            temp = temp.right;
        }
        return temp;
    }

    private void transplant(Node u, Node v) {
        if (u.parent == sentinel)
            root = v;
        else if (u == u.parent.left)
            u.parent.left = v;
        else
            u.parent.right = v;
        v.parent = u.parent;
    }

    private void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != sentinel)
            y.left.parent = x;
        y.parent = x.parent;
        if (x.parent == sentinel)
            root = y;
        else if (x == x.parent.left)
            x.parent.left = y;
        else
            x.parent.right = y;
        y.left = x;
        x.parent = y;

        y.max = x.max;
        x.max = Math.max(Math.max(x.left.max, x.right.max), x.interval.high);
    }

    private void rightRotate(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != sentinel)
            y.right.parent = x;
        y.parent = x.parent;
        if (x.parent == sentinel)
            root = y;
        else if (x == x.parent.left)
            x.parent.left = y;
        else
            x.parent.right = y;
        y.right = x;
        x.parent = y;

        y.max = x.max;
        x.max = Math.max(Math.max(x.left.max, x.right.max), x.interval.high);
    }

    private void insertFixup(Node z) {
        while (z.parent.color == Node.RED) {
            if (z.parent == z.parent.parent.left) {
                Node y = z.parent.parent.right;
                if (y.color == Node.RED) {
                    z.parent.color = Node.BLACK;
                    y.color = Node.BLACK;
                    z.parent.parent.color = Node.RED;
                    z = z.parent.parent;
                }
                else {
                    if (z == z.parent.right) {
                        z = z.parent;
                        leftRotate(z);
                    }
                    z.parent.color = Node.BLACK;
                    z.parent.parent.color = Node.RED;
                    rightRotate(z.parent.parent);
                }
            }
            else {
                Node y = z.parent.parent.left;
                if (y.color == Node.RED) {
                    z.parent.color = Node.BLACK;
                    y.color = Node.BLACK;
                    z.parent.parent.color = Node.RED;
                    z = z.parent.parent;
                }
                else {
                    if (z == z.parent.left) {
                        z = z.parent;
                        rightRotate(z);
                    }
                    z.parent.color = Node.BLACK;
                    z.parent.parent.color = Node.RED;
                    leftRotate(z.parent.parent);
                }
            }
        }
        root.color = Node.BLACK;
    }

    private void removeFixup(Node x) {
        while (x != root && x.color == Node.BLACK) {
            if (x == x.parent.left) {
                Node w = x.parent.right;
                if (w.color == Node.RED) {
                    w.color = Node.BLACK;
                    x.parent.color = Node.RED;
                    leftRotate(x.parent);
                    w = x.parent.right;
                }
                if (w.left.color == Node.BLACK && w.right.color == Node.BLACK) {
                    w.color = Node.RED;
                    x = x.parent;
                }
                else {
                    if (w.right.color == Node.BLACK) {
                        w.left.color = Node.BLACK;
                        w.color = Node.RED;
                        rightRotate(w);
                        w = x.parent.right;
                    }
                    w.color = x.parent.color;
                    x.parent.color = Node.BLACK;
                    w.right.color = Node.BLACK;
                    leftRotate(x.parent);
                    x = root;
                }
            }
            else {
                Node w = x.parent.left;
                if (w.color == Node.RED) {
                    w.color = Node.BLACK;
                    x.parent.color = Node.RED;
                    rightRotate(x.parent);
                    w = x.parent.left;
                }
                if (w.right.color == Node.BLACK && w.left.color == Node.BLACK) {
                    w.color = Node.RED;
                    x = x.parent;
                }
                else {
                    if (w.left.color == Node.BLACK) {
                        w.right.color = Node.BLACK;
                        w.color = Node.RED;
                        leftRotate(w);
                        w = x.parent.left;
                    }
                    w.color = x.parent.color;
                    x.parent.color = Node.BLACK;
                    w.left.color = Node.BLACK;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }
        x.color = Node.BLACK;
    }

    private static class Example
    {
        public static void main(String[] args) {
            IntervalTree<Integer> it = new IntervalTree<>();
            it.insert(new Interval(16, 21), 0);
            it.insert(new Interval(25, 30), 0);
            it.insert(new Interval(8, 9), 0);
            it.insert(new Interval(5, 8), 0);
            it.insert(new Interval(15, 23), 0);
            it.insert(new Interval(17, 19), 0);
            it.insert(new Interval(26, 26), 0);
            it.insert(new Interval(0, 3), 0);
            it.insert(new Interval(19, 20), 0);
            it.insert(new Interval(6, 10), 0);
            it.insert(new Interval(25, 27), 0);
            it.remove(new Interval(25, 25));
            it.remove(new Interval(6, 7));
        }
    }
}
