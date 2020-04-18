package _22_ElementaryGraphAlgorithms;

import java.util.*;

public class UndirectedGraph<T> extends Graph<T>
{
    @Override
    public List<Vertex<T>> getAdjacentVerticesOf(Vertex<T> v) {
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

    public List<Edge<T>> getEdgesOf(Vertex<T> v) {
        return adj.get(v);
    }

    @Override
    public void addEdge(Vertex<T> a, Vertex<T> b) {
        adj.get(a).add(new Edge<>(a, b));
        adj.get(b).add(new Edge<>(b, a));
    }

    @Override
    public void removeEdge(Vertex<T> a, Vertex<T> b) {
        adj.get(a).removeIf(e -> e.dest.equals(b));
        adj.get(b).removeIf(e -> e.dest.equals(a));
    }

    private static class Example
    {
        public static void main(String[] args) {
            UndirectedGraph<Character> graph = new UndirectedGraph<>();

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
            System.out.println(graph.getEdges());

            System.out.println("Breadth first traversal, root: s");
            System.out.println(Graph.breadthFirstSearch(graph, new Vertex<>('s')));

            System.out.println("Depth first traversal, root: v");
            System.out.println(Graph.depthFirstSearch(graph, new Vertex<>('v')));
        }
    }
}
