package _21_DataStructuresForDisjointSets;

import java.util.HashMap;
import java.util.Map;

public class WeightedUnionFind<T>
{
    private static class Node<T>
    {
        private T value;
        private Node<T> parent;
        private int rank;

        public Node(T value) {
            this.value = value;
            parent = this;
            rank = 0;
        }
    }

    private Map<T, Node<T>> elements = new HashMap<>();

    private boolean exists(T value) {
        return elements.containsKey(value);
    }

    public void add(T value) {
        elements.put(value, new Node<>(value));
    }

    public boolean connected(T a, T b) {
        return findSet(a) != null && findSet(a) == findSet(b);
    }

    public void union(T a, T b) {
        if (exists(a) && exists(b))
            union(elements.get(a), elements.get(b));
    }

    private void union(Node<T> a, Node<T> b) {
        Node<T> pa = findSet(a);
        Node<T> pb = findSet(b);
        if (pa == pb)
            return;
        link(pa, pb);
    }

    public void link(T a, T b) {
        if (exists(a) && exists(b))
            link(elements.get(a), elements.get(b));
    }

    private void link(Node<T> x, Node<T> y) {
        if (x.rank > y.rank)
            y.parent = x;
        else {
            x.parent = y;
            if (x.rank == y.rank)
                y.rank++;
        }
    }

    public T findSet(T value) {
        return exists(value) ? findSet(elements.get(value)).value : null;
    }

    private Node<T> findSet(Node<T> n) {
        if (n != n.parent)
            n.parent = findSet(n.parent);
        return n.parent;
    }

    private static class Example
    {
        public static void main(String[] args) {
            WeightedUnionFind<Integer> sets = new WeightedUnionFind<>();
            for (Integer i : new Integer[]{1, 2, 3, 4, 5, 6, 7, 8})
                sets.add(i);

            sets.union(1, 2);
            sets.union(3, 4);
            sets.union(5, 8);
            sets.union(7, 8);
            System.out.println(sets.connected(7, 8));
            sets.union(2, 5);
            System.out.println(sets.connected(1, 5));
        }
    }
}
