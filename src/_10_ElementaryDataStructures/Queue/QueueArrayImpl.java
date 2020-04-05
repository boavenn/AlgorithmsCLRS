package _10_ElementaryDataStructures.Queue;

public class QueueArrayImpl<T>
{
    private final static int DEFAULT_CAPACITY = 10;
    private T[] arr;
    private int beginIndex = 0;
    private int endIndex = 0;

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
        arr[endIndex++] = t;
        endIndex %= arr.length;
    }

    public T dequeue() {
        if (isEmpty())
            throw new IllegalStateException("Queue is empty");
        T v = arr[beginIndex++];
        beginIndex %= arr.length;
        return v;
    }

    public T peek() {
        return arr[beginIndex];
    }

    public int size() {
        return (endIndex + arr.length - beginIndex) % arr.length;
    }

    public boolean isEmpty() {
        return beginIndex == endIndex;
    }

    public boolean isFull() {
        return beginIndex == (endIndex + 1) % arr.length;
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
