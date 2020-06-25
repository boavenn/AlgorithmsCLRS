package _35_ApproximationAlgorithms;

import java.util.*;

public final class GreedySetCover
{
    public static <T> List<Set<T>> greedySetCover(List<T> x, List<Set<T>> f) {
        List<T> u = new LinkedList<>(x);
        List<Set<T>> c = new LinkedList<>();
        while (!u.isEmpty()) {
            Set<T> max = getMaxSet(u, f);
            for (T el : max)
                u.remove(el);
            c.add(max);
        }
        return c;
    }

    private static <T> Set<T> getMaxSet(List<T> u, List<Set<T>> f) {
        Set<T> temp = null;
        int max = 0;
        for (Set<T> s : f) {
            int n = numOfCommonElements(u, s);
            if (n > max) {
                temp = s;
                max = n;
            }
        }
        return temp;
    }

    private static <T> int numOfCommonElements(List<T> u, Set<T> s) {
        int n = 0;
        for (T el : s) {
            if (u.contains(el))
                n++;
        }
        return n;
    }

    private static class Example
    {
        public static void main(String[] args) {
            List<Integer> universe = new LinkedList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
            List<Set<Integer>> sets = new LinkedList<>();
            sets.add(new LinkedHashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6)));
            sets.add(new LinkedHashSet<>(Arrays.asList(5, 6, 8, 9)));
            sets.add(new LinkedHashSet<>(Arrays.asList(1, 4, 7, 10)));
            sets.add(new LinkedHashSet<>(Arrays.asList(2, 5, 7, 8, 11)));
            sets.add(new LinkedHashSet<>(Arrays.asList(3, 6, 9, 12)));
            sets.add(new LinkedHashSet<>(Arrays.asList(10, 11)));
            System.out.println(greedySetCover(universe, sets));
        }
    }
}
