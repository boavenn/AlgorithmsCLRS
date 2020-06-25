package _10_ElementaryDataStructures.Stack;

public class StackArrayImpl<T>
{
    private final static int DEFAULT_CAPACITY = 10;
    private T[] arr;
    private int top = -1;

    @SuppressWarnings("unchecked")
    public StackArrayImpl(int capacity) {
        arr = (T[]) (new Object[capacity]);
    }

    public StackArrayImpl() {
        this(DEFAULT_CAPACITY);
    }

    public T pop() {
        if (isEmpty())
            throw new IllegalStateException("Stack is empty");
        return arr[top--];
    }

    public void push(T t) {
        if (isFull())
            throw new IllegalStateException("Stack is full");
        arr[++top] = t;
    }

    public T top() {
        if (isEmpty())
            throw new IllegalStateException("Stack is empty");
        return arr[top];
    }

    public int size() {
        return top + 1;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public boolean isFull() {
        return top + 1 == arr.length;
    }
}
