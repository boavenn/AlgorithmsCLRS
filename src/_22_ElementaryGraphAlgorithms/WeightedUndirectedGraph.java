package _22_ElementaryGraphAlgorithms;

import java.util.*;

public class WeightedUndirectedGraph<T> extends Graph<T>
{
    public static class WEdge<T> extends Edge<T>
    {
        private int weight;

        public WEdge(Vertex<T> src, Vertex<T> dest, int weight) {
            super(src, dest);
            this.weight = weight;
        }

        public Vertex<T> getSrc() {
            return src;
        }

        public Vertex<T> getDest() {
            return dest;
        }

        public int getWeight() {
            return weight;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            WEdge<?> wEdge = (WEdge<?>) o;
            return weight == wEdge.weight && src.equals(wEdge.src) && dest.equals(wEdge.dest);
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), src, dest, weight);
        }

        @Override
        public String toString() {
            return "[" + src.key + " -" + weight + "-> " + dest.key + "]";
        }
    }

    @Override
    public List<Vertex<T>> getAdjacentVerticesOf(Vertex<T> v) {
        List<Vertex<T>> temp = new LinkedList<>();
        for (Edge<T> e : adj.get(v))
            temp.add(e.dest);
        return temp;
    }

    @SuppressWarnings("unchecked")
    public List<WEdge<T>> getEdges() {
        List<Edge<T>> temp = new LinkedList<>();
        for (List<Edge<T>> l : adj.values())
            temp.addAll(l);
        return (List<WEdge<T>>)(List<?>) temp;
    }

    @SuppressWarnings("unchecked")
    public List<WEdge<T>> getEdgesOf(Vertex<T> v) {
        return (List<WEdge<T>>)(List<?>) adj.get(v);
    }

    @Override
    public void addEdge(Vertex<T> a, Vertex<T> b) {
        addEdge(a, b, 0);
    }

    public void addEdge(T a, T b, int weight) {
        addEdge(new Vertex<>(a), new Vertex<>(b), weight);
    }

    public void addEdge(Vertex<T> a, Vertex<T> b, int weight) {
        adj.get(a).add(new WEdge<>(a, b, weight));
        adj.get(b).add(new WEdge<>(b, a, weight));
    }

    @Override
    public void removeEdge(Vertex<T> a, Vertex<T> b) {
        adj.get(a).removeIf(e -> e.dest.equals(b));
        adj.get(b).removeIf(e -> e.dest.equals(a));
    }

    private static class Example
    {
        public static void main(String[] args) {
            WeightedUndirectedGraph<Character> graph = new WeightedUndirectedGraph<>();

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
