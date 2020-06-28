package _21_DataStructuresForDisjointSets;

import java.util.*;

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

    public List<List<T>> disjointSets() {
        Map<Node<T>, List<T>> sets = new HashMap<>();
        for (var entry : elements.entrySet()) {
            Node<T> root = findSet(entry.getValue());
            if (!sets.containsKey(root)) {
                sets.put(root, new LinkedList<>());
            }
            sets.get(root).add(entry.getKey());
        }
        return new ArrayList<>(sets.values());
    }

    private boolean exists(T value) {
        return elements.containsKey(value);
    }

    public void makeSet(T value) {
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
}
