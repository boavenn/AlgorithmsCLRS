package _12_BinarySearchTrees;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BinarySearchTreeTest
{
    private BinarySearchTree<Integer> bst;

    @BeforeEach
    void setUp() {
        bst = new BinarySearchTree<>(Integer::compareTo);
        for (int i : new int[]{4, 2, 7, 1, 3, 6, 9}) {
            bst.insert(i);
        }
    }

    @Test
    void storeElementsCorrectly() {
        assertIterableEquals(List.of(4, 2, 1, 3, 7, 6, 9), bst.preorderWalk());
        assertIterableEquals(List.of(1, 2, 3, 4, 6, 7, 9), bst.inorderWalk());
        assertIterableEquals(List.of(1, 3, 2, 6, 9, 7, 4), bst.postorderWalk());
    }

    @Test
    void remove() {
        bst.remove(4);
        assertIterableEquals(List.of(6, 2, 1, 3, 7, 9), bst.preorderWalk());
    }

    @Test
    void contains() {
        assertTrue(bst.contains(4));
        assertTrue(bst.contains(6));
        assertTrue(bst.contains(1));
        assertFalse(bst.contains(14));
        assertFalse(bst.contains(-1));
    }

    @Test
    void size() {
        assertEquals(7, bst.size());
        bst.remove(2);
        bst.remove(9);
        assertEquals(5, bst.size());
    }

    @Test
    void minimum() {
        assertEquals(1, bst.minimum());
        bst.remove(1);
        assertEquals(2, bst.minimum());
    }

    @Test
    void maximum() {
        assertEquals(9, bst.maximum());
        bst.remove(9);
        assertEquals(7, bst.maximum());
    }

    @Test
    void successor() {
        assertEquals(6, bst.successor(4));
        assertEquals(4, bst.successor(3));
    }

    @Test
    void predecessor() {
        assertEquals(2, bst.predecessor(3));
        assertEquals(4, bst.predecessor(6));
    }
}