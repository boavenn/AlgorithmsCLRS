package _02_GettingStarted;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class BubbleSortTest
{
    @Test
    void shouldSortCorrectly() {
        Random r = new Random();
        Integer[] ints = IntStream.generate(r::nextInt)
                .limit(1_000)
                .boxed()
                .toArray(Integer[]::new);

        Integer[] expected = ints.clone();
        Arrays.sort(expected);

        BubbleSort.sort(ints, Integer::compareTo);

        assertArrayEquals(expected, ints);
    }
}