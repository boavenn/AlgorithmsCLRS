package _23_MinimumSpanningTrees;

import _19_FibonacciHeap.FibonacciHeap;
import _22_ElementaryGraphAlgorithms.Graph;

import java.util.*;

import static _22_ElementaryGraphAlgorithms.Graph.Vertex;
import static _22_ElementaryGraphAlgorithms.Graph.Edge;

public final class PrimAlgorithm
{
    public static <T> List<Edge<T>> findMST(Graph<T> graph, Vertex<T> root) {
        if (graph.isDirected())
            throw new IllegalArgumentException("Graph is directed");

        int numOfVertices = graph.vertices().size();
        List<Edge<T>> result = new LinkedList<>();
        HashSet<Vertex<T>> visited = new HashSet<>();
        PriorityQueue<Edge<T>> queue = new PriorityQueue<>(Comparator.comparingInt(Edge::getWeight));

        visited.add(root);
        queue.addAll(graph.adjacentEdgesOf(root));

        while (visited.size() != numOfVertices) {
            Edge<T> min = queue.poll();
            // if edge destination vertex is already in MST we omit it
            while (min != null && visited.contains(min.getDest()))
                min = queue.poll();

            if(min == null)
                throw new IllegalArgumentException("Cannot create MST from a given graph");

            result.add(min);
            visited.add(min.getDest());
            queue.addAll(graph.adjacentEdgesOf(min.getDest()));
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
            System.out.println(PrimAlgorithm.findMST(graph, new Vertex<>('a')));
        }
    }
}
