package _11_HashTables;

import java.util.ArrayList;

public class HashTable<K, V>
{
    private class HashNode
    {
        K key;
        V value;
        HashNode next;

        public HashNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private final static int DEFAULT_CAPACITY = 10;
    private final static float CAPACITY_THRESHOLD = 0.75f;
    private ArrayList<HashNode> buckets = new ArrayList<>();
    private int capacity;
    private int size = 0;

    public HashTable(int capacity) {
        this.capacity = capacity;
        for (int i = 0; i < capacity; i++)
            buckets.add(null);
    }

    public HashTable() {
        this(DEFAULT_CAPACITY);
    }

    private int getBucketIdx(K key) {
        return Math.abs(key.hashCode() % capacity);
    }

    public void add(K key, V value) {
        int bucketIdx = getBucketIdx(key);
        HashNode head = buckets.get(bucketIdx);
        HashNode node = new HashNode(key, value);

        while (head != null) {
            if (head.key.equals(key)) {
                head.value = value;
                return;
            }
            head = head.next;
        }

        head = buckets.get(bucketIdx);
        node.next = head;
        buckets.set(bucketIdx, node);
        size++;

        if (1f * size / capacity > CAPACITY_THRESHOLD)
            resize();
    }

    public V remove(K key) {
        int bucketIdx = getBucketIdx(key);
        HashNode head = buckets.get(bucketIdx);

        HashNode prev = null;
        while (head != null) {
            if (head.key.equals(key))
                break;
            prev = head;
            head = head.next;
        }

        if (head == null)
            return null;

        if (prev != null)
            prev.next = head.next;
        else
            buckets.set(bucketIdx, head.next);
        size--;
        return head.value;
    }

    public V get(K key) {
        int bucketIdx = getBucketIdx(key);
        HashNode head = buckets.get(bucketIdx);

        while (head != null) {
            if (head.key.equals(key))
                return head.value;
            head = head.next;
        }

        return null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void resize() {
        ArrayList<HashNode> temp = buckets;
        buckets = new ArrayList<>();
        capacity *= 2;
        size = 0;

        for (int i = 0; i < capacity; i++)
            buckets.add(null);

        for (HashNode node : temp) {
            while (node != null) {
                add(node.key, node.value);
                node = node.next;
            }
        }
    }

    private static class Example
    {
        public static void main(String[] args) {
            HashTable<String, Integer> hashTable = new HashTable<>(3);
            hashTable.add("one", 1);
            hashTable.add("two", 2);
            hashTable.add("three", -20);
            hashTable.add("three", 3);
            hashTable.add("four", 4);
            hashTable.add("five", 5);
            System.out.println("Size: " + hashTable.size());
            System.out.println("Three: " + hashTable.get("three"));
            hashTable.remove("five");
            hashTable.remove("one");
            hashTable.remove("three");
            System.out.println("Two: " + hashTable.get("two"));
            System.out.println("Size: " + hashTable.size());
            hashTable.remove("two");
            hashTable.remove("four");
            System.out.println("Is empty: " + hashTable.isEmpty());
        }
    }
}
