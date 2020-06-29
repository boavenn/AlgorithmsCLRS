package _27_MultithreadedAlgorithms;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FibonacciTest
{
    @Test
    void returnCorrectOutput() {
        List<Integer> expected = List.of(0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610,
                987, 1597, 2584, 4181, 6765, 10946, 17711, 28657, 46368);

        List<Integer> result = new LinkedList<>();
        for (int i = 0; i < 25; i++) {
            result.add(Fibonacci.parallel(i));
        }

        assertIterableEquals(expected, result);
    }
}