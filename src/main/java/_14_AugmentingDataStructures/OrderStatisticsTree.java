package _14_AugmentingDataStructures;

import java.util.Comparator;
import java.util.Random;

public class OrderStatisticsTree<T>
{
    private class Node
    {
        public static final int BLACK = 1;
        public static final int RED = 0;
        private T key;
        private int size = 1;
        private Node left = sentinel;
        private Node right = sentinel;
        private Node parent = sentinel;
        private int color = RED;

        public Node(T key) {
            this.key = key;
        }
    }

    private Node root;
    private Node sentinel;
    private Comparator<T> comp;

    public OrderStatisticsTree(Comparator<T> comp) {
        this.comp = comp;
        sentinel = new Node(null);
        sentinel.left = sentinel;
        sentinel.right = sentinel;
        sentinel.parent = sentinel;
        sentinel.size = 0;
        sentinel.color = Node.BLACK;
        root = sentinel;
    }

    public void insert(T key) {
        if (contains(key))
            return;

        Node newNode = new Node(key);
        Node p = sentinel;
        Node n = root;
        while (n != sentinel) {
            p = n;
            if (comp.compare(newNode.key, n.key) < 0)
                n = n.left;
            else
                n = n.right;
        }
        newNode.parent = p;
        if (p == sentinel)
            root = newNode;
        else if (comp.compare(newNode.key, p.key) < 0)
            p.left = newNode;
        else
            p.right = newNode;
        insertFixup(newNode);
    }

    public void remove(T key) {
        Node nodeToDelete = search(key);
        if (nodeToDelete == sentinel)
            return;

        int yOriginalColor = nodeToDelete.color;
        Node n;
        if (nodeToDelete.left == sentinel) {
            n = nodeToDelete.right;
            transplant(nodeToDelete, nodeToDelete.right);
        }
        else if (nodeToDelete.right == sentinel) {
            n = nodeToDelete.left;
            transplant(nodeToDelete, nodeToDelete.left);
        }
        else {
            Node min = minimum(nodeToDelete.right);
            yOriginalColor = min.color;
            n = min.right;
            if (min.parent == nodeToDelete)
                n.parent = min;
            else {
                transplant(min, min.right);
                min.right = nodeToDelete.right;
                min.right.parent = min;
            }
            transplant(nodeToDelete, min);
            min.left = nodeToDelete.left;
            min.left.parent = min;
            min.color = nodeToDelete.color;
        }

        Node t = n.parent;
        while (t != sentinel) {
            t.size--;
            t = t.parent;
        }

        if (yOriginalColor == Node.BLACK)
            removeFixup(n);
    }

    public T select(int i) {
        return select(root, i).key;
    }

    private Node select(Node x, int i) {
        int r = x.left.size + 1;
        if (i == r)
            return x;
        else if (i < r)
            return select(x.left, i);
        else
            return select(x.right, i - r);
    }

    public boolean contains(T key) {
        Node z = search(key);
        return z != sentinel;
    }

    public int size() {
        return root == null ? 0 : root.size;
    }

    public int rank(T key) {
        Node x = search(key);
        if (x == sentinel)
            return -1;
        return rank(x);
    }

    private int rank(Node n) {
        int r = n.left.size + 1;
        Node t = n;
        while (t != sentinel) {
            if (t == t.parent.right)
                r += t.parent.left.size + 1;
            t = t.parent;
        }
        return r;
    }

    public void printInOrder() {
        printInOrder(root);
    }

    private void printInOrder(Node node) {
        if (node == sentinel)
            return;
        printInOrder(node.left);
        System.out.print(node.key + " ");
        printInOrder(node.right);
    }

    private Node search(T key) {
        Node n = root;
        while (n != sentinel && comp.compare(n.key, key) != 0) {
            if (comp.compare(key, n.key) < 0)
                n = n.left;
            else
                n = n.right;
        }
        return n;
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
        b.size = a.size;
        a.size = a.left.size + a.right.size + 1;
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
        b.size = a.size;
        a.size = a.left.size + a.right.size + 1;
    }

    private void insertFixup(Node n) {
        Node t = n.parent;
        while (t != sentinel) {
            t.size++;
            t = t.parent;
        }

        while (n.parent.color == Node.RED) {
            if (n.parent == n.parent.parent.left) {
                Node u = n.parent.parent.right;
                if (u.color == Node.RED) {
                    n.parent.color = Node.BLACK;
                    u.color = Node.BLACK;
                    n.parent.parent.color = Node.RED;
                    n = n.parent.parent;
                }
                else {
                    if (n == n.parent.right) {
                        n = n.parent;
                        leftRotate(n);
                    }
                    n.parent.color = Node.BLACK;
                    n.parent.parent.color = Node.RED;
                    rightRotate(n.parent.parent);
                }
            }
            else {
                Node u = n.parent.parent.left;
                if (u.color == Node.RED) {
                    n.parent.color = Node.BLACK;
                    u.color = Node.BLACK;
                    n.parent.parent.color = Node.RED;
                    n = n.parent.parent;
                }
                else {
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
                }
                else {
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
            }
            else {
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
                }
                else {
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
            OrderStatisticsTree<Integer> ost = new OrderStatisticsTree<>(Integer::compareTo);
            for (int i = 0; i < 18; i++)
                ost.insert(i);
            System.out.println("Size: " + ost.size());
            for (int i = 0; i < 18; i++)
                System.out.print(ost.select(i + 1) + " ");
            System.out.println();
            for (int i = 0; i < 18; i += 2)
                ost.remove(i);
            for (int i = 0; i < ost.size(); i++)
                System.out.print(ost.select(i + 1) + " ");
        }
    }
}
