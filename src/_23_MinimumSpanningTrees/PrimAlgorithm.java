package _23_MinimumSpanningTrees;

import _22_ElementaryGraphAlgorithms.WeightedUndirectedGraph;

import java.util.*;

import static _22_ElementaryGraphAlgorithms.UndirectedGraph.Vertex;
import static _22_ElementaryGraphAlgorithms.WeightedUndirectedGraph.WEdge;

public abstract class PrimAlgorithm
{
    public static <T> List<WEdge<T>> calc(WeightedUndirectedGraph<T> graph) {
        List<Vertex<T>> vertices = graph.getVertices();
        // result minimum path
        List<WEdge<T>> result = new LinkedList<>();
        // vertices already included in MSP
        Map<Vertex<T>, Boolean> visited = new HashMap<>();
        // priority queue to sort edges by weight
        PriorityQueue<WEdge<T>> queue = new PriorityQueue<>(Comparator.comparingInt(WEdge::getWeight));

        Vertex<T> v = vertices.get(0);
        visited.put(v, true);
        queue.addAll(graph.getEdgesOf(v));

        while(visited.size() != vertices.size()) {
            WEdge<T> min = queue.poll();
            // if edge destination is already in MSP we omit it
            while(visited.containsKey(min.getDest()))
                min = queue.poll();
            result.add(min);

            v = min.getDest();
            visited.put(v, true);
            queue.addAll(graph.getEdgesOf(v));
        }

        return result;
    }

    private static class Example
    {
        public static void main(String[] args) {
            WeightedUndirectedGraph<Character> graph = new WeightedUndirectedGraph<>();

            Character[] V = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i'};
            Character[][] E = {
                    {'a', 'b'}, {'a', 'h'}, {'b', 'h'}, {'h', 'i'}, {'h', 'g'},
                    {'g', 'i'}, {'i', 'c'}, {'c', 'b'}, {'c', 'd'}, {'c', 'f'},
                    {'f', 'g'}, {'f', 'd'}, {'d', 'e'}, {'e', 'f'},
            };
            Integer[] W = {4, 8, 11, 7, 1, 6, 2, 8, 7, 4, 2, 14, 9, 10};

            for (Character c : V)
                graph.addVertex(c);
            for (int i = 0; i < E.length; i++)
                graph.addEdge(E[i][0], E[i][1], W[i]);

            System.out.println(graph);
            System.out.println(PrimAlgorithm.calc(graph));
        }
    }
}
