package _10_ElementaryDataStructures.LinkedList;

public class TwoWayLinkedListImpl<T>
{
    private class Node
    {
        private T value;
        private Node next;
        private Node prev;

        public Node(T value) {
            this.value = value;
        }

        public void insertAfter(Node node) {
            node.next = this.next;
            node.prev = this;
            next.prev = node;
            next = node;
        }

        public void insertBefore(Node node) {
            node.next = this;
            node.prev = this.prev;
            this.prev.next = node;
            this.prev = node;
        }

        public void remove() {
            this.prev.next = next;
            this.next.prev = prev;
        }
    }

    private Node sentinel;
    private int size;

    public TwoWayLinkedListImpl() {
        sentinel = new Node(null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    private Node getNode(int idx) {
        if (idx < 0)
            throw new IndexOutOfBoundsException();

        Node temp = sentinel.next;
        int i = 0;
        while (temp != sentinel && i < idx) {
            i++;
            temp = temp.next;
        }

        if (temp == sentinel)
            throw new IndexOutOfBoundsException();
        return temp;
    }

    private Node getNode(T t) {
        Node temp = sentinel.next;
        while (temp != sentinel && !temp.value.equals(t))
            temp = temp.next;

        if (temp == sentinel)
            return null;
        return temp;
    }

    public void insert(T t) {
        Node node = new Node(t);
        sentinel.insertBefore(node);
        size++;
    }

    public void insert(T t, int idx) {
        Node node = new Node(t);
        if (idx == 0)
            sentinel.insertAfter(node);
        else {
            Node temp = getNode(idx - 1);
            temp.insertAfter(node);
        }
        size++;
    }

    public boolean remove(T t) {
        Node node = getNode(t);
        if (node == null)
            return false;
        node.remove();
        size--;
        return true;
    }

    public T remove(int idx) {
        Node node = getNode(idx);
        node.remove();
        size--;
        return node.value;
    }

    public T get(int idx) {
        return getNode(idx).value;
    }

    public boolean contains(T t) {
        return indexOf(t) != -1;
    }

    public int indexOf(T t) {
        Node temp = sentinel.next;
        int i = 0;
        while (temp != sentinel && !temp.value.equals(t)) {
            i++;
            temp = temp.next;
        }

        if (temp == sentinel)
            return -1;
        return i;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private static class Example
    {
        public static void main(String[] args) {
            TwoWayLinkedListImpl<Integer> list = new TwoWayLinkedListImpl<>();
            list.insert(1);
            list.insert(2);
            list.insert(3);
            list.remove(0);
            list.remove(Integer.valueOf(3));
            System.out.println(list.get(0));
            list.remove(0);
            System.out.println("Size: " + list.size());
            System.out.println(list.isEmpty());
        }
    }
}
