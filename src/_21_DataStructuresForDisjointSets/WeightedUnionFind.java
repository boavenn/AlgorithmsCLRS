package _21_DataStructuresForDisjointSets;

public abstract class WeightedUnionFind<T>
{
    public static class Node<T>
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

    public static <T> Node<T> makeSet(T value) {
        return new Node<T>(value);
    }

    public static <T> void union(Node<T> x, Node<T> y) {
        link(findSet(x), findSet(y));
    }

    public static <T> void link(Node<T> x, Node<T> y) {
        if (x.rank > y.rank)
            y.parent = x;
        else {
            x.parent = y;
            if (x.rank == y.rank)
                y.rank++;
        }
    }

    public static <T> Node<T> findSet(Node<T> n) {
        if (n != n.parent)
            n = findSet(n.parent);
        return n.parent;
    }

    @SuppressWarnings("unchecked")
    private static class Example
    {
        public static void main(String[] args) {
            Node<Integer>[] n = new Node[11];
            for (int i = 0; i < n.length; i++)
                n[i] = makeSet(i);

            union(n[1], n[3]);
            union(n[2], n[3]);

            union(n[5], n[6]);
            union(n[9], n[7]);
            union(n[7], n[6]);
            union(n[4], n[7]);
            union(n[10], n[5]);

            union(n[1], n[5]);
        }
    }
}
