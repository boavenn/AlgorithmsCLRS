package _08_SortingInLinearTime;

public final class RadixSort
{
    // d - maximum digits of a number
    public static void sort(int[] arr, int d) {
        for (int i = 0, exp = 1; i < d; i++, exp *= 10)
            countingSortRadix(arr, exp);
    }

    private static void countingSortRadix(int[] arr, int exp) {
        int[] util = new int[10];
        int[] res = new int[arr.length];

        for (int value : arr)
            util[(value / exp) % 10]++;
        for (int i = 1; i < 10; i++)
            util[i] += util[i - 1];
        for (int i = arr.length - 1; i >= 0; i--) {
            res[util[(arr[i] / exp) % 10] - 1] = arr[i];
            util[(arr[i] / exp) % 10]--;
        }
        System.arraycopy(res, 0, arr, 0, arr.length);
    }
}
