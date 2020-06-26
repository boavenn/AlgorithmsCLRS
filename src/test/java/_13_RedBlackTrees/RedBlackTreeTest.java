package _13_RedBlackTrees;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RedBlackTreeTest
{
    private RedBlackTree<Integer> rbt;

    @BeforeEach
    void setUp() {
        rbt = new RedBlackTree<>(Integer::compareTo);
    }

    @Test
    void returnCorrectSize() {
        final int dataSize = 1_000;
        insert(dataSize);
        assertEquals(dataSize, rbt.size());

        final int removeSize = 256;
        delete(removeSize);
        assertEquals(dataSize - removeSize, rbt.size());

        final int addSize = 100;
        insert(addSize, dataSize - removeSize);
        assertEquals(dataSize - removeSize, rbt.size());
    }

    @Test
    void returnCorrectMaximum() {
        final int dataSize = 1_000;
        insert(dataSize);
        assertEquals(dataSize - 1, rbt.maximum());
        rbt.remove(dataSize - 1);
        assertEquals(dataSize - 2, rbt.maximum());
    }

    @Test
    void returnCorrectMinimum() {
        final int dataSize = 1_000;
        insert(dataSize);
        assertEquals(0, rbt.minimum());
        rbt.remove(0);
        assertEquals(1, rbt.minimum());
    }

    @Test
    void returnCorrectSuccessor() {
        final int dataSize = 1_000;
        insert(dataSize);
        int v = dataSize / 2;
        assertEquals(v + 1, rbt.successor(v));
        assertNull(rbt.successor(dataSize - 1));
    }

    @Test
    void returnCorrectPredecessor() {
        final int dataSize = 1_000;
        insert(dataSize);
        int v = dataSize / 2;
        assertEquals(v - 1, rbt.predecessor(v));
        assertNull(rbt.predecessor(0));
    }

    private void insert(int n, int offset) {
        for (int i = 0; i < n; i++)
            rbt.insert(i + offset);
    }

    private void insert(int n) {
        insert(n, 0);
    }

    private void delete(int n, int offset) {
        for (int i = 0; i < n; i++)
            rbt.remove(i + offset);
    }

    private void delete(int n) {
        delete(n, 0);
    }
}