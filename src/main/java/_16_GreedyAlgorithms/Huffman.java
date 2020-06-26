package _16_GreedyAlgorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public final class Huffman
{
    private static class Node implements Comparable<Node>
    {
        private int freq;
        private char c;
        private Node left;
        private Node right;
        private Node parent;

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

    private static String findCode(Node character) {
        StringBuilder str = new StringBuilder();
        Node n = character;
        while (n.parent != null) {
            if (n == n.parent.left) {
                str.append('0');
            } else {
                str.append('1');
            }
            n = n.parent;
        }
        return str.reverse().toString();
    }

    public static Map<Character, String> calcHuffmanCodes(int[] frequency, char[] chars) {
        int n = chars.length;
        List<Node> nodes = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            nodes.add(new Node(frequency[i], chars[i]));
        }
        PriorityQueue<Node> queue = new PriorityQueue<>(nodes);

        for (int i = 0; i < n - 1; i++) {
            Node internalNode = new Node();
            Node a = queue.poll();
            a.parent = internalNode;
            Node b = queue.poll();
            b.parent = internalNode;
            internalNode.freq = a.freq + b.freq;
            internalNode.left = a;
            internalNode.right = b;
            queue.add(internalNode);
        }

        return nodes.stream().collect(Collectors.toMap(node -> node.c, Huffman::findCode));
    }
}
