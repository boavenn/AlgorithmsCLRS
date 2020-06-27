package _20_VanEmdeBoasTrees;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class vEBTreeTest
{
    private vEBTree<Integer> vebTree;

    @BeforeEach
    void setUp() {
        vebTree = new vEBTree<>(8);
        for (int i : new int[]{4, 2, 7, 1, 3, 6, 0, 5}) {
            vebTree.insert(i, i);
        }
    }

    @Test
    void remove() {
        vebTree.remove(4);
        assertFalse(vebTree.contains(4));
    }

    @Test
    void contains() {
        assertTrue(vebTree.contains(4));
        assertTrue(vebTree.contains(6));
        assertTrue(vebTree.contains(1));
        assertFalse(vebTree.contains(14));
        assertFalse(vebTree.contains(-1));
    }

    @Test
    void minimum() {
        assertEquals(0, vebTree.minimum());
        vebTree.remove(0);
        vebTree.remove(1);
        assertEquals(2, vebTree.minimum());
    }

    @Test
    void maximum() {
        assertEquals(7, vebTree.maximum());
        vebTree.remove(7);
        vebTree.remove(6);
        assertEquals(5, vebTree.maximum());
    }

    @Test
    void successor() {
        assertEquals(5, vebTree.successor(4));
        assertEquals(4, vebTree.successor(3));
    }

    @Test
    void predecessor() {
        assertEquals(2, vebTree.predecessor(3));
        assertEquals(4, vebTree.predecessor(5));
    }
}