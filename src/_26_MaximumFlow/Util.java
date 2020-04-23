package _26_MaximumFlow;

import _22_ElementaryGraphAlgorithms.Graph;

import java.util.Map;

import static _22_ElementaryGraphAlgorithms.Graph.Vertex;
import static _22_ElementaryGraphAlgorithms.Graph.Edge;
import static _25_AllPairsShortestPaths.Util.VertexMatrix;

abstract class Util
{
    static class Node
    {
        Integer height;
        Integer excess;

        public Node(Integer height, Integer excess) {
            this.height = height;
            this.excess = excess;
        }
    }

    static class Pipe
    {
        Integer capacity;
        Integer flow;

        public Pipe(Integer capacity, Integer flow) {
            this.capacity = capacity;
            this.flow = flow;
        }
    }

    static <T> Graph<T> createResidualGraph(Graph<T> graph) {
        Graph<T> temp = new Graph<>(true);

        for (Vertex<T> v : graph.getVertices())
            temp.addVertex(v.getKey());

        for (Edge<T> e : graph.getEdges()) {
            temp.addEdge(e.getSrc().getKey(), e.getDest().getKey(), e.getWeight());
            temp.addEdge(e.getDest().getKey(), e.getSrc().getKey(), 0);
        }

        return temp;
    }

    static <T> Graph<T> initializePreflow(Graph<T> graph, Vertex<T> src, Map<Vertex<T>, Node> nodes, VertexMatrix<T, Pipe> pipes) {
        Graph<T> resGraph = Util.createResidualGraph(graph);

        // init nodes
        for (Vertex<T> v : resGraph.getVertices())
            nodes.put(v, new Node(0, 0));
        nodes.get(src).height = resGraph.getVertices().size();

        // init pipes
        for (Edge<T> e : resGraph.getEdges())
            pipes.set(e.getSrc(), e.getDest(), new Pipe(0, 0));
        for (Edge<T> e : graph.getEdges())
            pipes.get(e.getSrc(), e.getDest()).capacity = e.getWeight();

        // init preflow
        for (Vertex<T> v : resGraph.getAdjacentVerticesOf(src)) {
            int c = pipes.get(src, v).capacity;
            pipes.get(src, v).flow = c;
            pipes.get(v, src).flow = -c;
            nodes.get(src).excess -= c;
            nodes.get(v).excess += c;
        }
        return resGraph;
    }

    static <T> void relabel(Graph<T> resGraph, Vertex<T> u, Map<Vertex<T>, Node> nodes, VertexMatrix<T, Pipe> pipes) {
        int minHeight = Integer.MAX_VALUE;
        for (Vertex<T> v : resGraph.getAdjacentVerticesOf(u)) {
            if (nodes.get(u).height <= nodes.get(v).height && pipes.get(u, v).capacity - pipes.get(u, v).flow > 0)
                minHeight = Math.min(minHeight, nodes.get(v).height);
        }
        nodes.get(u).height = minHeight + 1;
    }

    static <T> void push(Vertex<T> u, Vertex<T> v, Map<Vertex<T>, Node> nodes, VertexMatrix<T, Pipe> pipes) {
        int flow = Math.min(nodes.get(u).excess, pipes.get(u, v).capacity - pipes.get(u, v).flow);
        nodes.get(u).excess -= flow;
        nodes.get(v).excess += flow;
        pipes.get(u, v).flow += flow;
        pipes.get(v, u).flow -= flow;
    }
}
