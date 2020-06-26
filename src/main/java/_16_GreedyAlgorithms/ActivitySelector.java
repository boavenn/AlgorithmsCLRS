package _16_GreedyAlgorithms;

import java.util.LinkedList;
import java.util.List;

public final class ActivitySelector
{
    public static List<Integer> greedyActivitySelector(int[] s, int[] f) {
        int n = s.length;
        List<Integer> result = new LinkedList<>();
        result.add(0);
        int k = 1;
        for (int m = 1; m < n; m++) {
            if (s[m] >= f[k]) {
                result.add(m);
                k = m;
            }
        }
        return result;
    }
}
