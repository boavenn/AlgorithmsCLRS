package _06_Heapsort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PriorityQueueTest
{
    private PriorityQueue<Integer> priorityQueue;

    @BeforeEach
    void setUp() {
        priorityQueue = new PriorityQueue<>(Integer::compareTo);
    }

    @Test
    void enqueueAndDequeueShouldWorkCorrectly() {
        Integer[] ints = {17, 2, 235, 4, -25, 12};
        for (Integer i : ints) {
            priorityQueue.enqueue(i);
        }
        Arrays.sort(ints, (a, b) -> -a.compareTo(b));

        Integer[] res = Stream.generate(priorityQueue::dequeue)
                .limit(ints.length)
                .toArray(Integer[]::new);

        assertArrayEquals(ints, res);
    }

    @Test
    void size() {
        IntStream.range(0, 5).forEach(priorityQueue::enqueue);

        assertEquals(5, priorityQueue.size());
    }

    @Test
    void isEmpty() {
        IntStream.range(0, 5).forEach(priorityQueue::enqueue);

        assertFalse(priorityQueue.isEmpty());
        for (int i = 0; i < 5; i++) {
            priorityQueue.dequeue();
        }
        assertTrue(priorityQueue.isEmpty());
    }

    @Test
    void contains() {
        IntStream.range(0, 5).forEach(priorityQueue::enqueue);

        assertTrue(priorityQueue.contains(3));
        priorityQueue.dequeue();
        priorityQueue.dequeue();
        assertFalse(priorityQueue.contains(4));
    }
}