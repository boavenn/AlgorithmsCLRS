package _11_HashTables;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashTableTest
{
    private HashTable<String, Integer> map;

    @BeforeEach
    void setUp() {
        map = new HashTable<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);
    }

    @Test
    void storeElementsCorrectly() {
        assertEquals(1, map.get("one"));
        assertEquals(2, map.get("two"));
        assertEquals(3, map.get("three"));

        assertTrue(map.contains("one"));
        assertFalse(map.contains("qwe"));
    }

    @Test
    void sizeReturnCorrectSize() {
        assertEquals(3, map.size());
        map.remove("one");
        map.remove("two");
        assertEquals(1, map.size());
    }
}