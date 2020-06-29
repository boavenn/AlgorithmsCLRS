package _27_MultithreadedAlgorithms;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class MergeSortTest
{
    @Test
    void sortsElementsCorrectly() {
        Random r = new Random();
        Integer[] ints = IntStream.generate(() -> r.nextInt(10))
                .limit(200)
                .boxed()
                .toArray(Integer[]::new);

        Integer[] expected = ints.clone();
        Arrays.sort(expected);

        MergeSort.sort(ints, Integer::compareTo);

        assertArrayEquals(expected, ints);
    }
}