package _21_DataStructuresForDisjointSets;

public abstract class WeightedUnionFind
{
    public static class Node
    {
        private int value;
        private Node parent;
        private int rank;

        public Node(int value) {
            this.value = value;
            parent = this;
            rank = 0;
        }
    }

    public static Node makeSet(int value) {
        return new Node(value);
    }

    public static void union(Node x, Node y) {
        link(findSet(x), findSet(y));
    }

    public static void link(Node x, Node y) {
        if (x.rank > y.rank)
            y.parent = x;
        else {
            x.parent = y;
            if (x.rank == y.rank)
                y.rank++;
        }
    }

    public static Node findSet(Node n) {
        if (n != n.parent)
            n = findSet(n.parent);
        return n.parent;
    }

    private static class Example
    {
        public static void main(String[] args) {
            Node[] n = new Node[11];
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
