package _22_ElementaryGraphAlgorithms;

import _21_DataStructuresForDisjointSets.WeightedUnionFind;

import java.util.*;

import static _22_ElementaryGraphAlgorithms.Graph.Vertex;

// Kosaraju's algorithm with disjoint sets
public final class StronglyConnectedComponents<T>
{
    private WeightedUnionFind<Vertex<T>> components;
    private Deque<Vertex<T>> stack;
    private HashSet<Vertex<T>> visited;

    private void process(Graph<T> graph) {
        if (!graph.isDirected())
            throw new IllegalArgumentException("Graph is not directed");
        Graph<T> transposedGraph = Graph.transpose(graph);
        components = new WeightedUnionFind<>();
        stack = new ArrayDeque<>();
        visited = new HashSet<>();

        for (Vertex<T> v : graph.vertices())
            visit(graph, v);

        visited.clear(); // it will be reused for checking if a vertex is already in a component

        while (!stack.isEmpty()) {
            Vertex<T> vertex = stack.pop();
            assign(transposedGraph, vertex, vertex);
        }
    }

    private void visit(Graph<T> graph, Vertex<T> vertex) {
        if (visited.contains(vertex))
            return;
        visited.add(vertex);
        for (Vertex<T> v : graph.adjacentVerticesOf(vertex))
            visit(graph, v);
        stack.push(vertex);
    }

    private void assign(Graph<T> transposedGraph, Vertex<T> v, Vertex<T> root) {
        if (visited.contains(v))
            return;

        components.makeSet(v);
        components.union(v, root);
        visited.add(v);

        for (Vertex<T> u : transposedGraph.adjacentVerticesOf(v))
            assign(transposedGraph, u, root);
    }

    public static <T> List<List<Vertex<T>>> find(Graph<T> graph) {
        StronglyConnectedComponents<T> scc = new StronglyConnectedComponents<>();
        scc.process(graph);
        return scc.components.disjointSets();
    }
}
