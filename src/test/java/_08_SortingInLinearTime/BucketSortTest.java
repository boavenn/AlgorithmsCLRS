package _08_SortingInLinearTime;

import _02_GettingStarted.InsertionSort;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class BucketSortTest
{
    @Test
    void shouldSortCorrectly() {
        Random r = new Random();
        Integer[] ints = IntStream.generate(() -> r.nextInt(10_000))
                .limit(1_000)
                .boxed()
                .toArray(Integer[]::new);

        Integer[] expected = ints.clone();
        Arrays.sort(expected);

        BucketSort.sort(ints, 9_999);

        assertArrayEquals(expected, ints);
    }
}