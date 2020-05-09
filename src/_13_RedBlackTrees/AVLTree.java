package _13_RedBlackTrees;

import java.util.Comparator;

/*
 * Disclaimer: yeah i know it's not a red-black tree
 */
public class AVLTree<T>
{
    private static class Node<T>
    {
        private T key;
        private Node<T> parent;
        private Node<T> left;
        private Node<T> right;
        private int height;

        public Node(T key) {
            this.key = key;
            this.height = 1;
        }
    }

    private Node<T> root;
    private Comparator<T> comp;
    private int size;

    public AVLTree(Comparator<T> comp) {
        this.comp = comp;
    }

    private int heightOf(Node<T> n) {
        return n == null ? 0 : n.height;
    }

    private int balanceFactorOf(Node<T> n) {
        return n == null ? 0 : heightOf(n.right) - heightOf(n.left);
    }

    private void updateHeightOf(Node<T> n) {
        if (n != null)
            n.height = 1 + Math.max(heightOf(n.left), heightOf(n.right));
    }

    public void insert(T key) {
        if(contains(key))
            return;

        root = insert(root, key);
        size++;
    }

    private Node<T> insert(Node<T> n, T key) {
        if (n == null)
            return new Node<>(key);

        if (comp.compare(key, n.key) < 0) {
            n.left = insert(n.left, key);
            n.left.parent = n;
        } else {
            n.right = insert(n.right, key);
            n.right.parent = n;
        }

        updateHeightOf(n);
        return rebalance(n);
    }

    public void remove(T key) {
        if(!contains(key))
            return;

        root = remove(root, key);
        size--;
    }

    private Node<T> remove(Node<T> n, T key) {
        if (comp.compare(n.key, key) == 0) {
            if (n.left == null)
                return n.right;
            else if (n.right == null)
                return n.left;
            else {
                T min = minimum(n.right).key;
                n.key = min;
                n.right = remove(n.right, min);
            }
        } else if (comp.compare(key, n.key) < 0) {
            n.left = remove(n.left, key);
        } else {
            n.right = remove(n.right, key);
        }

        updateHeightOf(n);
        return rebalance(n);
    }

    public boolean contains(T key) {
        return search(key) != null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    public T minimum() {
        return isEmpty() ? null : minimum(root).key;
    }

    private Node<T> minimum(Node<T> n) {
        Node<T> t = n;
        while (t.left != null)
            t = t.left;
        return t;
    }

    public T maximum() {
        return isEmpty() ? null : maximum(root).key;
    }

    private Node<T> maximum(Node<T> n) {
        Node<T> t = n.right;
        while (t.right != null)
            t = t.right;
        return t;
    }

    public T successor(T key) {
        Node<T> n = search(key);
        if (n == null)
            return null;
        Node<T> succ = successor(n);
        return succ == null ? null : succ.key;
    }

    private Node<T> successor(Node<T> n) {
        if (n.right != null)
            return minimum(n.right);
        Node<T> p = n.parent;
        while (p != null && p.right == n) {
            n = p;
            p = n.parent;
        }
        return p;
    }

    public T predecessor(T key) {
        Node<T> n = search(key);
        if (n == null)
            return null;
        Node<T> pred = predecessor(n);
        return pred == null ? null : pred.key;
    }

    private Node<T> predecessor(Node<T> n) {
        if (n.left != null)
            return maximum(n.left);
        Node<T> p = n.parent;
        while (p != null && p.left == n) {
            n = p;
            p = n.parent;
        }
        return p;
    }

    private Node<T> search(T key) {
        Node<T> n = root;
        while (n != null && comp.compare(n.key, key) != 0) {
            if (comp.compare(key, n.key) < 0)
                n = n.left;
            else
                n = n.right;
        }
        return n;
    }

    private Node<T> rebalance(Node<T> n) {
        int balance = balanceFactorOf(n);
        if (balance == 2) {
            if (balanceFactorOf(n.right) < 0)
                n.right = rightRotate(n.right);
            n = leftRotate(n);
        } else if (balance == -2) {
            if (balanceFactorOf(n.left) > 0)
                n.left = leftRotate(n.left);
            n = rightRotate(n);
        }
        return n;
    }

    private Node<T> leftRotate(Node<T> a) {
        Node<T> b = a.right;
        a.right = b.left;
        if (b.left != null)
            b.left.parent = a;
        b.parent = a.parent;

        if (a.parent == null)
            root = b;
        else if (a == a.parent.left)
            a.parent.left = b;
        else
            a.parent.right = b;

        b.left = a;
        a.parent = b;
        updateHeightOf(a);
        updateHeightOf(b);
        return b;
    }

    private Node<T> rightRotate(Node<T> a) {
        Node<T> b = a.left;
        a.left = b.right;
        if (b.right != null)
            b.right.parent = a;
        b.parent = a.parent;

        if (a.parent == null)
            root = b;
        else if (a == a.parent.right)
            a.parent.right = b;
        else
            a.parent.left = b;

        b.right = a;
        a.parent = b;
        updateHeightOf(a);
        updateHeightOf(b);
        return b;
    }

    private static class Example
    {
        public static void main(String[] args) {
            AVLTree<Integer> avl = new AVLTree<>(Integer::compareTo);
            avl.insert(43);
            avl.insert(69);
            avl.insert(36);
            avl.insert(5);
            avl.insert(72);
            avl.insert(26);
            avl.insert(79);
            avl.insert(59);

            avl.remove(36);
            avl.remove(5);

            System.out.println(avl.maximum());
            System.out.println(avl.minimum());
            System.out.println(avl.size());
            System.out.println(avl.isEmpty());
            System.out.println(avl.contains(72));
            System.out.println(avl.predecessor(26));
            System.out.println(avl.successor(59));
        }
    }
}
