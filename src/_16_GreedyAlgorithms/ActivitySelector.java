package _16_GreedyAlgorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ActivitySelector
{
    public static int[] greedyActivitySelector(int[] s, int[] f) {
        int n = s.length;
        List<Integer> A = new ArrayList<>();
        A.add(0);
        int k = 1;
        for(int m = 1; m < n; m++) {
            if(s[m] >= f[k]) {
                A.add(m);
                k = m;
            }
        }
        int[] temp = new int[A.size()];
        int j = 0;
        for(Integer i : A)
            temp[j++] = i;
        return temp;
    }

    private static class Example
    {
        public static void main(String[] args) {
            int[] s = {1, 3, 0, 5, 3, 5, 6, 8, 8, 2, 12};
            int[] f = {4, 5, 6, 7, 9, 9, 10, 11, 12, 14, 16};
            System.out.println(Arrays.toString(greedyActivitySelector(s, f)));
        }
    }
}
