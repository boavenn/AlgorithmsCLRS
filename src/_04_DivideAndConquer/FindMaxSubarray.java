package _04_DivideAndConquer;

public abstract class FindMaxSubarray
{
    public static int find(int[] arr) {
        return findMaxSubarray(arr, 0, arr.length - 1);
    }

    // bonus - Kadane's algorithm
    public static int findIterative(int[] arr) {
        int sum = 0;
        int max_sum = 0;

        for (int value : arr) {
            sum += value;
            if (sum < 0)
                sum = 0;
            if (sum > max_sum)
                max_sum = sum;
        }

        return max_sum;
    }

    private static int findMaxCrossingSubarray(int[] arr, int begin, int mid, int end) {
        int leftSum = Integer.MIN_VALUE;
        int sum = 0;
        for (int i = mid; i >= begin; i--) {
            sum += arr[i];
            if (sum > leftSum)
                leftSum = sum;
        }

        int rightSum = Integer.MIN_VALUE;
        sum = 0;
        for (int i = mid + 1; i <= end; i++) {
            sum += arr[i];
            if (sum > rightSum)
                rightSum = sum;
        }

        return leftSum + rightSum;
    }

    private static int findMaxSubarray(int[] arr, int begin, int end) {
        if(begin == end)
            return arr[begin];

        int mid = (begin + end) / 2;
        int leftSum = findMaxSubarray(arr, begin, mid);
        int crossSum = findMaxCrossingSubarray(arr, begin, mid, end);
        int rightSum = findMaxSubarray(arr, mid + 1, end);
        return Math.max(Math.max(leftSum, crossSum), rightSum);
    }

    public static void main(String[] args) {
        int[] arr = {13, -3, -25, 20, -3, -16, -23, 18, 20, -7, 12, -5, -22, 15, -4, 7};
        System.out.println(find(arr));
        System.out.println(findIterative(arr));
    }
}
