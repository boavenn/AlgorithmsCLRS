package _14_AugmentingDataStructures;

import java.util.Random;

public class OrderStatisticsTree<T extends Comparable<T>>
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

    private Node sentinel;
    private Node root;

    public OrderStatisticsTree() {
        sentinel = new Node(null);
        sentinel.left = sentinel;
        sentinel.right = sentinel;
        sentinel.parent = sentinel;
        sentinel.size = 0;
        sentinel.color = Node.BLACK;
        root = sentinel;
    }

    public void insert(T key) {
        Node z = new Node(key);
        Node y = sentinel;
        Node x = root;
        while (x != sentinel) {
            y = x;
            if (z.key.compareTo(x.key) < 0)
                x = x.left;
            else
                x = x.right;
        }
        z.parent = y;
        if (y == sentinel)
            root = z;
        else if (z.key.compareTo(y.key) < 0)
            y.left = z;
        else
            y.right = z;
        insertFixup(z);
    }

    public void remove(T key) {
        Node z = search(key);
        if (z == sentinel)
            return;
        Node y = z;
        int yOriginalColor = y.color;
        Node x;
        if (z.left == sentinel) {
            x = z.right;
            transplant(z, z.right);
        }
        else if (z.right == sentinel) {
            x = z.left;
            transplant(z, z.left);
        }
        else {
            y = minimum(z.right);
            yOriginalColor = y.color;
            x = y.right;
            if (y.parent == z)
                x.parent = y;
            else {
                transplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }
        if (yOriginalColor == Node.BLACK)
            removeFixup(x);
    }

    public boolean contains(T key) {
        Node z = search(key);
        return z != sentinel;
    }

    public void printInOrder() {
        printInOrder(root);
    }

    public T select(int i) {
        return select(root, i).key;
    }

    public int rank(T key) {
        Node x = search(key);
        if (x == sentinel)
            return -1;
        return rank(x);
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

    private int rank(Node x) {
        int r = x.left.size + 1;
        Node y = x;
        while (y != sentinel) {
            if (y == y.parent.right)
                r += y.parent.left.size + 1;
            y = y.parent;
        }
        return r;
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
        while (n != sentinel && n.key != key) {
            if (key.compareTo(n.key) < 0)
                n = n.left;
            else
                n = n.right;
        }
        return n;
    }

    private Node minimum(Node n) {
        if (n == sentinel)
            return null;
        Node temp = n;
        while (temp.left != sentinel) {
            temp = temp.left;
        }
        return temp;
    }

    private Node maximum(Node n) {
        if (n == sentinel)
            return null;
        Node temp = n;
        while (temp.right != sentinel) {
            temp = temp.right;
        }
        return temp;
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

    private void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != sentinel)
            y.left.parent = x;
        y.parent = x.parent;
        if (x.parent == sentinel)
            root = y;
        else if (x == x.parent.left)
            x.parent.left = y;
        else
            x.parent.right = y;
        y.left = x;
        x.parent = y;
        y.size = x.size;
        x.size = x.left.size + x.right.size + 1;
    }

    private void rightRotate(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != sentinel)
            y.right.parent = x;
        y.parent = x.parent;
        if (x.parent == sentinel)
            root = y;
        else if (x == x.parent.left)
            x.parent.left = y;
        else
            x.parent.right = y;
        y.right = x;
        x.parent = y;
        y.size = x.size;
        x.size = x.left.size + x.right.size + 1;
    }

    private void insertFixup(Node z) {
        Node temp = z.parent;
        while (temp != sentinel) {
            temp.size++;
            temp = temp.parent;
        }
        while (z.parent.color == Node.RED) {
            if (z.parent == z.parent.parent.left) {
                Node y = z.parent.parent.right;
                if (y.color == Node.RED) {
                    z.parent.color = Node.BLACK;
                    y.color = Node.BLACK;
                    z.parent.parent.color = Node.RED;
                    z = z.parent.parent;
                }
                else {
                    if (z == z.parent.right) {
                        z = z.parent;
                        leftRotate(z);
                    }
                    z.parent.color = Node.BLACK;
                    z.parent.parent.color = Node.RED;
                    rightRotate(z.parent.parent);
                }
            }
            else {
                Node y = z.parent.parent.left;
                if (y.color == Node.RED) {
                    z.parent.color = Node.BLACK;
                    y.color = Node.BLACK;
                    z.parent.parent.color = Node.RED;
                    z = z.parent.parent;
                }
                else {
                    if (z == z.parent.left) {
                        z = z.parent;
                        rightRotate(z);
                    }
                    z.parent.color = Node.BLACK;
                    z.parent.parent.color = Node.RED;
                    leftRotate(z.parent.parent);
                }
            }
        }
        root.color = Node.BLACK;
    }

    private void removeFixup(Node x) {
        Node temp = x.parent;
        while (temp != sentinel) {
            temp.size--;
            temp = temp.parent;
        }
        while (x != root && x.color == Node.BLACK) {
            if (x == x.parent.left) {
                Node w = x.parent.right;
                if (w.color == Node.RED) {
                    w.color = Node.BLACK;
                    x.parent.color = Node.RED;
                    leftRotate(x.parent);
                    w = x.parent.right;
                }
                if (w.left.color == Node.BLACK && w.right.color == Node.BLACK) {
                    w.color = Node.RED;
                    x = x.parent;
                }
                else {
                    if (w.right.color == Node.BLACK) {
                        w.left.color = Node.BLACK;
                        w.color = Node.RED;
                        rightRotate(w);
                        w = x.parent.right;
                    }
                    w.color = x.parent.color;
                    x.parent.color = Node.BLACK;
                    w.right.color = Node.BLACK;
                    leftRotate(x.parent);
                    x = root;
                }
            }
            else {
                Node w = x.parent.left;
                if (w.color == Node.RED) {
                    w.color = Node.BLACK;
                    x.parent.color = Node.RED;
                    rightRotate(x.parent);
                    w = x.parent.left;
                }
                if (w.right.color == Node.BLACK && w.left.color == Node.BLACK) {
                    w.color = Node.RED;
                    x = x.parent;
                }
                else {
                    if (w.left.color == Node.BLACK) {
                        w.right.color = Node.BLACK;
                        w.color = Node.RED;
                        leftRotate(w);
                        w = x.parent.left;
                    }
                    w.color = x.parent.color;
                    x.parent.color = Node.BLACK;
                    w.left.color = Node.BLACK;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }
        x.color = Node.BLACK;
    }

    private static class Example
    {
        public static void main(String[] args) {
            OrderStatisticsTree<Integer> ost = new OrderStatisticsTree<>();
            Random r = new Random();
            for (int i = 0; i < 18; i++)
                ost.insert(i + r.nextInt(5));
            for (int i = 0; i < 18; i++)
                System.out.println(ost.select(i + 1));
            System.out.println();
            for (int i = 0; i < 9; i++)
                ost.remove(i + r.nextInt(5));
            for (int i = 0; i < 18; i++)
                System.out.println(ost.select(i + 1));
        }
    }
}
