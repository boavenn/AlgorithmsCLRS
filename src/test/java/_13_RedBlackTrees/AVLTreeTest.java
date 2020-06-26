package _13_RedBlackTrees;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AVLTreeTest
{
    private AVLTree<Integer> avl;

    @BeforeEach
    void setUp() {
        avl = new AVLTree<>(Integer::compareTo);
    }

    @Test
    void rebalancesCorrectlySmallDataset() {
        final int dataSize = 1_000;
        insert(dataSize);
        assertTrue(avl.height() < maxHeight(dataSize));
    }

    @Test
    void rebalancesCorrectlyLargeDataset() {
        final int dataSize = 1_000_000;
        insert(dataSize);
        assertTrue(avl.height() < maxHeight(dataSize));
    }

    @Test
    void returnCorrectSize() {
        final int dataSize = 1_000;
        insert(dataSize);
        assertEquals(dataSize, avl.size());

        final int removeSize = 256;
        delete(removeSize);
        assertEquals(dataSize - removeSize, avl.size());

        final int addSize = 100;
        insert(addSize, dataSize - removeSize);
        assertEquals(dataSize - removeSize, avl.size());
    }

    @Test
    void returnCorrectMaximum() {
        final int dataSize = 1_000;
        insert(dataSize);
        assertEquals(dataSize - 1, avl.maximum());
        avl.remove(dataSize - 1);
        assertEquals(dataSize - 2, avl.maximum());
    }

    @Test
    void returnCorrectMinimum() {
        final int dataSize = 1_000;
        insert(dataSize);
        assertEquals(0, avl.minimum());
        avl.remove(0);
        assertEquals(1, avl.minimum());
    }

    @Test
    void returnCorrectSuccessor() {
        final int dataSize = 1_000;
        insert(dataSize);
        int v = dataSize / 2;
        assertEquals(v + 1, avl.successor(v));
        assertNull(avl.successor(dataSize - 1));
    }

    @Test
    void returnCorrectPredecessor() {
        final int dataSize = 1_000;
        insert(dataSize);
        int v = dataSize / 2;
        assertEquals(v - 1, avl.predecessor(v));
        assertNull(avl.predecessor(0));
    }

    private int maxHeight(int n) {
        return (int) (1.5 * Math.log(n) / Math.log(2));
    }

    private void insert(int n, int offset) {
        for (int i = 0; i < n; i++)
            avl.insert(i + offset);
    }

    private void insert(int n) {
        insert(n, 0);
    }

    private void delete(int n, int offset) {
        for (int i = 0; i < n; i++)
            avl.remove(i + offset);
    }

    private void delete(int n) {
        delete(n, 0);
    }
}