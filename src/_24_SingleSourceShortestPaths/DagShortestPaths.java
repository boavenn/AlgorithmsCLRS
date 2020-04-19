package _24_SingleSourceShortestPaths;

import _22_ElementaryGraphAlgorithms.Graph;
import _22_ElementaryGraphAlgorithms.TopologicalSort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static _22_ElementaryGraphAlgorithms.Graph.Vertex;
import static _22_ElementaryGraphAlgorithms.Graph.Edge;

public abstract class DagShortestPaths<T>
{
    public static <T> Map<Vertex<T>, Integer> dagShortestPaths(Graph<T> graph, Vertex<T> src) {
        Map<Vertex<T>, Integer> map = Util.initializeSource(graph.getVertices(), src);
        List<Vertex<T>> vertices = TopologicalSort.topologicalSort(graph);
        // if we want to have information about "how to get to" a vertex from the src to get the shortest path
        Map<Vertex<T>, Vertex<T>> util = new HashMap<>();

        for (Vertex<T> v : vertices) {
            for (Edge<T> e : graph.getAdjacentEdgesOf(v))
                if(Util.relax(map, e))
                    util.put(e.getDest(), e.getSrc());
        }

        System.out.println(util);

        return map;
    }

    private static class Example
    {
        public static void main(String[] args) {
            Graph<Character> graph = new Graph<>(true);

            Character[] V = {'r', 's', 't', 'x', 'y', 'z'};
            Character[][] E = {
                    {'r', 's'}, {'r', 't'}, {'s', 'x'}, {'s', 't'}, {'t', 'x'},
                    {'t', 'y'}, {'t', 'z'}, {'x', 'z'}, {'x', 'y'}, {'y', 'z'}
            };
            Integer[] W = {5, 3, 6, 2, 7, 4, 2, 1, -1, -2};

            for (Character c : V)
                graph.addVertex(c);
            for (int i = 0; i < E.length; i++)
                graph.addEdge(E[i][0], E[i][1], W[i]);

            System.out.println(graph);
            System.out.println("Shortest paths from root 'r': ");
            System.out.println(DagShortestPaths.dagShortestPaths(graph, new Vertex<>('r')));
        }
    }
}
