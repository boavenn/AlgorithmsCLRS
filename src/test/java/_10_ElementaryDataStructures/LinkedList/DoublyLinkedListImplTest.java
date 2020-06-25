package _10_ElementaryDataStructures.LinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DoublyLinkedListImplTest
{
    private DoublyLinkedListImpl<Integer> list;

    @BeforeEach
    void setUp() {
        list = new DoublyLinkedListImpl<>();
        for (int i : new int[]{1, 2, 3}) {
            list.insert(i);
        }
    }

    @Test
    void shouldInsertItemsCorrectly() {
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));

        list.insert(4, 1);
        assertEquals(4, list.get(1));
    }

    @Test
    void shouldRemoveItemsCorrectly() {
        list.remove(Integer.valueOf(1));
        assertEquals(2, list.size());
        list.remove(1);
        assertEquals(2, list.get(0));
    }

    @Test
    void indexOfShouldReturnCorrectIndex() {
        assertEquals(2, list.indexOf(3));
    }

    @Test
    void containsShouldReturnCorrectFlag() {
        assertTrue(list.contains(2));
        assertFalse(list.contains(4));
    }

    @Test
    void sizeShouldReturnCorrectSize() {
        assertEquals(3, list.size());
        list.remove(0);
        assertEquals(2, list.size());
    }
}