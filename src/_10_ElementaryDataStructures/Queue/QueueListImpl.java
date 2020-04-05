package _10_ElementaryDataStructures.Queue;

public class QueueListImpl<T>
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
    private Node tail;
    private int size;

    public void enqueue(T t) {
        Node node = new Node(t);
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

    private static class Example
    {
        public static void main(String[] args) {
            QueueListImpl<Integer> queue = new QueueListImpl<>();
            queue.enqueue(1);
            queue.enqueue(2);
            queue.enqueue(3);
            System.out.println(queue.peek());
            System.out.println(queue.dequeue());
            System.out.println(queue.dequeue());
            System.out.println(queue.dequeue());
            System.out.println("Size: " + queue.size());
        }
    }
}
