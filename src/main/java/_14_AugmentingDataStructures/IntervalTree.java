package _14_AugmentingDataStructures;

import java.util.Comparator;

public class IntervalTree<T>
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

    private Node root;
    private Node sentinel;
    private int size;

    public IntervalTree() {
        sentinel = new Node(new Interval(0, 0), null);
        sentinel.left = sentinel;
        sentinel.right = sentinel;
        sentinel.parent = sentinel;
        sentinel.color = Node.BLACK;
        root = sentinel;
    }

    public void insert(Interval interval, T value) {
        Node newNode = new Node(interval, value);
        Node p = sentinel;
        Node n = root;
        while (n != sentinel) {
            p = n;
            n.max = Math.max(n.max, newNode.max);
            if (newNode.interval.compareTo(p.interval) < 0)
                n = n.left;
            else
                n = n.right;
        }
        newNode.parent = p;
        if (p == sentinel)
            root = newNode;
        else if (newNode.interval.compareTo(p.interval) < 0)
            p.left = newNode;
        else
            p.right = newNode;

        insertFixup(newNode);
        size++;
    }

    public void remove(Interval interval) {
        Node nodeToDelete = intervalSearch(interval);
        if (nodeToDelete == sentinel)
            return;

        Node p = nodeToDelete.parent;
        int oldColor = nodeToDelete.color;
        Node n;
        if (nodeToDelete.left == sentinel) {
            n = nodeToDelete.right;
            transplant(nodeToDelete, nodeToDelete.right);
        } else if (nodeToDelete.right == sentinel) {
            n = nodeToDelete.left;
            transplant(nodeToDelete, nodeToDelete.left);
        } else {
            Node min = minimum(nodeToDelete.right);
            oldColor = min.color;
            n = min.right;
            if (min.parent == nodeToDelete)
                n.parent = min;
            else {
                p = min.parent;
                transplant(min, min.right);
                min.right = nodeToDelete.right;
                min.right.parent = min;
            }
            transplant(nodeToDelete, min);
            min.left = nodeToDelete.left;
            min.left.parent = min;
            min.color = nodeToDelete.color;
        }
        while (p != sentinel) {
            p.max = Math.max(Math.max(p.left.max, p.right.max), p.interval.high);
            p = p.parent;
        }
        if (oldColor == Node.BLACK)
            removeFixup(n);
        size--;
    }

    public boolean contains(Interval interval) {
        Node z = intervalSearch(interval);
        return z != sentinel;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
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
        Node t = n;
        while (t.left != sentinel) {
            t = t.left;
        }
        return t;
    }

    private Node maximum(Node n) {
        Node t = n;
        while (t.right != sentinel) {
            t = t.right;
        }
        return t;
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

    private void leftRotate(Node a) {
        Node b = a.right;
        a.right = b.left;
        if (b.left != sentinel)
            b.left.parent = a;
        b.parent = a.parent;

        if (a.parent == sentinel)
            root = b;
        else if (a == a.parent.left)
            a.parent.left = b;
        else
            a.parent.right = b;

        b.left = a;
        a.parent = b;
        a.max = Math.max(Math.max(a.left.max, a.right.max), a.interval.high);
        b.max = a.max;
    }

    private void rightRotate(Node a) {
        Node b = a.left;
        a.left = b.right;
        if (b.right != sentinel)
            b.right.parent = a;
        b.parent = a.parent;

        if (a.parent == sentinel)
            root = b;
        else if (a == a.parent.left)
            a.parent.left = b;
        else
            a.parent.right = b;

        b.right = a;
        a.parent = b;
        a.max = Math.max(Math.max(a.left.max, a.right.max), a.interval.high);
        b.max = a.max;
    }

    private void insertFixup(Node n) {
        while (n.parent.color == Node.RED) {
            if (n.parent == n.parent.parent.left) {
                Node u = n.parent.parent.right;
                if (u.color == Node.RED) {
                    n.parent.color = Node.BLACK;
                    u.color = Node.BLACK;
                    n.parent.parent.color = Node.RED;
                    n = n.parent.parent;
                } else {
                    if (n == n.parent.right) {
                        n = n.parent;
                        leftRotate(n);
                    }
                    n.parent.color = Node.BLACK;
                    n.parent.parent.color = Node.RED;
                    rightRotate(n.parent.parent);
                }
            } else {
                Node u = n.parent.parent.left;
                if (u.color == Node.RED) {
                    n.parent.color = Node.BLACK;
                    u.color = Node.BLACK;
                    n.parent.parent.color = Node.RED;
                    n = n.parent.parent;
                } else {
                    if (n == n.parent.left) {
                        n = n.parent;
                        rightRotate(n);
                    }
                    n.parent.color = Node.BLACK;
                    n.parent.parent.color = Node.RED;
                    leftRotate(n.parent.parent);
                }
            }
        }
        root.color = Node.BLACK;
    }

    private void removeFixup(Node n) {
        while (n != root && n.color == Node.BLACK) {
            if (n == n.parent.left) {
                Node s = n.parent.right;
                if (s.color == Node.RED) {
                    s.color = Node.BLACK;
                    n.parent.color = Node.RED;
                    leftRotate(n.parent);
                    s = n.parent.right;
                }
                if (s.left.color == Node.BLACK && s.right.color == Node.BLACK) {
                    s.color = Node.RED;
                    n = n.parent;
                } else {
                    if (s.right.color == Node.BLACK) {
                        s.left.color = Node.BLACK;
                        s.color = Node.RED;
                        rightRotate(s);
                        s = n.parent.right;
                    }
                    s.color = n.parent.color;
                    n.parent.color = Node.BLACK;
                    s.right.color = Node.BLACK;
                    leftRotate(n.parent);
                    n = root;
                }
            } else {
                Node s = n.parent.left;
                if (s.color == Node.RED) {
                    s.color = Node.BLACK;
                    n.parent.color = Node.RED;
                    rightRotate(n.parent);
                    s = n.parent.left;
                }
                if (s.right.color == Node.BLACK && s.left.color == Node.BLACK) {
                    s.color = Node.RED;
                    n = n.parent;
                } else {
                    if (s.left.color == Node.BLACK) {
                        s.right.color = Node.BLACK;
                        s.color = Node.RED;
                        leftRotate(s);
                        s = n.parent.left;
                    }
                    s.color = n.parent.color;
                    n.parent.color = Node.BLACK;
                    s.left.color = Node.BLACK;
                    rightRotate(n.parent);
                    n = root;
                }
            }
        }
        n.color = Node.BLACK;
    }

    private static class Example
    {
        public static void main(String[] args) {
            IntervalTree<Integer> it = new IntervalTree<>();
            it.insert(new Interval(16, 21), 1);
            it.insert(new Interval(25, 30), 2);
            it.insert(new Interval(8, 9), 3);
            it.insert(new Interval(5, 8), 4);
            it.insert(new Interval(15, 23), 5);
            it.insert(new Interval(17, 19), 6);
            it.insert(new Interval(26, 26), 7);
            it.insert(new Interval(0, 3), 8);
            it.insert(new Interval(19, 20), 9);
            it.insert(new Interval(6, 10), 10);
            it.insert(new Interval(25, 27), 11);
            System.out.println(it.size());
            it.printInOrder();

            it.remove(new Interval(25, 25));
            it.remove(new Interval(6, 7));
        }
    }
}
