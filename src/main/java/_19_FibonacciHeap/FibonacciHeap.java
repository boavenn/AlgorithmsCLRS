package _19_FibonacciHeap;

import java.util.Comparator;

public class FibonacciHeap<T>
{
    public static class FibNode<T>
    {
        private FibNode<T> parent;
        private FibNode<T> left;
        private FibNode<T> right;
        private FibNode<T> child;
        private T key;
        private int degree;
        private boolean isMarked;

        public FibNode(T key) {
            this.key = key;
            this.left = this;
            this.right = this;
            this.isMarked = false;
            this.degree = 0;
        }

        public boolean hasNeighbour() {
            return right != this;
        }

        public boolean isChild() {
            return parent != null;
        }

        public void insert(FibNode<T> n) {
            left.right = n;
            n.left = left;
            n.right = this;
            left = n;
        }
    }

    private FibNode<T> min;
    private Comparator<T> comp;
    private int size = 0;

    public FibonacciHeap(Comparator<T> comp) {
        this.comp = comp;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public FibNode<T> insert(T key) {
        FibNode<T> n = new FibNode<>(key);
        insert(n);
        return n;
    }

    private void insert(FibNode<T> n) {
        insertToRootList(n);
        size++;
    }

    private void insertToRootList(FibNode<T> n) {
        if (min == null) {
            min = n;
        } else {
            n.left = min.left;
            min.left.right = n;
            n.right = min;
            min.left = n;
            if (comp.compare(n.key, min.key) < 0)
                min = n;
        }
    }

    private void removeChild(FibNode<T> c) {
        if (!c.isChild())
            throw new IllegalArgumentException();

        if (c.hasNeighbour()) {
            c.left.right = c.right;
            c.right.left = c.left;
            c.parent.child = c.right;
        } else {
            c.parent.child = null;
        }
        c.parent = null;
    }

    private void link(FibNode<T> a, FibNode<T> b) {
        // update min if necessary
        if (min == a)
            min = b;
        // remove a from root list
        a.left.right = a.right;
        a.right.left = a.left;
        // set a as child of b
        if (b.child != null) {
            a.left = b.child.left;
            b.child.left.right = a;
            a.right = b.child;
            b.child.left = a;
        } else {
            a.left = a;
            a.right = a;
            b.child = a;
        }
        a.parent = b;
        b.degree++;
        a.isMarked = false;
    }

    public T minimum() {
        return min == null ? null : min.key;
    }

    public FibonacciHeap<T> union(FibonacciHeap<T> H) {
        FibonacciHeap<T> newHeap = new FibonacciHeap<>(comp);

        H.min.left.right = min.right;
        min.right.left = H.min.left;
        min.right = H.min;
        H.min.left = min;

        newHeap.insertToRootList(H.min);
        newHeap.min = comp.compare(H.min.key, min.key) < 0 ? H.min : min;
        newHeap.size = size + H.size;

        return newHeap;
    }

    public T extractMin() {
        FibNode<T> m = min;
        T v = null;
        if (m != null) {
            v = m.key;
            // add m children to root list
            while (m.child != null) {
                FibNode<T> c = m.child;
                removeChild(c);
                insertToRootList(c);
            }
            // remove m from root list
            m.right.left = m.left;
            m.left.right = m.right;

            // look for a new min
            if (m == m.right) {
                min = null;
            } else {
                min = m.right;
                consolidate();
            }
            size--;
        }
        return v;
    }

    @SuppressWarnings("unchecked")
    private void consolidate() {
        FibNode<T>[] A = new FibNode[size];

        FibNode<T> m = min;
        FibNode<T> last = min.left;
        boolean done = false;

        do {
            FibNode<T> a = m;
            m = m.right;
            if (m.left == last)
                done = true;

            int d = a.degree;
            while (A[d] != null) {
                FibNode<T> b = A[d];
                if (comp.compare(a.key, b.key) > 0) {
                    link(a, b);
                    a = b;
                } else {
                    link(b, a);
                }
                A[d] = null;
                d++;
            }
            A[d] = a;
        } while (!done);

        for (int i = 0; i < size; i++) {
            if (A[i] != null && comp.compare(A[i].key, min.key) < 0)
                min = A[i];
        }
    }

    private void cut(FibNode<T> c, FibNode<T> p) {
        c.right.left = c.left;
        c.left.right = c.right;
        if (p.child == c) {
            p.child = c.right == c ? null : c.right;
        }
        p.degree--;
        insertToRootList(c);
        c.parent = null;
        c.isMarked = false;
    }

    private void cascadingCut(FibNode<T> n) {
        FibNode<T> p = n.parent;
        if (p != null) {
            if (!n.isMarked)
                n.isMarked = true;
            else {
                cut(n, p);
                cascadingCut(p);
            }
        }
    }

    public void decreaseKey(FibNode<T> n, T newKey) {
        if (comp.compare(newKey, n.key) > 0)
            throw new IllegalArgumentException();

        n.key = newKey;
        FibNode<T> p = n.parent;
        if (p != null && comp.compare(n.key, p.key) < 0) {
            cut(n, p);
            cascadingCut(p);
        }
        if (comp.compare(n.key, min.key) < 0)
            min = n;
    }
}
