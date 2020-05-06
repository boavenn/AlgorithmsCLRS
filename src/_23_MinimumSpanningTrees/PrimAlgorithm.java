package _23_MinimumSpanningTrees;

import _22_ElementaryGraphAlgorithms.Graph;

import java.util.*;

import static _22_ElementaryGraphAlgorithms.Graph.Vertex;
import static _22_ElementaryGraphAlgorithms.Graph.Edge;

public abstract class PrimAlgorithm
{
    public static <T> List<Edge<T>> calc(Graph<T> graph, Vertex<T> root) {
        List<Vertex<T>> vertices = graph.getVertices();
        // result minimum path
        List<Edge<T>> result = new LinkedList<>();
        // vertices already included in MSP
        Map<Vertex<T>, Boolean> visited = new HashMap<>();
        // priority queue to sort edges by weight
        PriorityQueue<Edge<T>> queue = new PriorityQueue<>(Comparator.comparingInt(Edge::getWeight));

        visited.put(root, true);
        queue.addAll(graph.getAdjacentEdgesOf(root));

        while (visited.size() != vertices.size()) {
            Edge<T> min = queue.poll();
            // if edge destination is already in MSP we omit it
            while (min != null && visited.containsKey(min.getDest()))
                min = queue.poll();

            if(min == null)
                throw new IllegalArgumentException("Cannot create MSP from a given graph");

            result.add(min);
            visited.put(min.getDest(), true);
            queue.addAll(graph.getAdjacentEdgesOf(min.getDest()));
        }

        return result;
    }

    private static class Example
    {
        public static void main(String[] args) {
            Graph<Character> graph = new Graph<>(false);

            Character[] V = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i'};
            Character[][] E = {
                    {'a', 'b'}, {'a', 'h'},
                    {'b', 'h'}, {'h', 'i'}, {'h', 'g'},
                    {'g', 'i'}, {'i', 'c'}, {'c', 'b'}, {'c', 'd'}, {'c', 'f'},
                    {'f', 'g'}, {'f', 'd'}, {'d', 'e'}, {'e', 'f'},
            };
            Integer[] W = {4, 8, 11, 7, 1, 6, 2, 8, 7, 4, 2, 14, 9, 10};

            for (Character c : V)
                graph.addVertex(c);
            for (int i = 0; i < E.length; i++)
                graph.addEdge(E[i][0], E[i][1], W[i]);

            System.out.println(graph);
            System.out.println(PrimAlgorithm.calc(graph, new Vertex<>('a')));
        }
    }
}
