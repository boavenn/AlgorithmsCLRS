package _26_MaximumFlow;

import _22_ElementaryGraphAlgorithms.Graph;

import java.util.List;
import java.util.Map;

import static _22_ElementaryGraphAlgorithms.Graph.Vertex;
import static _22_ElementaryGraphAlgorithms.Graph.Edge;
import static _25_AllPairsShortestPaths.Util.VertexMatrix;

public final class Util
{
    public static class Node
    {
        private Integer height;
        private Integer excess;

        public Node(Integer height, Integer excess) {
            this.height = height;
            this.excess = excess;
        }

        public Integer getHeight() {
            return height;
        }

        public Integer getExcess() {
            return excess;
        }
    }

    public static class Pipe
    {
        private Integer capacity;
        private Integer flow;

        public Pipe(Integer capacity, Integer flow) {
            this.capacity = capacity;
            this.flow = flow;
        }

        public Integer getCapacity() {
            return capacity;
        }

        public Integer getFlow() {
            return flow;
        }
    }

    public static <T> Graph<T> createResidualGraph(Graph<T> graph) {
        Graph<T> temp = new Graph<>(true);

        for (Vertex<T> v : graph.vertices())
            temp.addVertex(v.getKey());

        for (Edge<T> e : graph.edges()) {
            temp.addEdge(e.getSrc().getKey(), e.getDest().getKey(), e.getWeight());
            temp.addEdge(e.getDest().getKey(), e.getSrc().getKey(), 0);
        }

        return temp;
    }

    public static <T> Graph<T> initializePreflow(Graph<T> graph, Vertex<T> src,
                                                 Map<Vertex<T>, Node> nodes, VertexMatrix<T, Pipe> pipes) {
        Graph<T> resGraph = Util.createResidualGraph(graph);

        // init nodes
        for (Vertex<T> v : resGraph.vertices())
            nodes.put(v, new Node(0, 0));
        nodes.get(src).height = resGraph.vertices().size();

        // init pipes
        for (Edge<T> e : resGraph.edges())
            pipes.set(e.getSrc(), e.getDest(), new Pipe(0, 0));
        for (Edge<T> e : graph.edges())
            pipes.get(e.getSrc(), e.getDest()).capacity = e.getWeight();

        // init preflow
        for (Vertex<T> v : resGraph.adjacentVerticesOf(src)) {
            int c = pipes.get(src, v).capacity;
            pipes.get(src, v).flow = c;
            pipes.get(v, src).flow = -c;
            nodes.get(src).excess -= c;
            nodes.get(v).excess += c;
        }
        return resGraph;
    }

    public static <T> void relabel(Graph<T> resGraph, Vertex<T> vertex,
                                   Map<Vertex<T>, Node> nodes, VertexMatrix<T, Pipe> pipes) {
        int minHeight = Integer.MAX_VALUE;
        for (Vertex<T> v : resGraph.adjacentVerticesOf(vertex)) {
            if (nodes.get(vertex).height <= nodes.get(v).height &&
                    pipes.get(vertex, v).capacity - pipes.get(vertex, v).flow > 0)
                minHeight = Math.min(minHeight, nodes.get(v).height);
        }
        nodes.get(vertex).height = minHeight + 1;
    }

    public static <T> void push(Vertex<T> u, Vertex<T> v, Map<Vertex<T>, Node> nodes, VertexMatrix<T, Pipe> pipes) {
        int flow = Math.min(nodes.get(u).excess, pipes.get(u, v).capacity - pipes.get(u, v).flow);
        nodes.get(u).excess -= flow;
        nodes.get(v).excess += flow;
        pipes.get(u, v).flow += flow;
        pipes.get(v, u).flow -= flow;
    }
}
