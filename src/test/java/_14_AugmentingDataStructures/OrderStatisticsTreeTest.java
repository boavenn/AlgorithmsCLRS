package _14_AugmentingDataStructures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderStatisticsTreeTest
{
    private OrderStatisticsTree<Integer> ost;

    @BeforeEach
    void setUp() {
        ost = new OrderStatisticsTree<>(Integer::compareTo);
        for (int i : new int[]{4, 2, 7, 1, 3, 6, 9}) {
            ost.insert(i);
        }
    }

    @Test
    void selectReturnCorrectElement() {
        assertEquals(4, ost.select(4));
        assertEquals(9, ost.select(7));
        assertNull(ost.select(8));
        assertNull(ost.select(0));
    }

    @Test
    void rankReturnCorrectPosition() {
        assertEquals(4, ost.rank(4));
        assertEquals(7, ost.rank(9));
        assertEquals(1, ost.rank(1));
        assertEquals(-1, ost.rank(29));
    }
}