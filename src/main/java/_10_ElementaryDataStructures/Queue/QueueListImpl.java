package _10_ElementaryDataStructures.Queue;

public class QueueListImpl<T>
{
    private static class Node<T>
    {
        private T value;
        private Node<T> next;

        public Node(T value) {
            this.value = value;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public void enqueue(T t) {
        Node<T> node = new Node<>(t);
        if (head == null)
            head = node;
        else
            tail.next = node;
        tail = node;
        size++;
    }

    public T dequeue() {
        if (isEmpty())
            throw new IllegalStateException("Queue is empty");
        T v = head.value;
        if (head == tail)
            tail = null;
        head = head.next;
        size--;
        return v;
    }

    public T peek() {
        if (isEmpty())
            throw new IllegalStateException("Queue is empty");
        return head.value;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
