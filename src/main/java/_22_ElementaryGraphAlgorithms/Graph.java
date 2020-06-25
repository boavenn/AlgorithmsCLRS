package _22_ElementaryGraphAlgorithms;

import java.util.*;

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

        public void setKey(T key) {
            this.key = key;
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
            return "[" + key + "]";
        }
    }

    public static class Edge<T>
    {
        protected Vertex<T> src;
        protected Vertex<T> dest;
        protected Integer weight;

        public Edge(Vertex<T> src, Vertex<T> dest, Integer weight) {
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
            Edge<?> edge = (Edge<?>) o;
            return src.equals(edge.src) && dest.equals(edge.dest);
        }

        @Override
        public String toString() {
            String w = weight == null ? "" : "(" + weight + ")";
            return "[" + src.key + " -" + w + "-> " + dest.key + "]";
        }
    }

    private Map<Vertex<T>, List<Edge<T>>> adj = new HashMap<>();
    private boolean directed;

    public Graph(boolean directed) {
        this.directed = directed;
    }

    public boolean isDirected() {
        return directed;
    }

    public int numberOfVertices() {
        return getVertices().size();
    }

    public int numberOfEdges() {
        int n = getEdges().size();
        return directed ? n : n / 2;
    }

    public List<Vertex<T>> getVertices() {
        return new LinkedList<>(adj.keySet());
    }

    public List<Vertex<T>> getAdjacentVerticesOf(T key) {
        return getAdjacentVerticesOf(new Vertex<>(key));
    }

    public List<Vertex<T>> getAdjacentVerticesOf(Vertex<T> v) {
        if(!containsVertex(v))
            return null;

        List<Vertex<T>> temp = new LinkedList<>();
        for (Edge<T> e : adj.get(v))
            temp.add(e.dest);
        return temp;
    }

    public List<Edge<T>> getEdges() {
        List<Edge<T>> temp = new LinkedList<>();
        for (List<Edge<T>> l : adj.values())
            temp.addAll(l);
        return temp;
    }

    public List<Edge<T>> getAdjacentEdgesOf(T key) {
        return getAdjacentEdgesOf(new Vertex<>(key));
    }

    public List<Edge<T>> getAdjacentEdgesOf(Vertex<T> v) {
        return containsVertex(v) ? new LinkedList<>(adj.get(v)) : null;
    }

    public void addVertex(T key) {
        addVertex(new Vertex<>(key));
    }

    public void addVertex(Vertex<T> v) {
        adj.putIfAbsent(v, new LinkedList<>());
    }

    public void removeVertex(T key) {
        removeVertex(new Vertex<>(key));
    }

    public void removeVertex(Vertex<T> v) {
        if(!containsVertex(v))
            return;

        adj.values().forEach(edges -> edges.removeIf(e -> e.src.equals(v) || e.dest.equals(v)));
        adj.remove(v);
    }

    public void addEdge(T a, T b) {
        addEdge(new Vertex<>(a), new Vertex<>(b));
    }

    public void addEdge(Vertex<T> a, Vertex<T> b) {
        addEdge(a, b, null);
    }

    public void addEdge(T a, T b, Integer weight) {
        addEdge(new Vertex<>(a), new Vertex<>(b), weight);
    }

    public void addEdge(Vertex<T> a, Vertex<T> b, Integer weight) {
        if(containsEdge(new Edge<>(a, b)))
            return;

        adj.get(a).add(new Edge<>(a, b, weight));
        if (!directed)
            adj.get(b).add(new Edge<>(b, a, weight));
    }

    public void removeEdge(T a, T b) {
        removeEdge(new Vertex<>(a), new Vertex<>(b));
    }

    public void removeEdge(Vertex<T> a, Vertex<T> b) {
        adj.get(a).removeIf(e -> e.dest.equals(b));
        if (!directed)
            adj.get(b).removeIf(e -> e.dest.equals(a));
    }

    public boolean containsVertex(T key) {
        return containsVertex(new Vertex<>(key));
    }

    public boolean containsVertex(Vertex<T> v) {
        return adj.containsKey(v);
    }

    public boolean containsEdge(T a, T b) {
        return containsEdge(new Edge<>(new Vertex<>(a), new Vertex<>(b), null));
    }

    public boolean containsEdge(Edge<T> e) {
        return adj.values().stream().anyMatch(list -> list.contains(e));
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Map.Entry<Vertex<T>, List<Edge<T>>> e : adj.entrySet()) {
            str.append(e.getKey().getKey()).append(": [");
            for (Edge<T> v : e.getValue())
                str.append(v.dest.getKey()).append(", ");
            int idx = str.length() - 2;
            if (str.charAt(idx) == ',')
                str.replace(idx, idx + 2, "]\n");
            else
                str.append("]\n");
        }
        return str.substring(0, str.length() - 1);
    }

    public static <T> Graph<T> transpose(Graph<T> graph) {
        if(!graph.directed)
            throw new IllegalArgumentException("Graph is not directed");

        Graph<T> temp = new Graph<>(true);

        List<Vertex<T>> vertices = graph.getVertices();
        for (Vertex<T> v : vertices) {
            temp.addVertex(v.key);
        }

        for (Vertex<T> v : vertices) {
            for (Edge<T> e : graph.getAdjacentEdgesOf(v))
                temp.addEdge(e.dest.key, e.src.key, e.weight);
        }

        return temp;
    }
}
