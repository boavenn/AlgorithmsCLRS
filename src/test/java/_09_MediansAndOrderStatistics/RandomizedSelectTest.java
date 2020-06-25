package _09_MediansAndOrderStatistics;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class RandomizedSelectTest
{
    @Test
    void randomizedSelect() {
        List<Integer> ints = IntStream.range(0, 100)
                .boxed()
                .collect(Collectors.toList());
        Collections.shuffle(ints);

        assertEquals(32, RandomizedSelect.randomizedSelect(ints.toArray(new Integer[0]), Integer::compareTo, 33));
    }
}