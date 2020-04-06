package _10_ElementaryDataStructures.LinkedList;

public class LinkedListArrayImpl<T>
{
    private final static int DEFAULT_CAPACITY = 10;
    private T[] arr;
    private int topIndex = -1;

    @SuppressWarnings("unchecked")
    public LinkedListArrayImpl(int capacity) {
        arr = (T[]) (new Object[capacity]);
    }

    public LinkedListArrayImpl() {
        this(DEFAULT_CAPACITY);
    }

    public void insert(T t) {
        arr[++topIndex] = t;
        if (topIndex == arr.length + 1)
            resize();
    }

    public void insert(T t, int idx) {
        if (idx < 0 || idx > size())
            throw new IndexOutOfBoundsException();

        shiftRight(idx);
        arr[idx] = t;
        if (topIndex == arr.length + 1)
            resize();
    }

    public boolean remove(T t) {
        int idx = indexOf(t);
        if (idx == -1)
            return false;
        remove(idx);
        return true;
    }

    public T remove(int idx) {
        if (idx < 0 || idx > size())
            throw new IndexOutOfBoundsException();
        T v = arr[idx];
        shiftLeft(idx);
        return v;
    }

    public T get(int idx) {
        if (idx < 0 || idx > size())
            throw new IndexOutOfBoundsException();
        return arr[idx];
    }

    public boolean contains(T t) {
        if (t == null) {
            for (int i = 0; i <= topIndex; i++)
                if (arr[i] == null)
                    return true;
        } else {
            for (int i = 0; i <= topIndex; i++)
                if (arr[i].equals(t))
                    return true;
        }
        return false;
    }

    public int indexOf(T t) {
        if (t == null) {
            for (int i = 0; i <= topIndex; i++)
                if (arr[i] == null)
                    return i;
        } else {
            for (int i = 0; i <= topIndex; i++)
                if (arr[i].equals(t))
                    return i;
        }
        return -1;
    }

    public int size() {
        return topIndex + 1;
    }

    public boolean isEmpty() {
        return topIndex == -1;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int capacity = arr.length;
        capacity *= 2;
        T[] temp = (T[]) new Object[capacity];
        System.arraycopy(arr, 0, temp, 0, arr.length);
        this.arr = temp;
    }

    private void shiftRight(int idx) {
        for (int i = topIndex; i >= idx; i--)
            arr[i + 1] = arr[i];
        arr[idx] = null;
        topIndex++;
    }

    private void shiftLeft(int idx) {
        for (int i = idx + 1; i <= topIndex; i++)
            arr[i - 1] = arr[i];
        arr[topIndex] = null;
        topIndex--;
    }

    private static class Example
    {
        public static void main(String[] args) {
            LinkedListArrayImpl<Integer> list = new LinkedListArrayImpl<>();
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
