package _19_FibonacciHeap;

public class FibonacciHeap<T extends Comparable<T>>
{
    private static class Node<T extends Comparable<T>>
    {
        private Node<T> parent;
        private Node<T> left;
        private Node<T> right;
        private Node<T> child;
        private T key;
        private int degree;
        private boolean isMarked;

        public Node(T key) {
            this.key = key;
            this.left = this;
            this.right = this;
            this.isMarked = false;
            this.degree = 0;
        }
    }

    private int n = 0;
    private Node<T> min;

    private void insertToRootList(Node<T> x) {
        if (min == null) {
            min = x;
            min.left = min;
            min.right = min;
        }
        else {
            x.left = min.left;
            min.left.right = x;
            x.right = min;
            min.left = x;
            if (x.key.compareTo(min.key) < 0)
                min = x;
        }
    }

    private void link(Node<T> x, Node<T> y) {
        // update min if necessary
        if (min == x)
            min = y;
        // remove x from root list
        x.left.right = x.right;
        x.right.left = x.left;
        // set x as child of y
        if (y.child != null) {
            x.left = y.child.left;
            y.child.left.right = x;
            x.right = y.child;
            y.child.left = x;
        }
        else {
            x.left = x;
            x.right = x;
            y.child = x;
        }
        x.parent = y;
        y.degree++;
        x.isMarked = false;
    }

    @SuppressWarnings("unchecked")
    private void consolidate() {
        Object[] A = new Object[n];

        Node<T> w = min;
        do {
            Node<T> x = w;
            w = w.right;
            int d = x.degree;
            while (A[d] != null) {
                Node<T> y = (Node<T>) A[d];
                if (x.key.compareTo(y.key) > 0) {
                    swap(x, y);
                    link(x, y);
                }
                else {
                    link(y, x);
                }
                A[d] = null;
                d++;
            }
            A[d] = x.parent == null ? x : x.parent;
        } while (w != min);

        min = null;

        for (int i = 0; i < n; i++) {
            if (A[i] != null) {
                insertToRootList((Node<T>) A[i]);
            }
        }
    }

    private void swap(Node<T> x, Node<T> y) {
        if (y.right == x) {
            y.right = x.right;
            x.left = y.left;
            y.right.left = y;
            x.left.right = x;
            x.right = y;
            y.left = x;
        }
        else if (x.right == y) {
            x.right = y.right;
            y.left = x.left;
            x.right.left = x;
            y.left.right = y;
            y.right = x;
            x.left = y;
        }
        else {
            Node<T> temp = new Node<>(null);
            temp.left = x.left;
            temp.right = x.right;
            // replace y with x
            x.left = y.left;
            y.left.right = x;
            x.right = y.right;
            y.right.left = x;
            // set y on previous place of x
            temp.left.right = y;
            y.left = temp.left;
            temp.right.left = y;
            y.right = temp.right;
        }
    }

    public void insert(T key) {
        Node<T> x = new Node<>(key);
        insert(x);
    }

    public void insert(Node<T> x) {
        insertToRootList(x);
        n++;
    }

    public T minimum() {
        return min == null ? null : min.key;
    }

    public FibonacciHeap<T> union(FibonacciHeap<T> H) {
        FibonacciHeap<T> newHeap = new FibonacciHeap<>();

        // merge both root lists
        H.min.left.right = min.right;
        min.right.left = H.min.left;
        min.right = H.min;
        H.min.left = min;

        // look for new min
        if (H.min.key.compareTo(min.key) < 0)
            newHeap.min = H.min;
        else
            newHeap.min = min;

        newHeap.n = n + H.n;
        return newHeap;
    }

    public T extractMin() {
        Node<T> z = min;
        T v = null;
        if (z != null) {
            v = z.key;
            // add z children to root list
            while (z.child != null) {
                Node<T> x = z.child;
                Node<T> y = x.right;
                insertToRootList(x);
                x.parent = null;
                if (y != x)
                    z.child = y;
                else
                    z.child = null;
            }
            // remove z from root list
            z.right.left = z.left;
            z.left.right = z.right;

            // look for a new min
            if (z == z.right) {
                min = null;
            }
            else {
                min = z.right;
                consolidate();
            }
            n--;
        }
        return v;
    }

    private void cut(Node<T> x, Node<T> y) {
        x.right.left = x.left;
        x.left.right = x.right;
        if (y.child == x) {
            y.child = x.right == x ? null : x.right;
        }
        y.degree--;
        insertToRootList(x);
        x.parent = null;
        x.isMarked = false;
    }

    private void cascadingCut(Node<T> y) {
        Node<T> z = y.parent;
        if (z != null) {
            if (!y.isMarked)
                y.isMarked = true;
            else {
                cut(y, z);
                cascadingCut(z);
            }
        }
    }

    public void decreaseKey(Node<T> x, T key) {
        if (key.compareTo(x.key) > 0)
            throw new IllegalArgumentException();
        x.key = key;
        Node<T> y = x.parent;
        if (y != null && x.key.compareTo(y.key) < 0) {
            cut(x, y);
            cascadingCut(y);
        }
        if (x.key.compareTo(min.key) < 0)
            min = x;
    }

    public static class Example
    {
        public static void main(String[] args) {
            FibonacciHeap<Integer> heap = new FibonacciHeap<>();
            Node<Integer> n = new Node<>(17);
            heap.insert(n);
            heap.insert(3);
            heap.insert(24);
            heap.insert(21);
            heap.insert(7);
            heap.insert(23);
            heap.extractMin();
            heap.decreaseKey(n, 6);

            FibonacciHeap<Integer> h1 = new FibonacciHeap<>();
            h1.insert(1);
            h1.insert(2);
            h1.insert(3);
            h1.insert(4);

            FibonacciHeap<Integer> h2 = new FibonacciHeap<>();
            h2.insert(10);
            h2.insert(11);
            h2.insert(12);
            h2.insert(13);

            FibonacciHeap<Integer> u = h1.union(h2);
        }
    }
}
