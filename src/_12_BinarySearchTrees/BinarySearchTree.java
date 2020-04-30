package _12_BinarySearchTrees;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class BinarySearchTree<T>
{
    private class Node
    {
        private T key;
        private Node left;
        private Node right;

        public Node(T key) {
            this.key = key;
        }
    }

    private Node root;
    private Comparator<T> comp;
    private int size = 0;

    /**
     * Creates BST with a given comparator
     *
     * @param comp comparator which will be used to compare elements in the BST
     */
    public BinarySearchTree(Comparator<T> comp) {
        this.comp = comp;
    }

    /**
     * Inserts given value into the BST
     *
     * @param v value to be inserted
     */
    public void insert(T v) {
        Node newNode = new Node(v);
        Node p = null;
        Node n = root;

        while (n != null) {
            p = n;
            if (comp.compare(v, n.key) < 0)
                n = n.left;
            else
                n = n.right;
        }

        if (p == null)
            root = newNode;
        else if (comp.compare(v, p.key) < 0)
            p.left = newNode;
        else
            p.right = newNode;
        size++;
    }

    /**
     * Removes given value from the BST if it's present
     *
     * @param v value to be removed
     */
    public void remove(T v) {
        root = remove(root, v);
    }

    /**
     * @param n starting node
     * @param v value to be removed
     * @return node that took place of the one given as the param
     */
    private Node remove(Node n, T v) {
        if (n == null)
            return null;

        if (v == n.key) {
            size--;
            if (n.left == null && n.right == null)
                return null;
            if (n.left == null)
                return n.right;
            if (n.right == null)
                return n.left;
            // if node has two children
            T min = minimum(n.right).key;
            n.key = min;
            n.right = remove(n.right, min);
            // if we are removing min from a subtree we have to now negate the first size--
            size++;
            return n;
        } else if (comp.compare(v, n.key) < 0) {
            n.left = remove(n.left, v);
            return n;
        } else {
            n.right = remove(n.right, v);
            return n;
        }
    }

    /**
     * Checks whether the BST contains a given value
     *
     * @param v value to be checked
     * @return true if value is found, false otherwise
     */
    public boolean contains(T v) {
        return search(v) != null;
    }

    /**
     * @return true if the BST is empty, false otherwise
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * @return number of elements in the tree
     */
    public int size() {
        return size;
    }

    /**
     * @return minimum value in the BST or null if the tree is empty
     */
    public T minimum() {
        if (root == null)
            return null;
        return minimum(root).key;
    }

    /**
     * @return maximum value in the BST or null if the tree is empty
     */
    public T maximum() {
        if (root == null)
            return null;
        return maximum(root).key;
    }

    /**
     * @param n starting node
     * @return node with minimum value, starting from n
     */
    private Node minimum(Node n) {
        Node t = n;
        while (t.left != null) {
            t = t.left;
        }
        return t;
    }

    /**
     * @param n starting node
     * @return node with maximum value, starting from n
     */
    private Node maximum(Node n) {
        Node t = n;
        while (t.right != null) {
            t = t.right;
        }
        return t;
    }

    /**
     * @param v value to be searched
     * @return node with a given value or null if it's not found
     */
    private Node search(T v) {
        Node n = root;
        while (n != null && n.key != v) {
            if (comp.compare(v, n.key) < 0)
                n = n.left;
            else
                n = n.right;
        }
        return n;
    }

    /**
     * @param v value whose successor is to be found
     * @return successor of v, it means element which comes after v in
     *         an inorder traversal or null if there is no such element
     */
    public T successor(T v) {
        Node n = successor(root, v);
        return n == null ? null : n.key;
    }

    /**
     * @param n starting node
     * @param v value whose successor is to be found
     * @return successor node
     */
    private Node successor(Node n, T v) {
        if (n == null)
            return null;

        if (n.key == v) {
            if (n.right != null)
                return minimum(n.right);
            return null;
        } else if (comp.compare(v, n.key) < 0) {
            Node t = successor(n.left, v);
            return t == null ? n : t;
        } else {
            return successor(n.right, v);
        }
    }

    /**
     * @param v value whose predecessor is to be found
     * @return predecessor of v, it means element which comes before v in
     *         an inorder traversal or null if there is no such element
     */
    public T predecessor(T v) {
        Node n = predecessor(root, v);
        return n == null ? null : n.key;
    }

    /**
     * @param n starting node
     * @param v value whose predecessor is to be found
     * @return predecessor node
     */
    private Node predecessor(Node n, T v) {
        if (n == null)
            return null;

        if (n.key == v) {
            if (n.left != null)
                return maximum(n.left);
            return null;
        } else if (comp.compare(v, n.key) > 0) {
            Node t = predecessor(n.right, v);
            return t == null ? n : t;
        } else {
            return predecessor(n.left, v);
        }
    }

    /**
     * Traverses tree in a preorder way (root first, children next)
     *
     * @return list containing elements in the given order
     */
    public List<T> preorderWalk() {
        List<T> l = new LinkedList<>();
        preorderWalk(root, l);
        return l;
    }

    private void preorderWalk(Node n, List<T> l) {
        if (n == null)
            return;
        l.add(n.key);
        preorderWalk(n.left, l);
        preorderWalk(n.right, l);
    }

    /**
     * Traverses tree in a inorder way (left child first, root and right one next)
     *
     * @return list containing elements in the given order
     */
    public List<T> inOrderWalk() {
        List<T> l = new LinkedList<>();
        inOrderWalk(root, l);
        return l;
    }

    private void inOrderWalk(Node n, List<T> l) {
        if (n == null)
            return;
        inOrderWalk(n.left, l);
        l.add(n.key);
        inOrderWalk(n.right, l);
    }

    /**
     * Traverses tree in a postorder way (children first, root next)
     *
     * @return list containing elements in the given order
     */
    public List<T> postOrderWalk() {
        List<T> l = new LinkedList<>();
        postOrderWalk(root, l);
        return l;
    }

    private void postOrderWalk(Node n, List<T> l) {
        if (n == null)
            return;
        postOrderWalk(n.left, l);
        postOrderWalk(n.right, l);
        l.add(n.key);
    }

    private static class Example
    {
        public static void main(String[] args) {
            BinarySearchTree<Integer> bst = new BinarySearchTree<>(Integer::compareTo);
            Integer[] nodes = {10, 6, 13, 4, 8, 11, 14, 5, 7, 9, 12, 15};
            for (Integer i : nodes)
                bst.insert(i);

            System.out.println("Preorder walk: " + bst.preorderWalk());
            System.out.println("Inorder walk: " + bst.inOrderWalk());
            System.out.println("Postorder walk: " + bst.postOrderWalk());

            System.out.println("Successor of 5: " + bst.successor(5));
            System.out.println("Predecessor of 6: " + bst.predecessor(6));
            System.out.println("Successor of 10: " + bst.successor(10));
            System.out.println("Predecessor of 11: " + bst.predecessor(11));

            System.out.println("\nTree min: " + bst.minimum());
            System.out.println("Tree max: " + bst.maximum());

            System.out.println("\nContains 10? - " + bst.contains(10));
            System.out.println("Contains 2? - " + bst.contains(2));
            System.out.println("Size: " + bst.size());

            System.out.println("\nRemoving 10 and 8...");
            bst.remove(10);
            bst.remove(8);

            System.out.println("\nContains 10? - " + bst.contains(10));
            System.out.println("Inorder walk: " + bst.inOrderWalk());
            System.out.println("Size: " + bst.size());
        }
    }
}
