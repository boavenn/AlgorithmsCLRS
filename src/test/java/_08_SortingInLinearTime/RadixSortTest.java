package _08_SortingInLinearTime;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class RadixSortTest
{
    @Test
    void shouldSortCorrectly() {
        Random r = new Random();
        int[] ints = IntStream.generate(() -> r.nextInt(10_000))
                .limit(10_000)
                .toArray();

        int[] expected = ints.clone();
        Arrays.sort(expected);

        RadixSort.sort(ints, 4);

        assertArrayEquals(expected, ints);
    }
}