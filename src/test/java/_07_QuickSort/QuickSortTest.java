package _07_QuickSort;

import _02_GettingStarted.InsertionSort;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class QuickSortTest
{
    @Test
    void shouldSortCorrectly() {
        Random r = new Random();
        Integer[] ints = IntStream.generate(r::nextInt)
                .limit(10_000)
                .boxed()
                .toArray(Integer[]::new);

        Integer[] expected = ints.clone();
        Arrays.sort(expected);

        QuickSort.sort(ints, Integer::compareTo);

        assertArrayEquals(expected, ints);
    }
}