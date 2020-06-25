package _11_HashTables;

import java.util.ArrayList;

public class HashTable<K, V>
{
    private static class HashNode<K, V>
    {
        private K key;
        private V value;
        private HashNode<K, V> next;

        public HashNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private final static int DEFAULT_CAPACITY = 10;
    private final static float CAPACITY_THRESHOLD = 0.75f;
    private ArrayList<HashNode<K, V>> buckets = new ArrayList<>();
    private int capacity;
    private int size = 0;

    public HashTable(int capacity) {
        this.capacity = Math.max(capacity, DEFAULT_CAPACITY);
        for (int i = 0; i < this.capacity; i++)
            buckets.add(null);
    }

    public HashTable() {
        this(DEFAULT_CAPACITY);
    }

    private int getBucketIdx(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    public void put(K key, V value) {
        int bucketIdx = getBucketIdx(key);

        // update value if the key already exists
        HashNode<K, V> t = find(key, bucketIdx);
        if (t != null) {
            t.value = value;
            return;
        }

        HashNode<K, V> node = new HashNode<>(key, value);
        node.next = buckets.get(bucketIdx);
        buckets.set(bucketIdx, node);
        size++;

        if (1f * size / capacity > CAPACITY_THRESHOLD)
            resize();
    }

    public V remove(K key) {
        int bucketIdx = getBucketIdx(key);
        HashNode<K, V> head = buckets.get(bucketIdx);

        HashNode<K, V> prev = null;
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
        HashNode<K, V> n = find(key, getBucketIdx(key));
        return n == null ? null : n.value;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(K key) {
        return find(key, getBucketIdx(key)) != null;
    }

    private HashNode<K, V> find(K key, int idx) {
        HashNode<K, V> n = buckets.get(idx);
        while (n != null) {
            if (n.key.equals(key))
                return n;
            n = n.next;
        }
        return null;
    }

    private void resize() {
        ArrayList<HashNode<K, V>> temp = buckets;
        buckets = new ArrayList<>();
        capacity *= 2;
        size = 0;

        for (int i = 0; i < capacity; i++)
            buckets.add(null);

        for (HashNode<K, V> node : temp) {
            while (node != null) {
                put(node.key, node.value);
                node = node.next;
            }
        }
    }

    private static class Example
    {
        public static void main(String[] args) {
            HashTable<String, Integer> hashTable = new HashTable<>(3);
            hashTable.put("one", 1);
            hashTable.put("two", 2);
            hashTable.put("three", -20);
            hashTable.put("three", 3);
            hashTable.put("four", 4);
            hashTable.put("five", 5);
            System.out.println("Contains one: " + hashTable.contains("one"));
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
