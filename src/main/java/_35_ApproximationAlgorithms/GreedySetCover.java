package _35_ApproximationAlgorithms;

import java.util.*;

public final class GreedySetCover
{
    public static <T> List<Set<T>> process(List<T> universe, List<Set<T>> sets) {
        List<T> universeCopy = new LinkedList<>(universe);
        List<Set<T>> cover = new LinkedList<>();
        while (!universeCopy.isEmpty()) {
            Set<T> maxSet = getMaxSet(universeCopy, sets);
            for (T el : maxSet)
                universeCopy.remove(el);
            cover.add(maxSet);
        }
        return cover;
    }

    private static <T> Set<T> getMaxSet(List<T> universe, List<Set<T>> sets) {
        Set<T> temp = null;
        int max = 0;
        for (Set<T> s : sets) {
            int n = numOfCommonElements(universe, s);
            if (n > max) {
                temp = s;
                max = n;
            }
        }
        return temp;
    }

    private static <T> int numOfCommonElements(List<T> universe, Set<T> set) {
        int n = 0;
        for (T el : set) {
            if (universe.contains(el))
                n++;
        }
        return n;
    }
}
