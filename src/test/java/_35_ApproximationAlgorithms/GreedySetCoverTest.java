package _35_ApproximationAlgorithms;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class GreedySetCoverTest
{
    @Test
    void returnCorrectOutput() {
        List<Integer> universe = new LinkedList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
        List<Set<Integer>> sets = new LinkedList<>();
        sets.add(new LinkedHashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6)));
        sets.add(new LinkedHashSet<>(Arrays.asList(5, 6, 8, 9)));
        sets.add(new LinkedHashSet<>(Arrays.asList(1, 4, 7, 10)));
        sets.add(new LinkedHashSet<>(Arrays.asList(2, 5, 7, 8, 11)));
        sets.add(new LinkedHashSet<>(Arrays.asList(3, 6, 9, 12)));
        sets.add(new LinkedHashSet<>(Arrays.asList(10, 11)));

        Set<Integer> expSet1 = Set.of(1, 2, 3, 4, 5, 6);
        Set<Integer> expSet2 = Set.of(2, 5, 7, 8, 11);
        Set<Integer> expSet3 = Set.of(3, 6, 9, 12);
        Set<Integer> expSet4 = Set.of(1, 4, 7, 10);

        assertIterableEquals(List.of(expSet1, expSet2, expSet3, expSet4), GreedySetCover.process(universe, sets));
    }
}