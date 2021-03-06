package _10_ElementaryDataStructures.Stack;

public class StackListImpl<T>
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
    private int size;

    public T pop() {
        if (isEmpty())
            throw new IllegalStateException("Stack is empty");
        T v = head.value;
        head = head.next;
        size--;
        return v;
    }

    public void push(T t) {
        Node<T> node = new Node<>(t);
        node.next = head;
        head = node;
        size++;
    }

    public T top() {
        if (isEmpty())
            throw new IllegalStateException("Stack is empty");
        return head.value;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
