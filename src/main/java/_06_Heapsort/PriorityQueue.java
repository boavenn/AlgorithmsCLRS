package _06_Heapsort;

import java.util.Comparator;
import java.util.Random;

public class PriorityQueue<T>
{
    private final static int DEFAULT_CAPACITY = 10;
    private T[] arr;
    private Comparator<T> comp;
    private int capacity;
    private int top = 0;

    @SuppressWarnings("unchecked")
    public PriorityQueue(Comparator<T> comp, int capacity) {
        this.comp = comp;
        this.capacity = Math.max(capacity, DEFAULT_CAPACITY);
        arr = (T[]) new Object[this.capacity];
    }

    public PriorityQueue(Comparator<T> comp) {
        this(comp, DEFAULT_CAPACITY);
    }

    public void enqueue(T v) {
        arr[top++] = v;
        swim(size() - 1);
        if (size() == capacity)
            resize();
    }

    public T dequeue() {
        if (isEmpty())
            return null;

        T res = arr[0];
        top--;
        if (size() > 1) {
            swap(0, size());
            sink(0);
        }
        return res;
    }

    public int size() {
        return top;
    }

    public boolean isEmpty() {
        return top == 0;
    }

    public boolean contains(T v) {
        for (int i = 0; i < size(); i++) {
            if (arr[i].equals(v))
                return true;
        }
        return false;
    }

    private void swim(int idx) {
        int p = (idx - 1) / 2;
        while (idx != 0 && comp.compare(arr[idx], arr[p]) > 0) {
            swap(idx, p);
            idx = p;
            p = (idx - 1) / 2;
        }
    }

    private void sink(int idx) {
        int child = 2 * idx + 1;
        while (child < size()) {
            if (child < size() - 1 && comp.compare(arr[child], arr[child + 1]) < 0)
                child++;
            if (comp.compare(arr[idx], arr[child]) >= 0) {
                break;
            }
            swap(idx, child);
            idx = child;
            child = 2 * idx + 1;
        }
    }

    private void swap(int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        capacity *= 2;
        T[] newArr = (T[]) new Object[capacity];
        System.arraycopy(arr, 0, newArr, 0, size());
        arr = newArr;
    }

    private static class Example
    {
        public static void main(String[] args) {
            Integer[] arr = new Integer[50];
            Random r = new Random();
            for(int i = 0; i < arr.length; i++)
                arr[i] = r.nextInt(100);

            PriorityQueue<Integer> q = new PriorityQueue<>(Integer::compareTo);
            for (Integer i : arr)
                q.enqueue(i);
            while(!q.isEmpty()) {
                System.out.print(q.dequeue() + " ");
            }
        }
    }
}
