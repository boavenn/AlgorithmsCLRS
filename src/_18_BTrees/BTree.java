package _18_BTrees;

import java.util.Comparator;

/*
 * B-Tree properties:
 * 1. Every node has n keys sorted in increasing
 * 2. Every internal (non-leaf) node has n + 1 children
 * 3. All leaves are at the same level
 * 4. Every node except root must contain atleast M - 1 keys
 * 5. All nodes may contain at most 2M - 1 keys
 *
 * M - tree order (note that definition of a tree order is inconsistent)
 */
public class BTree<T>
{
    private class Node
    {
        private int n;
        private T[] keys;
        private boolean isLeaf;
        private Node[] children;
        private Node parent;

        @SuppressWarnings("unchecked")
        public Node(boolean isLeaf) {
            this.isLeaf = isLeaf;
            this.keys = (T[]) new Object[2 * order - 1];
            this.children = new BTree.Node[2 * order];
            this.n = 0;
        }

        public Node search(T key) {
            int i = 0;
            while (i < n && comp.compare(keys[i], key) < 0)
                i++;

            if (i < n && comp.compare(keys[i], key) == 0)
                return this;
            else if (isLeaf)
                return null;
            else
                return children[i].search(key);
        }

        public void addKey(T key) {
            int i = n - 1;
            while (i >= 0 && comp.compare(keys[i], key) > 0) {
                keys[i + 1] = keys[i];
                i--;
            }
            keys[i + 1] = key;
            n++;
        }

        public T removeKey(int idx) {
            if (idx < 0 || idx >= n)
                throw new IllegalArgumentException();

            T v = keys[idx];
            int j;
            for (j = idx; j < n - 1; j++)
                keys[j] = keys[j + 1];
            keys[j] = null;
            n--;
            return v;
        }

        public int indexOf(T key) {
            int i = 0;
            while (i < n && comp.compare(keys[i], key) != 0)
                i++;
            if (i == n)
                return -1;
            return i;
        }

        public T removeKey(T key) {
            return removeKey(indexOf(key));
        }

        public void appendChild(Node c) {
            children[n] = c;
            c.parent = this;
        }

        public void prependChild(Node c) {
            if (n > 0)
                System.arraycopy(children, 0, children, 1, n);
            children[0] = c;
            c.parent = this;
        }

        public Node removeChild(int idx) {
            if (idx < 0 || idx > n)
                throw new IllegalArgumentException();

            Node c = children[idx];
            int j;
            for (j = idx; j < n; j++)
                children[j] = children[j + 1];
            children[j] = null;
            return c;
        }
    }

    private Comparator<T> comp;
    private Node root;
    private int order;
    private int size;
    private int minKeys;
    private int minChildren;
    private int maxKeys;
    private int maxChildren;

    public BTree(int order, Comparator<T> comp) {
        this.comp = comp;
        this.order = order;
        this.minKeys = order - 1;
        this.minChildren = order;
        this.maxKeys = 2 * order - 1;
        this.maxChildren = 2 * order;
        this.root = new Node(true);
    }

    private int indexOf(Node x) {
        if (x.parent == null)
            return -1;
        Node p = x.parent;
        int i = 0;
        while (p.children[i] != x)
            i++;
        return i;
    }

    private Node rightSiblingOf(Node x) {
        int i = indexOf(x);
        if (i == -1)
            return null;
        else if (i < x.parent.n)
            return x.parent.children[i + 1];
        else
            return null;
    }

    private Node leftSiblingOf(Node x) {
        int i = indexOf(x);
        if (i == -1)
            return null;
        else if (i > 0)
            return x.parent.children[i - 1];
        else
            return null;
    }

    private Node leftSubtreeOf(Node x, T key) {
        return x.children[x.indexOf(key)];
    }

    private Node rightSubtreeOf(Node x, T key) {
        return x.children[x.indexOf(key) + 1];
    }

    private Node predecessorLeafOf(Node x, T key) {
        Node subtree = leftSubtreeOf(x, key);
        while (!subtree.isLeaf) {
            subtree = subtree.children[subtree.n];
        }
        return subtree;
    }

    private Node successorLeafOf(Node x, T key) {
        Node subtree = rightSubtreeOf(x, key);
        while (!subtree.isLeaf) {
            subtree = subtree.children[0];
        }
        return subtree;
    }

    private void rightRotation(Node leftSiblingOfX, Node x) {
        Node parent = x.parent;
        x.addKey(parent.removeKey(indexOf(leftSiblingOfX)));
        parent.addKey(leftSiblingOfX.removeKey(leftSiblingOfX.n - 1));
        if (!x.isLeaf)
            x.prependChild(leftSiblingOfX.removeChild(leftSiblingOfX.n));
    }

    private void leftRotation(Node rightSiblingOfX, Node x) {
        Node parent = x.parent;
        x.addKey(parent.removeKey(indexOf(rightSiblingOfX) - 1));
        parent.addKey(rightSiblingOfX.removeKey(0));
        if (!x.isLeaf)
            x.appendChild(rightSiblingOfX.removeChild(0));
    }

    private void merge(Node left, Node right) {
        Node parent = left.parent;
        parent.removeChild(indexOf(right));
        left.addKey(parent.removeKey(indexOf(left)));

        if (left.isLeaf) {
            // leaf has no children so only keys have to be moved
            for (int i = 0; i < right.n; i++)
                left.addKey(right.keys[i]);
        } else {
            // if a node isn't leaf we need to move children as well
            int i;
            for (i = 0; i < right.n; i++) {
                left.appendChild(right.children[i]);
                left.addKey(right.keys[i]);
            }
            left.appendChild(right.children[i]);
        }

        // if now root is empty we simply shrink our tree
        if (parent == root && parent.n == 0) {
            left.parent = null;
            root = left;
        }
        // if now parent of the nodes is deficient we need to rebalance it as well
        else if (parent != root && parent.n < minKeys)
            rebalance(parent);
    }

    private void rebalance(Node x) {
        // check right sibling of x
        Node rightSibling = rightSiblingOf(x);
        if (rightSibling != null && rightSibling.n > minKeys) {
            leftRotation(rightSibling, x);
            return;
        }

        // check left sibling of x
        Node leftSibling = leftSiblingOf(x);
        if (leftSibling != null && leftSibling.n > minKeys) {
            rightRotation(leftSibling, x);
            return;
        }

        // if we cant borrow any keys from either right or left sibling we need to merge
        if (leftSibling != null)
            merge(leftSibling, x);
        else
            merge(x, rightSibling);
    }

    private void splitChild(Node n, int idx) {
        Node y = n.children[idx];
        Node z = new Node(y.isLeaf);
        z.parent = n;
        z.n = minKeys;
        // move half of the keys from y to z
        for (int j = 0; j < minKeys; j++) {
            z.keys[j] = y.keys[order + j];
            y.keys[order + j] = null;
        }
        // if y isn't leaf (it means it has children) we need to move them as well
        if (!y.isLeaf) {
            for (int j = 0; j < minChildren; j++) {
                z.children[j] = y.children[order + j];
                z.children[j].parent = z;
                y.children[order + j] = null;
            }
        }
        y.n = minKeys;

        // insert z to parent n
        for (int j = n.n; j >= idx; j--)
            n.children[j + 1] = n.children[j];
        n.children[idx + 1] = z;
        // insert new key to parent n
        for (int j = n.n - 1; j >= idx; j--)
            n.keys[j + 1] = n.keys[j];
        n.keys[idx] = y.keys[minKeys];
        y.keys[minKeys] = null;
        n.n++;
    }

    public boolean contains(T key) {
        return root.search(key) != null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void insert(T key) {
        Node r = root;
        // if root is full we split it and make additional level in our tree
        if (r.n == maxKeys) {
            Node s = new Node(false);
            r.parent = s;
            root = s;
            s.children[0] = r;
            splitChild(s, 0);
            insertNonfull(s, key);
        } else
            insertNonfull(r, key);
        size++;
    }

    private void insertNonfull(Node x, T key) {
        int i = x.n - 1;
        if (x.isLeaf) {
            x.addKey(key);
        } else {
            // look for a correct child
            while (i >= 0 && comp.compare(x.keys[i], key) > 0)
                i--;
            i++;
            // if child is full we split it
            if (x.children[i].n == maxKeys) {
                splitChild(x, i);
                if (comp.compare(x.keys[i], key) < 0)
                    i++;
            }
            // insert key to correct child
            insertNonfull(x.children[i], key);
        }
    }

    public T remove(T key) {
        Node x = root.search(key);
        if (x == null)
            return null;

        size--;
        if (x.isLeaf)
            return removeFromLeaf(x, key);
        else
            return removeFromInternal(x, key);
    }

    private T removeFromLeaf(Node x, T key) {
        T v = x.removeKey(key);
        if (x.n < minKeys)
            rebalance(x);
        return v;
    }

    private T removeFromInternal(Node x, T key) {
        T v = x.keys[x.indexOf(key)];
        Node predecessorLeaf = predecessorLeafOf(x, key);
        if (predecessorLeaf.n > minKeys) {
            // replace our key with predecessor key
            x.keys[x.indexOf(key)] = predecessorLeaf.removeKey(predecessorLeaf.n - 1);
        } else {
            // if predecessor leaf would be deficient after key removal we look for a successor
            Node successorLeaf = successorLeafOf(x, key);
            x.keys[x.indexOf(key)] = successorLeaf.removeKey(0);
            // now if successor leaf is deficient we start a tree rebalance from the leaf to the top
            if (successorLeaf.n < minKeys)
                rebalance(successorLeaf);
        }
        return v;
    }

    private static class Example
    {
        public static void main(String[] args) {
            BTree<Integer> btree = new BTree<>(3, Integer::compareTo);

            Integer[] arr = {4, 5, 6, 10, 14, 15, 16, 20, 23, 27, 50, 51, 52, 60, 64, 65, 68, 70, 72, 73, 75};
            for (Integer i : arr)
                btree.insert(i);

            System.out.println(btree.size());
            btree.remove(70);
            btree.remove(51);
            btree.remove(64);
            btree.remove(72);
            System.out.println(btree.size());
        }
    }
}
