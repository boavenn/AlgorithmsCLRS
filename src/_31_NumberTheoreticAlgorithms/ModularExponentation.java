package _31_NumberTheoreticAlgorithms;

public final class ModularExponentation
{
    public static int modExp(int a, int b, int n) {
        int d = 1;
        int log2b = (int) (Math.log(b) / Math.log(2));
        for (int i = log2b; i >= 0; i--) {
            d = (d * d) % n;
            int x = 1 << i; // 2^i
            if ((b & x) == x) {
                d = (d * a) % n;
            }
        }
        return d;
    }

    private static class Example
    {
        public static void main(String[] args) {
            System.out.println(ModularExponentation.modExp(7, 560, 561));
        }
    }
}
