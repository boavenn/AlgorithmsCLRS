package _22_ElementaryGraphAlgorithms;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Graph<T>
{
    public static class Vertex<T>
    {
        private T key;

        public Vertex(T key) {
            this.key = key;
        }

        public T getKey() {
            return key;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vertex<?> vertex = (Vertex<?>) o;
            return Objects.equals(key, vertex.key);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key);
        }

        @Override
        public String toString() {
            return String.format("[%s]", key);
        }
    }

    public static class Edge<T>
    {
        protected Vertex<T> src;
        protected Vertex<T> dest;
        protected Integer weight;

        public Edge(Vertex<T> src, Vertex<T> dest, Integer weight) {
            if (src == null || dest == null)
                throw new IllegalArgumentException("Vertices cannot be null");
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }

        public Edge(Vertex<T> src, Vertex<T> dest) {
            this(src, dest, null);
        }

        public Vertex<T> getSrc() {
            return src;
        }

        public Vertex<T> getDest() {
            return dest;
        }

        public Integer getWeight() {
            return weight;
        }

        public void setSrc(Vertex<T> src) {
            this.src = src;
        }

        public void setDest(Vertex<T> dest) {
            this.dest = dest;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge<?> e = (Edge<?>) o;
            return src.equals(e.src) && dest.equals(e.dest) &&
                    ((weight == null && e.weight == null) || (weight != null && weight.equals(e.weight)));
        }

        @Override
        public int hashCode() {
            return Objects.hash(src, dest, weight);
        }

        @Override
        public String toString() {
            String w = weight == null ? "" : "(" + weight + ")";
            return String.format("[%s -%s-> %s]", src.key, w, dest.key);
        }
    }

    private Map<Vertex<T>, List<Edge<T>>> adjList = new HashMap<>();
    private boolean directed;

    public Graph(boolean directed) {
        this.directed = directed;
    }

    public boolean isDirected() {
        return directed;
    }

    public boolean contains(Vertex<T> vertex) {
        return adjList.containsKey(vertex);
    }

    public boolean contains(Edge<T> edge) {
        if (!contains(edge.src) || !contains(edge.dest)) {
            return false;
        }
        return Stream.concat(adjList.get(edge.src).stream(), adjList.get(edge.dest).stream()).anyMatch(edge::equals);
    }

    public List<Vertex<T>> vertices() {
        return new LinkedList<>(adjList.keySet());
    }

    public List<Edge<T>> edges() {
        return adjList.values().stream().flatMap(List::stream).collect(Collectors.toList());
    }

    public List<Vertex<T>> adjacentVerticesOf(Vertex<T> v) {
        return contains(v) ? adjList.get(v).stream()
                .map(Edge::getDest)
                .collect(Collectors.toList()) : null;
    }

    public List<Edge<T>> adjacentEdgesOf(Vertex<T> v) {
        return contains(v) ? new LinkedList<>(adjList.get(v)) : null;
    }

    public int numberOfVertices() {
        return vertices().size();
    }

    public int numberOfEdges() {
        int n = edges().size();
        return directed ? n : n / 2;
    }

    public void addVertex(T key) {
        addVertex(new Vertex<>(key));
    }

    public void addVertex(Vertex<T> v) {
        adjList.putIfAbsent(v, new LinkedList<>());
    }

    public void addEdge(T a, T b) {
        addEdge(a, b, null);
    }

    public void addEdge(T a, T b, Integer weight) {
        addEdge(new Vertex<>(a), new Vertex<>(b), weight);
    }

    public void addEdge(Vertex<T> a, Vertex<T> b) {
        addEdge(a, b, null);
    }

    public void addEdge(Vertex<T> a, Vertex<T> b, Integer weight) {
        Edge<T> e = new Edge<>(a, b, weight);
        if (contains(e)) {
            return;
        }
        adjList.get(a).add(e);
        if (!directed) {
            adjList.get(b).add(new Edge<>(b, a, weight));
        }
    }

    public void removeVertex(Vertex<T> v) {
        if (!contains(v)) {
            return;
        }
        adjList.values().forEach(edges -> edges.removeIf(e -> e.src.equals(v) || e.dest.equals(v)));
        adjList.remove(v);
    }

    public void removeEdge(Edge<T> e) {
        removeEdge(e.src, e.dest);
    }

    public void removeEdge(Vertex<T> a, Vertex<T> b) {
        adjList.get(a).removeIf(e -> e.dest.equals(b));
        if (!directed) {
            adjList.get(b).removeIf(e -> e.dest.equals(a));
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (var entry : adjList.entrySet()) {
            str.append(entry.getKey().key).append(": [");
            entry.getValue().forEach(e -> str.append(e.dest.key).append(", "));
            int i = str.length() - 2;
            if (str.charAt(i) == ',')
                str.delete(i, i + 2);
            str.append("]\n");
        }
        return str.substring(0, str.length() - 1);
    }

    public static <T> Graph<T> transpose(Graph<T> graph) {
        if(!graph.directed)
            return graph;

        Graph<T> transposedGraph = new Graph<>(true);

        List<Vertex<T>> vertices = graph.vertices();
        for (Vertex<T> v : vertices) {
            transposedGraph.addVertex(v.key);
        }

        for (Vertex<T> v : vertices) {
            for (Edge<T> e : graph.adjacentEdgesOf(v))
                transposedGraph.addEdge(e.dest.key, e.src.key, e.weight);
        }

        return transposedGraph;
    }
}
