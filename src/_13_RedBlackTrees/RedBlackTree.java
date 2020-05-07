package _13_RedBlackTrees;

import _12_BinarySearchTrees.BinarySearchTree;

import java.util.Comparator;

/*
 * Red-black tree properties:
 * 1. Each node is either red or black
 * 2. The root is black
 * 3. All leaves (NIL or sentinel) are black
 * 4. If a node is red, then both its children are black
 * 5. Every path from a given node to any of its descendant NIL nodes goes
 *    through the same number of black nodes
 */
public class RedBlackTree<T>
{
    private class Node
    {
        public static final int BLACK = 1;
        public static final int RED = 0;
        private T key;
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
    private int size;

    public RedBlackTree(Comparator<T> comp) {
        this.comp = comp;
        sentinel = new Node(null);
        sentinel.left = sentinel;
        sentinel.right = sentinel;
        sentinel.parent = sentinel;
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
            if (comp.compare(key, n.key) < 0)
                n = n.left;
            else
                n = n.right;
        }
        newNode.parent = p;
        if (p == sentinel)
            root = newNode;
        else if (comp.compare(key, p.key) < 0)
            p.left = newNode;
        else
            p.right = newNode;

        insertFixup(newNode);
        size++;
    }

    public void remove(T key) {
        Node nodeToDelete = search(key);
        if (nodeToDelete == sentinel)
            return;

        int oldColor = nodeToDelete.color;
        Node n;
        if (nodeToDelete.left == sentinel) {
            n = nodeToDelete.right;
            transplant(nodeToDelete, nodeToDelete.right);
        } else if (nodeToDelete.right == sentinel) {
            n = nodeToDelete.left;
            transplant(nodeToDelete, nodeToDelete.left);
        } else {
            Node min = minimum(nodeToDelete.right);
            oldColor = min.color;
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
        if (oldColor == Node.BLACK)
            removeFixup(n);
        size--;
    }

    public boolean contains(T key) {
        return search(key) != sentinel;
    }

    public boolean isEmpty() {
        return root == sentinel;
    }

    public int size() {
        return size;
    }

    public T minimum() {
        return isEmpty() ? null : minimum(root).key;
    }

    private Node minimum(Node n) {
        Node t = n;
        while (t.left != sentinel) {
            t = t.left;
        }
        return t;
    }

    public T maximum() {
        return isEmpty() ? null : maximum(root).key;
    }

    private Node maximum(Node n) {
        Node t = n;
        while (t.right != sentinel) {
            t = t.right;
        }
        return t;
    }

    public T successor(T key) {
        Node n = successor(root, key);
        return n == sentinel ? null : n.key;
    }

    private Node successor(Node n, T key) {
        if (n == sentinel)
            return sentinel;

        if (n.key == key) {
            if (n.right != sentinel)
                return minimum(n.right);
            return sentinel;
        } else if (comp.compare(key, n.key) < 0) {
            Node t = successor(n.left, key);
            return t == sentinel ? n : t;
        } else {
            return successor(n.right, key);
        }
    }

    public T predecessor(T key) {
        Node n = predecessor(root, key);
        return n == sentinel ? null : n.key;
    }

    private Node predecessor(Node n, T key) {
        if (n == sentinel)
            return sentinel;

        if (n.key == key) {
            if (n.left != sentinel)
                return maximum(n.left);
            return sentinel;
        } else if (comp.compare(key, n.key) > 0) {
            Node t = predecessor(n.right, key);
            return t == sentinel ? n : t;
        } else {
            return predecessor(n.left, key);
        }
    }

    private Node search(T key) {
        Node n = root;
        while (n != sentinel && n.key != key) {
            if (comp.compare(key, n.key) < 0)
                n = n.left;
            else
                n = n.right;
        }
        return n;
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
    }

    /*
     * There are 4 cases to handle after insertion of a new node N:
     * 1. Uncle U of N is red
     * 2. Uncle U of N is black and N is the right child
     * 3. Uncle U of N is black and N is the left child
     * 4. N is the root node
     */
    private void insertFixup(Node n) {
        while (n.parent.color == Node.RED) {
            if (n.parent == n.parent.parent.left) {
                Node u = n.parent.parent.right;
                // case 1
                if (u.color == Node.RED) {
                    n.parent.color = Node.BLACK;
                    u.color = Node.BLACK;
                    n.parent.parent.color = Node.RED;
                    n = n.parent.parent;
                } else {
                    // case 2
                    if (n == n.parent.right) {
                        n = n.parent;
                        leftRotate(n);
                    }
                    // case 3
                    n.parent.color = Node.BLACK;
                    n.parent.parent.color = Node.RED;
                    rightRotate(n.parent.parent);
                }
            } else {
                Node u = n.parent.parent.left;
                // case 1
                if (u.color == Node.RED) {
                    n.parent.color = Node.BLACK;
                    u.color = Node.BLACK;
                    n.parent.parent.color = Node.RED;
                    n = n.parent.parent;
                } else {
                    // case 2
                    if (n == n.parent.left) {
                        n = n.parent;
                        rightRotate(n);
                    }
                    // case 3
                    n.parent.color = Node.BLACK;
                    n.parent.parent.color = Node.RED;
                    leftRotate(n.parent.parent);
                }
            }
        }
        // case 4
        root.color = Node.BLACK;
    }

    /*
     * There are 4 cases to handle after removal of a node:
     * (N is the node which replaced the removed one)
     * 1. Sibling S of N is red
     * 2. Sibling S of N is black and its both children are black
     * 3. Sibling S of N is black and its left child is red and right is black
     * 4. Sibling S of N is black and its right child is red (left one may have any color)
     */
    private void removeFixup(Node n) {
        while (n != root && n.color == Node.BLACK) {
            if (n == n.parent.left) {
                Node s = n.parent.right;
                // case 1
                if (s.color == Node.RED) {
                    s.color = Node.BLACK;
                    n.parent.color = Node.RED;
                    leftRotate(n.parent);
                    s = n.parent.right;
                }
                // case 2
                if (s.left.color == Node.BLACK && s.right.color == Node.BLACK) {
                    s.color = Node.RED;
                    n = n.parent;
                } else {
                    // case 3
                    if (s.right.color == Node.BLACK) {
                        s.left.color = Node.BLACK;
                        s.color = Node.RED;
                        rightRotate(s);
                        s = n.parent.right;
                    }
                    // case 4
                    s.color = n.parent.color;
                    n.parent.color = Node.BLACK;
                    s.right.color = Node.BLACK;
                    leftRotate(n.parent);
                    n = root;
                }
            } else {
                Node s = n.parent.left;
                // case 1
                if (s.color == Node.RED) {
                    s.color = Node.BLACK;
                    n.parent.color = Node.RED;
                    rightRotate(n.parent);
                    s = n.parent.left;
                }
                // case 2
                if (s.right.color == Node.BLACK && s.left.color == Node.BLACK) {
                    s.color = Node.RED;
                    n = n.parent;
                } else {
                    // case 3
                    if (s.left.color == Node.BLACK) {
                        s.right.color = Node.BLACK;
                        s.color = Node.RED;
                        leftRotate(s);
                        s = n.parent.left;
                    }
                    // case 4
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
            RedBlackTree<Integer> rbt = new RedBlackTree<>(Integer::compareTo);
            Integer[] n = {11, 2, 14, 15, 1, 7, 8, 5, 4};
            for (Integer i : n)
                rbt.insert(i);
            System.out.println(rbt.minimum());
            System.out.println(rbt.maximum());
            System.out.println(rbt.isEmpty());
            System.out.println(rbt.size());
            rbt.remove(8);
            rbt.remove(2);
        }
    }
}
