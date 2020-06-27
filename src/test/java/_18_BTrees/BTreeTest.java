package _18_BTrees;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BTreeTest
{
    private BTree<Integer> bTree;
    private List<Integer> nums = List.of(9, 25, 17, 23, 21, 11, 16, 1, 24, 26, 13, 3, 12, 15, 8,
            22, 7, 2, 18, 14, 29, 0, 28, 10, 27, 5, 4, 19, 20, 6);

    @BeforeEach
    void setUp() {
        bTree = new BTree<>(3, Integer::compareTo);
        nums.forEach(bTree::insert);
    }

    @Test
    void contains() {
        nums.forEach(n -> assertTrue(bTree.contains(n)));
        assertFalse(bTree.contains(-1));
        assertFalse(bTree.contains(30));
        assertFalse(bTree.contains(66));
    }

    @Test
    void size() {
        assertEquals(30, bTree.size());
        for (int i = 0; i < 10; i++) {
            bTree.remove(i);
        }
        assertEquals(20, bTree.size());
    }

    @Test
    void isEmpty() {
        assertFalse(bTree.isEmpty());
        for (int i = 0; i < 30; i++) {
            bTree.remove(i);
        }
        assertTrue(bTree.isEmpty());
        for (int i = 0; i < 15; i++) {
            bTree.insert(i);
        }
        assertFalse(bTree.isEmpty());
    }
}