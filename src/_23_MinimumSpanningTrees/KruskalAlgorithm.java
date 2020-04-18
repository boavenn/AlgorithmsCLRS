package _23_MinimumSpanningTrees;

import _21_DataStructuresForDisjointSets.WeightedUnionFind;
import _22_ElementaryGraphAlgorithms.Graph;

import java.util.*;

import static _22_ElementaryGraphAlgorithms.Graph.Vertex;
import static _22_ElementaryGraphAlgorithms.Graph.Edge;
import static _21_DataStructuresForDisjointSets.WeightedUnionFind.Node;

public abstract class KruskalAlgorithm<T>
{
    public static <T> List<Edge<T>> calc(Graph<T> graph) {
        List<Vertex<T>> vertices = graph.getVertices();
        // result minimum path
        List<Edge<T>> result = new LinkedList<>();
        // disjoint sets made from graph vertices
        List<Node<T>> sets = new ArrayList<>(vertices.size());
        // edges sorted by weight
        List<Edge<T>> edges = graph.getEdges();
        edges.sort(Comparator.comparingInt(Edge::getWeight));

        for (Vertex<T> v : vertices)
            sets.add(WeightedUnionFind.makeSet(v.getKey()));

        for (Edge<T> e : edges) {
            Node<T> n1 = sets.get(sets.indexOf(new Node<>(e.getSrc().getKey())));
            Node<T> n2 = sets.get(sets.indexOf(new Node<>(e.getDest().getKey())));
            if (WeightedUnionFind.findSet(n1) != WeightedUnionFind.findSet(n2)) {
                result.add(e);
                WeightedUnionFind.union(n1, n2);
            }
        }

        return result;
    }

    private static class Example
    {
        public static void main(String[] args) {
            Graph<Character> graph = new Graph<>(false);

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
            System.out.println(KruskalAlgorithm.calc(graph));
        }
    }
}
