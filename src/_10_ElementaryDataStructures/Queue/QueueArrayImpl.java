package _10_ElementaryDataStructures.Queue;

public class QueueArrayImpl<T>
{
    private final static int DEFAULT_CAPACITY = 10;
    private T[] arr;
    private int begin = 0;
    private int end = 0;

    @SuppressWarnings("unchecked")
    public QueueArrayImpl(int capacity) {
        arr = (T[]) (new Object[capacity + 1]);
    }

    public QueueArrayImpl() {
        this(DEFAULT_CAPACITY);
    }

    public void enqueue(T t) {
        if (isFull())
            throw new IllegalStateException("Queue is full");
        arr[end++] = t;
        end %= arr.length;
    }

    public T dequeue() {
        if (isEmpty())
            throw new IllegalStateException("Queue is empty");
        T v = arr[begin++];
        begin %= arr.length;
        return v;
    }

    public T peek() {
        return arr[begin];
    }

    public int size() {
        return (end + arr.length - begin) % arr.length;
    }

    public boolean isEmpty() {
        return begin == end;
    }

    public boolean isFull() {
        return begin == (end + 1) % arr.length;
    }

    private static class Example
    {
        public static void main(String[] args) {
            QueueArrayImpl<Integer> queue = new QueueArrayImpl<>();
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
