package _10_ElementaryDataStructures.Queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueueListImplTest
{
    private QueueListImpl<Integer> queue;

    @BeforeEach
    void setUp() {
        queue = new QueueListImpl<>();
        for (int i : new int[]{1, 2, 3}) {
            queue.enqueue(i);
        }
    }

    @Test
    void elementsShouldBeCorrectlyQueued() {
        assertEquals(1, queue.peek());
        assertEquals(1, queue.dequeue());
        assertEquals(2, queue.dequeue());
        assertEquals(3, queue.peek());
        assertEquals(3, queue.dequeue());
    }

    @Test
    void sizeShouldReturnCorrectSize() {
        assertFalse(queue.isEmpty());
        assertEquals(3, queue.size());
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        assertEquals(0, queue.size());
        assertTrue(queue.isEmpty());
    }
}