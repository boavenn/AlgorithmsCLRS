package _22_ElementaryGraphAlgorithms;

import java.util.*;

public class Graph<T>
{
    protected static class Vertex<T>
    {
        T key;

        public Vertex(T key) {
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
    }

    protected Map<Vertex<T>, List<Vertex<T>>> adj = new HashMap<>();

    public void addVertex(T key) {
        adj.putIfAbsent(new Vertex<>(key), new LinkedList<>());
    }

    public void removeVertex(T key) {
        Vertex<T> v = new Vertex<>(key);
        adj.values().forEach(e -> e.remove(v));
        adj.remove(v);
    }

    public void addEdge(T a, T b) {
        Vertex<T> v1 = new Vertex<>(a);
        Vertex<T> v2 = new Vertex<>(b);
        adj.get(v1).add(v2);
        adj.get(v2).add(v1);
    }

    public void removeEdge(T a, T b) {
        Vertex<T> v1 = new Vertex<>(a);
        Vertex<T> v2 = new Vertex<>(b);
        List<Vertex<T>> l1 = adj.get(v1);
        List<Vertex<T>> l2 = adj.get(v2);
        if (l1 != null)
            l1.remove(v2);
        if (l2 != null)
            l2.remove(v1);
    }

    public List<Vertex<T>> getVertices() {
        return new LinkedList<>(adj.keySet());
    }

    public List<Vertex<T>> getAdjacentVertices(T key) {
        return adj.get(new Vertex<>(key));
    }

    public boolean contains(T key) {
        return adj.containsKey(new Vertex<>(key));
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Map.Entry<Vertex<T>, List<Vertex<T>>> e : adj.entrySet()) {
            str.append(e.getKey().key).append(": [");
            for (Vertex<T> v : e.getValue())
                str.append(v.key).append(", ");
            int idx = str.length() - 2;
            if (str.charAt(idx) == ',')
                str.replace(idx, idx + 2, "]\n");
            else
                str.append("]\n");
        }
        return str.substring(0, str.length() - 1);
    }

    public static <T> Set<T> breadthFirstSearch(Graph<T> graph, T root) {
        Set<T> visited = new LinkedHashSet<>();
        Queue<T> queue = new LinkedList<>();
        queue.add(root);
        visited.add(root);

        while (!queue.isEmpty()) {
            T vertex = queue.poll();
            for (Graph.Vertex<T> v : graph.getAdjacentVertices(vertex)) {
                if (!visited.contains(v.key)) {
                    visited.add(v.key);
                    queue.add(v.key);
                }
            }
        }

        return visited;
    }

    public static <T> Set<T> depthFirstSearch(Graph<T> graph, T root) {
        Set<T> visited = new LinkedHashSet<>();
        Stack<T> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            T vertex = stack.pop();
            if (!visited.contains(vertex)) {
                visited.add(vertex);
                for (Vertex<T> v : graph.getAdjacentVertices(vertex))
                    stack.push(v.key);
            }
        }

        return visited;
    }

    private static class Example
    {
        public static void main(String[] args) {
            Graph<Character> graph = new Graph<>();

            Character[] V = {'r', 's', 't', 'u', 'v', 'w', 'x', 'y'};
            Character[][] E = {
                    {'r', 'v'}, {'r', 's'}, {'s', 'w'}, {'w', 't'}, {'w', 'x'},
                    {'x', 't'}, {'t', 'u'}, {'x', 'u'}, {'x', 'y'}, {'u', 'y'}
            };

            for (Character c : V)
                graph.addVertex(c);

            for (Character[] c : E)
                graph.addEdge(c[0], c[1]);

            System.out.println(graph);

            System.out.println("Breadth first traversal, root: s");
            System.out.println(Graph.breadthFirstSearch(graph, 's'));

            System.out.println("Depth first traversal, root: v");
            System.out.println(Graph.depthFirstSearch(graph, 'v'));
        }
    }
}
