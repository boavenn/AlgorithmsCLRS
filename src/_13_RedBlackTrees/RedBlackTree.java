package _13_RedBlackTrees;

/*
Red-black tree properties:
1. Each node is either red or black
2. The root is black
3. All leaves (NIL or sentinel) are black
4. If a node is red, then both his children are black
5. Every path from a given node to any of its descendant NIL nodes goes
   through the same number of black nodes
 */

public class RedBlackTree<T extends Comparable<T>>
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

    private Node sentinel;
    private Node root;

    public RedBlackTree() {
        sentinel = new Node(null);
        sentinel.left = sentinel;
        sentinel.right = sentinel;
        sentinel.parent = sentinel;
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

    private void printInOrder(Node node) {
        if(node == sentinel)
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
    }

    /*
    There may be 3 cases to handle after insertion of a new Node z:
    Uncle y of the Node z is:
    1. red
    2. black and z is the left child
    3. black and z is the left child
     */
    private void insertFixup(Node z) {
        while (z.parent.color == Node.RED) {
            if (z.parent == z.parent.parent.left) {
                Node y = z.parent.parent.right;
                // case 1
                if (y.color == Node.RED) {
                    z.parent.color = Node.BLACK;
                    y.color = Node.BLACK;
                    z.parent.parent.color = Node.RED;
                    z = z.parent.parent;
                }
                else {
                    // case 2
                    if (z == z.parent.right) {
                        z = z.parent;
                        leftRotate(z);
                    }
                    // case 3
                    z.parent.color = Node.BLACK;
                    z.parent.parent.color = Node.RED;
                    rightRotate(z.parent.parent);
                }
            }
            else {
                Node y = z.parent.parent.left;
                // case 1
                if (y.color == Node.RED) {
                    z.parent.color = Node.BLACK;
                    y.color = Node.BLACK;
                    z.parent.parent.color = Node.RED;
                    z = z.parent.parent;
                }
                else {
                    // case 2
                    if (z == z.parent.left) {
                        z = z.parent;
                        rightRotate(z);
                    }
                    // case 3
                    z.parent.color = Node.BLACK;
                    z.parent.parent.color = Node.RED;
                    leftRotate(z.parent.parent);
                }
            }
        }
        root.color = Node.BLACK;
    }

    /*
    There may be 4 cases to handle after removal of a Node:
    Sibling w of the node x (x is the node which replaced z after its removal):
    1. is red
    2. is black and both children of w are black
    3. is black and left child of w is red and right is black
    4. is black and left child of w is black and right is red
     */
    private void removeFixup(Node x) {
        while (x != root && x.color == Node.BLACK) {
            if (x == x.parent.left) {
                Node w = x.parent.right;
                // case 1
                if (w.color == Node.RED) {
                    w.color = Node.BLACK;
                    x.parent.color = Node.RED;
                    leftRotate(x.parent);
                    w = x.parent.right;
                }
                // case 2
                if (w.left.color == Node.BLACK && w.right.color == Node.BLACK) {
                    w.color = Node.RED;
                    x = x.parent;
                }
                else {
                    // case 3
                    if (w.right.color == Node.BLACK) {
                        w.left.color = Node.BLACK;
                        w.color = Node.RED;
                        rightRotate(w);
                        w = x.parent.right;
                    }
                    // case 4
                    w.color = x.parent.color;
                    x.parent.color = Node.BLACK;
                    w.right.color = Node.BLACK;
                    leftRotate(x.parent);
                    x = root;
                }
            }
            else {
                Node w = x.parent.left;
                // case 1
                if (w.color == Node.RED) {
                    w.color = Node.BLACK;
                    x.parent.color = Node.RED;
                    rightRotate(x.parent);
                    w = x.parent.left;
                }
                // case 2
                if (w.right.color == Node.BLACK && w.left.color == Node.BLACK) {
                    w.color = Node.RED;
                    x = x.parent;
                }
                else {
                    // case 3
                    if (w.left.color == Node.BLACK) {
                        w.right.color = Node.BLACK;
                        w.color = Node.RED;
                        leftRotate(w);
                        w = x.parent.left;
                    }
                    // case 4
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
            RedBlackTree<Integer> rbt = new RedBlackTree<>();
            rbt.insert(11);
            rbt.insert(2);
            rbt.insert(14);
            rbt.insert(15);
            rbt.insert(1);
            rbt.insert(7);
            rbt.insert(8);
            rbt.insert(5);
            rbt.insert(4);
            System.out.println(rbt.contains(8));
            rbt.printInOrder();
            rbt.remove(4);
            rbt.remove(5);
            rbt.remove(11);
            rbt.remove(2);
            rbt.remove(1);
            rbt.remove(15);
            rbt.remove(14);
            rbt.remove(7);
            rbt.remove(8);
        }
    }
}
