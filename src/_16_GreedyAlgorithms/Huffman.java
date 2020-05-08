package _16_GreedyAlgorithms;

import java.util.Arrays;
import java.util.PriorityQueue;

public final class Huffman
{
    private static class Node implements Comparable<Node>
    {
        private int freq;
        private char c;
        private Node left;
        private Node right;

        public Node() {}

        public Node(int freq, char c) {
            this.freq = freq;
            this.c = c;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(freq, o.freq);
        }
    }

    public static Node calc(Node[] C) {
        int n  = C.length;
        PriorityQueue<Node> Q = new PriorityQueue<>(Arrays.asList(C));
        for(int i = 0; i < n - 1; i++) {
            Node a = new Node();
            a.left = Q.poll();
            a.right = Q.poll();
            a.freq = a.left.freq + a.right.freq;
            Q.add(a);
        }
        return Q.poll();
    }

    private static class Example
    {
        public static void main(String[] args) {
            int[] freq = {45, 13, 12, 16, 9, 5};
            char[] c = {'a', 'b', 'c', 'd', 'e', 'f'};
            Node[] nodes = new Node[freq.length];
            for(int i = 0; i < freq.length; i++)
                nodes[i] = new Node(freq[i], c[i]);
            Node root = calc(nodes);
            System.out.println(root.freq);
        }
    }
}
