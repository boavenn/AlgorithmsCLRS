package _22_ElementaryGraphAlgorithms;

import java.util.*;

public abstract class Graph<T>
{
    public static class Vertex<T>
    {
        protected T key;

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
            return key.equals(vertex.key);
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

        public Edge(Vertex<T> src, Vertex<T> dest) {
            this.src = src;
            this.dest = dest;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge<?> edge = (Edge<?>) o;
            return src.equals(edge.src) && dest.equals(edge.dest);
        }

        @Override
        public int hashCode() {
            return Objects.hash(src, dest);
        }

        @Override
        public String toString() {
            return "[" + src.key + " -> " + dest.key + "]";
        }
    }

    protected Map<Vertex<T>, List<Edge<T>>> adj = new HashMap<>();

    public List<Vertex<T>> getVertices() {
        return new LinkedList<>(adj.keySet());
    }

    public void addVertex(Vertex<T> v) {
        adj.putIfAbsent(v, new LinkedList<>());
    }

    public void removeVertex(Vertex<T> v) {
        adj.values().forEach(edges -> edges.removeIf(e -> e.src.equals(v) || e.dest.equals(v)));
        adj.remove(v);
    }

    public boolean contains(Vertex<T> v) {
        return adj.containsKey(v);
    }

    public abstract List<Vertex<T>> getAdjacentVerticesOf(Vertex<T> v);

    public abstract void addEdge(Vertex<T> a, Vertex<T> b);

    public abstract void removeEdge(Vertex<T> a, Vertex<T> b);

    public List<Vertex<T>> getAdjacentVerticesOf(T key) {
        return getAdjacentVerticesOf(new Vertex<>(key));
    }

    public void addVertex(T key) {
        addVertex(new Vertex<>(key));
    }

    public void removeVertex(T key) {
        removeVertex(new Vertex<>(key));
    }

    public void addEdge(T a, T b) {
        addEdge(new Vertex<>(a), new Vertex<>(b));
    }

    public void removeEdge(T a, T b) {
        removeEdge(new Vertex<>(a), new Vertex<>(b));
    }

    public boolean contains(T key) {
        return contains(new Vertex<>(key));
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

    public static <T> List<Edge<T>> breadthFirstSearch(Graph<T> graph, Vertex<T> root) {
        List<Edge<T>> result = new LinkedList<>();
        Set<Vertex<T>> visited = new LinkedHashSet<>();
        Queue<Vertex<T>> queue = new LinkedList<>();

        queue.add(root);
        visited.add(root);

        while (!queue.isEmpty()) {
            Vertex<T> vertex = queue.poll();
            for (Vertex<T> v : graph.getAdjacentVerticesOf(vertex)) {
                if (!visited.contains(v)) {
                    visited.add(v);
                    queue.add(v);
                    result.add(new Edge<T>(vertex, v));
                }
            }
        }

        return result;
    }

    public static <T> List<Edge<T>> depthFirstSearch(Graph<T> graph, Vertex<T> root) {
        List<Edge<T>> result = new LinkedList<>();
        Set<Vertex<T>> visited = new LinkedHashSet<>();
        Stack<Vertex<T>> stack = new Stack<>();

        stack.push(root);

        // only for edge printing
        Vertex<T> last = root;
        while (!stack.isEmpty()) {
            Vertex<T> vertex = stack.pop();
            if (!visited.contains(vertex)) {
                visited.add(vertex);
                if (vertex != last)
                    result.add(new Edge<>(last, vertex));
                last = vertex;
                for (Vertex<T> v : graph.getAdjacentVerticesOf(vertex))
                    stack.push(v);
            }
        }

        return result;
    }
}
