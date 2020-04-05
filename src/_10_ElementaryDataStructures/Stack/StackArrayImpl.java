package _10_ElementaryDataStructures.Stack;

public class StackArrayImpl<T>
{
    private final static int DEFAULT_CAPACITY = 10;
    private T[] arr;
    private int topIndex = -1;

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
        return arr[topIndex--];
    }

    public void push(T t) {
        if (isFull())
            throw new IllegalStateException("Stack is full");
        arr[++topIndex] = t;
    }

    public T top() {
        if (isEmpty())
            throw new IllegalStateException("Stack is empty");
        return arr[topIndex];
    }

    public int size() {
        return topIndex + 1;
    }

    public boolean isEmpty() {
        return topIndex == -1;
    }

    public boolean isFull() {
        return topIndex + 1 == arr.length;
    }

    private static class Example
    {
        public static void main(String[] args) {
            StackArrayImpl<Integer> stack = new StackArrayImpl<>();
            stack.push(1);
            stack.push(2);
            stack.push(3);
            System.out.println(stack.top());
            System.out.println(stack.pop());
            System.out.println(stack.pop());
            System.out.println(stack.pop());
            System.out.println("Size: " + stack.size());
        }
    }
}
