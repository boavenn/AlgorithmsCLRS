package _19_FibonacciHeap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static _19_FibonacciHeap.FibonacciHeap.FibNode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class FibonacciHeapTest
{
    private FibonacciHeap<Integer> heap;

    @BeforeEach
    void setUp() {
        heap = new FibonacciHeap<>(Integer::compareTo);
    }

    @Test
    void extractsElementsInCorrectOrder() {
        List<Integer> list = new ArrayList<>(List.of(6, 1, 7, 2, 0, 6, 1, 2, 6, 4, 12, 18, 35, 11, 4, 0, 1, 23, 56));
        list.forEach(heap::insert);
        list.sort(Integer::compareTo);

        List<Integer> res = new ArrayList<>();
        while (!heap.isEmpty()) {
            res.add(heap.extractMin());
        }

        assertIterableEquals(list, res);
    }

    @Test
    void extractsRandomElementsInCorrectOrder() {
        Random r = new Random();
        List<Integer> list = IntStream.generate(() -> r.nextInt(10_000))
                .limit(10_000)
                .boxed()
                .collect(Collectors.toList());
        list.forEach(heap::insert);
        list.sort(Integer::compareTo);
        List<Integer> res = new ArrayList<>();
        while (!heap.isEmpty()) {
            res.add(heap.extractMin());
        }
        assertIterableEquals(list, res);
    }

    @Test
    void unionTwoHeapsCorrectly() {
        final int size = 1000;
        Random r = new Random();
        List<Integer> l1 = IntStream.generate(() -> r.nextInt(size))
                .limit(size)
                .boxed()
                .collect(Collectors.toList());
        l1.forEach(heap::insert);

        FibonacciHeap<Integer> otherHeap = new FibonacciHeap<>(Integer::compareTo);

        List<Integer> l2 = IntStream.generate(() -> r.nextInt(size) + size)
                .limit(size)
                .boxed()
                .collect(Collectors.toList());
        l2.forEach(otherHeap::insert);

        FibonacciHeap<Integer> newHeap = heap.union(otherHeap);
        assertEquals(size * 2, newHeap.size());

        l1.addAll(l2);
        l1.sort(Integer::compareTo);

        List<Integer> res = new ArrayList<>();
        while (!newHeap.isEmpty()) {
            res.add(newHeap.extractMin());
        }
        assertIterableEquals(l1, res);
    }

    @Test
    void decreaseKeyCorrectly() {
        List<FibNode<Integer>> nodes = Stream.of(6, 1, 7, 2, 0, 6, 1, 2, 6, 4, 12, 18, 35, 11, 4, 0, 1, 23, 56)
                .map(heap::insert)
                .collect(Collectors.toList());

        heap.decreaseKey(nodes.get(10), -1); // 12
        heap.decreaseKey(nodes.get(1), -5); // 1
        heap.decreaseKey(nodes.get(0), 3); // 6
        heap.decreaseKey(nodes.get(11), 14); // 18
        heap.decreaseKey(nodes.get(2), 5); // 7

        List<Integer> res = new ArrayList<>();
        while (!heap.isEmpty()) {
            res.add(heap.extractMin());
        }
        assertIterableEquals(List.of(-5, -1, 0, 0, 1, 1, 2, 2, 3, 4, 4, 5, 6, 6, 11, 14, 23, 35, 56), res);
    }
}