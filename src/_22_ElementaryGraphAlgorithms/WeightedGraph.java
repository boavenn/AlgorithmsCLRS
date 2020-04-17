package _22_ElementaryGraphAlgorithms;

import java.util.*;
import java.util.stream.Collectors;

public class WeightedGraph<T> extends Graph<T>
{
    public static class Edge<T>
    {
        T src;
        T dest;
        int weight;

        public Edge(T src, T dest, int weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge<?> edge = (Edge<?>) o;
            return (src.equals(edge.src) && dest.equals(edge.dest)) || (src.equals(edge.dest) && dest.equals(edge.src));
        }

        @Override
        public int hashCode() {
            return Objects.hash(src, dest, weight);
        }

        @Override
        public String toString() {
            return "{src=" + src + ", dest=" + dest + ", weight=" + weight + "}";
        }
    }

    private Map<Vertex<T>, List<Edge<T>>> adj = new HashMap<>();

    @Override
    public void addVertex(T key) {
        adj.putIfAbsent(new Vertex<>(key), new LinkedList<>());
    }

    @Override
    public void removeVertex(T key) {
        adj.values().forEach(e -> e.removeIf(edge -> edge.src.equals(key) || edge.dest.equals(key)));
        adj.remove(new Vertex<>(key));
    }

    @Override
    public void addEdge(T a, T b) {
        addEdge(a, b, 0);
    }

    public void addEdge(T a, T b, int weight) {
        Vertex<T> v1 = new Vertex<>(a);
        Vertex<T> v2 = new Vertex<>(b);
        adj.get(v1).add(new Edge<>(a, b, weight));
        adj.get(v2).add(new Edge<>(b, a, weight));
    }

    @Override
    public void removeEdge(T a, T b) {
        Vertex<T> v1 = new Vertex<>(a);
        Vertex<T> v2 = new Vertex<>(b);
        List<Edge<T>> l1 = adj.get(v1);
        List<Edge<T>> l2 = adj.get(v2);
        if (l1 != null)
            l1.remove(new Edge<>(a, b, 0));
        if (l2 != null)
            l2.remove(new Edge<>(a, b, 0));
    }

    @Override
    public List<Vertex<T>> getVertices() {
        return new LinkedList<>(adj.keySet());
    }

    public List<Edge<T>> getEdges() {
        List<Edge<T>> temp = new LinkedList<>();
        for (List<Edge<T>> l : adj.values())
            temp.addAll(l);
        return temp;
    }

    @Override
    public List<Vertex<T>> getAdjacentVertices(T key) {
        return getAdjacentEdges(key).stream().map(e -> new Vertex<T>(e.dest)).collect(Collectors.toList());
    }

    public List<Edge<T>> getAdjacentEdges(T key) {
        return adj.get(new Vertex<>(key));
    }

    @Override
    public boolean contains(T key) {
        return adj.containsKey(new Vertex<>(key));
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Map.Entry<Vertex<T>, List<Edge<T>>> e : adj.entrySet()) {
            str.append(e.getKey().key).append(": [");
            for (Edge<T> v : e.getValue())
                str.append(v.dest).append(", ");
            int idx = str.length() - 2;
            if (str.charAt(idx) == ',')
                str.replace(idx, idx + 2, "]\n");
            else
                str.append("]\n");
        }
        return str.substring(0, str.length() - 1);
    }

    private static class Example
    {
        public static void main(String[] args) {
            WeightedGraph<Character> graph = new WeightedGraph<>();

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
            System.out.println(graph.getEdges());
        }
    }
}
