package _12_BinarySearchTrees;

public class BinarySearchTree<T extends Comparable<T>>
{
    private class Node
    {
        T value;
        Node left;
        Node right;

        public Node(T value) {
            this.value = value;
        }
    }

    Node root;

    public void insert(T value) {
        Node node = new Node(value);
        Node parent = null;
        Node temp = root;
        while (temp != null) {
            parent = temp;
            if (value.compareTo(temp.value) < 0)
                temp = temp.left;
            else
                temp = temp.right;
        }

        if (parent == null)
            root = node;
        else if (value.compareTo(parent.value) < 0)
            parent.left = node;
        else
            parent.right = node;
    }

    public void remove(T value) {
        root = removeRec(root, value);
    }

    public boolean contains(T value) {
        return search(value) != null;
    }

    public T minimum() {
        Node n = minimum(root);
        return n == null ? null : n.value;
    }

    public T maximum() {
        Node n = maximum(root);
        return n == null ? null : n.value;
    }

    private Node minimum(Node n) {
        if (n == null)
            return null;
        Node temp = n;
        while (temp.left != null) {
            temp = temp.left;
        }
        return temp;
    }

    private Node maximum(Node n) {
        if (n == null)
            return null;
        Node temp = n;
        while (temp.right != null) {
            temp = temp.right;
        }
        return temp;
    }

    private Node search(T value) {
//        return searchRecursive(root, value);
        return searchIterative(root, value);
    }

    private Node searchRecursive(Node n, T value) {
        if (n == null || n.value == value)
            return n;
        if (value.compareTo(n.value) < 0)
            return searchRecursive(n.left, value);
        else
            return searchRecursive(n.right, value);
    }

    private Node searchIterative(Node n, T value) {
        while (n != null && n.value != value) {
            if (value.compareTo(n.value) < 0)
                n = n.left;
            else
                n = n.right;
        }
        return n;
    }

    private Node removeRec(Node n, T value) {
        if (n == null)
            return null;

        if (value == n.value) {
            if (n.left == null && n.right == null)
                return null;
            if (n.left == null)
                return n.right;
            if (n.right == null)
                return n.left;
            T min = minimum(n.right).value;
            n.value = min;
            n.right = removeRec(n.right, min);
            return n;
        }
        else if (value.compareTo(n.value) < 0) {
            n.left = removeRec(n.left, value);
            return n;
        }
        else {
            n.right = removeRec(n.right, value);
            return n;
        }
    }

    private static class Example
    {
        public static void main(String[] args) {
            BinarySearchTree<Integer> bst = new BinarySearchTree<>();
            bst.insert(5);
            bst.insert(2);
            bst.insert(3);
            bst.insert(7);
            System.out.println(bst.maximum());
            System.out.println(bst.minimum());
            bst.remove(7);
            System.out.println(bst.contains(3));
            System.out.println(bst.maximum());
        }
    }
}
