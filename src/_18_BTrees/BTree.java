package _18_BTrees;

/*
B-tree properties:
1. Every node x has given properties:
   a) n - number of keys
   b) n keys sorted in increasing order
   c) isLeaf - true if x is leaf, false otherwise
2. Every internal node contains x.n + 1 pointers to its children
3. All leaves are at the same level
4. Every node except root must contain atleast t - 1 keys. Root may contain minimum 1 key
5. All nodes (including root) may contain at most 2t - 1 keys

t - tree order

A lot of assumptions have been made to make the code cleaner, so
normally you'd want to throw some exceptions here and there
 */

public class BTree<T extends Comparable<T>>
{
    private static class Node<T extends Comparable<T>>
    {
        private int n;
        private T[] keys;
        private boolean isLeaf;
        private Node<T>[] children;
        private Node<T> parent;

        @SuppressWarnings("unchecked")
        public Node(int t, boolean isLeaf) {
            this.isLeaf = isLeaf;
            this.keys = (T[]) new Comparable[2 * t - 1];
            this.children = new Node[2 * t];
            this.n = 0;
        }

        public Node<T> search(T key) {
            int i = 0;
            while (i < n && keys[i].compareTo(key) < 0)
                i++;

            if (i < n && keys[i].compareTo(key) == 0)
                return this;
            else if (isLeaf)
                return null;
            else
                return children[i].search(key);
        }

        // we assume that n < maxKeys
        public void addKey(T key) {
            int i = n - 1;
            while (i >= 0 && keys[i].compareTo(key) > 0) {
                keys[i + 1] = keys[i];
                i--;
            }
            keys[i + 1] = key;
            n++;
        }

        // we assume that the idx is correct
        public T removeKey(int idx) {
            T v = keys[idx];
            int j;
            for (j = idx; j < n - 1; j++)
                keys[j] = keys[j + 1];
            keys[j] = null;
            n--;
            return v;
        }

        // we assume that the key exists in the array
        public int indexOf(T key) {
            int i = 0;
            while (keys[i].compareTo(key) != 0)
                i++;
            return i;
        }

        public T removeKey(T key) {
            return removeKey(indexOf(key));
        }

        public void appendChild(Node<T> c) {
            children[n] = c;
            c.parent = this;
        }

        public void prependChild(Node<T> c) {
            for (int i = n - 1; i >= 0; i--)
                children[i + 1] = children[i];
            children[0] = c;
            c.parent = this;
        }

        public Node<T> removeChild(int idx) {
            Node<T> c = children[idx];
            int j;
            for (j = idx; j < n; j++)
                children[j] = children[j + 1];
            children[j] = null;
            return c;
        }
    }

    private Node<T> root;
    private int t;
    private int minKeys;
    private int minChildren;
    private int maxKeys;
    private int maxChildren;

    public BTree(int t) {
        this.t = t;
        this.minKeys = t - 1;
        this.minChildren = t;
        this.maxKeys = 2 * t - 1;
        this.maxChildren = 2 * t;
        this.root = new Node<>(t, true);
    }

    private int indexOf(Node<T> x) {
        if (x.parent == null)
            return -1;
        Node<T> p = x.parent;
        int i = 0;
        while (p.children[i] != x)
            i++;
        return i;
    }

    private Node<T> rightSiblingOf(Node<T> x) {
        int i = indexOf(x);
        if (i == -1)
            return null;
        else if (i < x.parent.n)
            return x.parent.children[i + 1];
        else
            return null;
    }

    private Node<T> leftSiblingOf(Node<T> x) {
        int i = indexOf(x);
        if (i == -1)
            return null;
        else if (i > 0)
            return x.parent.children[i - 1];
        else
            return null;
    }

    private Node<T> leftSubtreeOf(Node<T> x, T key) {
        return x.children[x.indexOf(key)];
    }

    private Node<T> rightSubtreeOf(Node<T> x, T key) {
        return x.children[x.indexOf(key) + 1];
    }

    private Node<T> predecessorLeafOf(Node<T> x, T key) {
        Node<T> subtree = leftSubtreeOf(x, key);
        while (!subtree.isLeaf) {
            subtree = subtree.children[subtree.n];
        }
        return subtree;
    }

    private Node<T> successorLeafOf(Node<T> x, T key) {
        Node<T> subtree = rightSubtreeOf(x, key);
        while (!subtree.isLeaf) {
            subtree = subtree.children[0];
        }
        return subtree;
    }

    private void rightRotation(Node<T> leftSibling, Node<T> x) {
        Node<T> parent = x.parent;
        x.addKey(parent.removeKey(indexOf(leftSibling)));
        parent.addKey(leftSibling.removeKey(leftSibling.n - 1));
        if (!x.isLeaf)
            x.prependChild(leftSibling.removeChild(leftSibling.n));
    }

    private void leftRotation(Node<T> rightSibling, Node<T> x) {
        Node<T> parent = x.parent;
        x.addKey(parent.removeKey(indexOf(rightSibling) - 1));
        parent.addKey(rightSibling.removeKey(0));
        if (!x.isLeaf)
            x.appendChild(rightSibling.removeChild(0));
    }

    private void merge(Node<T> left, Node<T> right) {
        Node<T> parent = left.parent;
        parent.removeChild(indexOf(right));
        left.addKey(parent.removeKey(indexOf(left)));

        if (left.isLeaf) {
            // leaf has no children so only keys have to be moved
            for (int i = 0; i < right.n; i++)
                left.addKey(right.keys[i]);
        }
        else {
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

    private void rebalance(Node<T> x) {
        // check right sibling of x
        Node<T> rightSibling = rightSiblingOf(x);
        if (rightSibling != null && rightSibling.n > minKeys) {
            leftRotation(rightSibling, x);
            return;
        }

        // check left sibling of x
        Node<T> leftSibling = leftSiblingOf(x);
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

    private void splitChild(Node<T> x, int i) {
        Node<T> y = x.children[i];
        Node<T> z = new Node<>(t, y.isLeaf);
        z.parent = x;
        z.n = minKeys;
        // move half of the keys from y to z
        for (int j = 0; j < minKeys; j++) {
            z.keys[j] = y.keys[t + j];
            y.keys[t + j] = null;
        }
        // if y isn't leaf (it means it has children) we need to move them as well
        if (!y.isLeaf) {
            for (int j = 0; j < minChildren; j++) {
                z.children[j] = y.children[t + j];
                z.children[j].parent = z;
                y.children[t + j] = null;
            }
        }
        y.n = minKeys;

        // insert z to parent x
        for (int j = x.n; j >= i; j--)
            x.children[j + 1] = x.children[j];
        x.children[i + 1] = z;
        // insert new key to parent x
        for (int j = x.n - 1; j >= i; j--)
            x.keys[j + 1] = x.keys[j];
        x.keys[i] = y.keys[minKeys];
        y.keys[minKeys] = null;
        x.n++;
    }

    public void insert(T key) {
        Node<T> r = root;
        // if root is full we split it and make additional level in our tree
        if (r.n == maxKeys) {
            Node<T> s = new Node<>(t, false);
            r.parent = s;
            root = s;
            s.children[0] = r;
            splitChild(s, 0);
            insertNonfull(s, key);
        }
        else
            insertNonfull(r, key);
    }

    private void insertNonfull(Node<T> x, T key) {
        int i = x.n - 1;
        if (x.isLeaf) {
            x.addKey(key);
        }
        else {
            // look for a correct child
            while (i >= 0 && x.keys[i].compareTo(key) > 0)
                i--;
            i++;
            // if child is full we split it
            if (x.children[i].n == maxKeys) {
                splitChild(x, i);
                if (x.keys[i].compareTo(key) < 0)
                    i++;
            }
            // insert key to correct child
            insertNonfull(x.children[i], key);
        }
    }

    public T remove(T key) {
        Node<T> x = root.search(key);
        if (x == null)
            return null;
        // at this point we already know that the key exists in node x
        return remove(x, key);
    }

    // we assume that the key exists in the node
    private T remove(Node<T> x, T key) {
        if (x.isLeaf)
            return removeFromLeaf(x, key);
        else
            return removeFromInternal(x, key);
    }

    private T removeFromLeaf(Node<T> x, T key) {
        T v = x.removeKey(key);
        if (x.n < minKeys)
            rebalance(x);
        return v;
    }

    private T removeFromInternal(Node<T> x, T key) {
        T v = x.keys[x.indexOf(key)];
        Node<T> predecessorLeaf = predecessorLeafOf(x, key);
        if (predecessorLeaf.n > minKeys) {
            // replace our key with predecessor key
            x.keys[x.indexOf(key)] = predecessorLeaf.removeKey(predecessorLeaf.n - 1);
        }
        else {
            // if predecessor leaf would be deficient after key removal we look for a successor
            Node<T> successorLeaf = successorLeafOf(x, key);
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
            BTree<Integer> btree = new BTree<>(3);

            Integer[] arr = {
                    4, 5, 6, 10, 14, 15, 16, 20, 23, 27, 50, 51, 52, 60, 64, 65, 68, 70, 72, 73, 75,
//                    77, 78, 79, 80, 81, 82, 89, 90, 92, 93, 95, 103, 110, 111
            };
            for (Integer i : arr)
                btree.insert(i);

            btree.remove(70);
            btree.remove(51);
            btree.remove(64);
            btree.remove(72);
        }
    }
}
