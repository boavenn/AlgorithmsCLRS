package _10_ElementaryDataStructures.LinkedList;

public class OneWayLinkedListImpl<T>
{
    private class Node
    {
        private T value;
        private Node next;

        public Node(T value) {
            this.value = value;
        }
    }

    private Node head;
    private int size = 0;

    public void insert(T t) {
        Node node = new Node(t);
        if (head == null) {
            head = node;
            size++;
            return;
        }
        Node temp = head;
        while (temp.next != null)
            temp = temp.next;
        temp.next = node;
        size++;
    }

    public void insert(T t, int idx) {
        if (idx < 0)
            throw new IndexOutOfBoundsException();
        Node node = new Node(t);
        if (idx == 0) {
            node.next = head;
            head = node;
        } else {
            Node temp = getNode(idx - 1);
            node.next = temp.next;
            temp.next = node;
        }
        size++;
    }

    public boolean remove(T t) {
        if (head == null)
            return false;

        if (head.value.equals(t)) {
            head = head.next;
            size--;
            return true;
        }

        Node temp = head;
        while (temp.next != null && !temp.next.value.equals(t))
            temp = temp.next;

        if (temp.next == null)
            return false;
        temp.next = temp.next.next;
        size--;
        return true;
    }

    public T remove(int idx) {
        if (idx < 0 || head == null)
            throw new IndexOutOfBoundsException();
        if (idx == 0) {
            T v = head.value;
            head = head.next;
            size--;
            return v;
        }

        Node temp = getNode(idx - 1);
        if (temp.next == null)
            throw new IndexOutOfBoundsException();
        T v = temp.next.value;
        temp.next = temp.next.next;
        size--;
        return v;
    }

    public T get(int idx) {
        return getNode(idx).value;
    }

    public boolean contains(T t) {
        return indexOf(t) != -1;
    }

    public int indexOf(T t) {
        int idx = 0;
        Node temp = head;
        while (temp != null) {
            if (temp.value.equals(t))
                return idx;
            temp = temp.next;
            idx++;
        }
        return -1;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return head == null;
    }

    private Node getNode(int idx) {
        if (idx < 0)
            throw new IndexOutOfBoundsException();
        Node temp = head;
        while (idx > 0 && temp != null) {
            temp = temp.next;
            idx--;
        }

        if (temp == null)
            throw new IndexOutOfBoundsException();
        return temp;
    }

    private static class Example
    {
        public static void main(String[] args) {
            OneWayLinkedListImpl<Integer> list = new OneWayLinkedListImpl<>();
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
