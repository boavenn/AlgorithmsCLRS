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

    private static class Example
    {
        public static void main(String[] args) {
            StackListImpl<Integer> stack = new StackListImpl<>();
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
