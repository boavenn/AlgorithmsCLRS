package _22_ElementaryGraphAlgorithms;

import java.util.*;

public class DirectedGraph<T> extends Graph<T>
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
        return new LinkedList<>(adj.get(v));
    }

    @Override
    public void addEdge(Vertex<T> a, Vertex<T> b) {
        adj.get(a).add(new Edge<>(a, b));
    }

    @Override
    public void removeEdge(Vertex<T> a, Vertex<T> b) {
        adj.get(a).removeIf(e -> e.dest.equals(b));
    }

    public static <T> DirectedGraph<T> transpose(DirectedGraph<T> graph) {
        DirectedGraph<T> temp = new DirectedGraph<>();

        List<Vertex<T>> vertices = graph.getVertices();
        for (Vertex<T> v : vertices) {
            temp.addVertex(v.getKey());
        }

        for (Vertex<T> v : vertices) {
            for (Vertex<T> u : graph.getAdjacentVerticesOf(v.getKey()))
                temp.addEdge(u.getKey(), v.getKey());
        }

        return temp;
    }

    public static <T> List<Vertex<T>> topologicalSort(DirectedGraph<T> graph) {
        // true means permanent mark, false means temporary
        Map<Vertex<T>, Boolean> visited = new HashMap<>();
        Stack<Vertex<T>> stack = new Stack<>();

        List<Vertex<T>> vertices = graph.getVertices();
        for (Vertex<T> v : vertices) {
            if (!visited.containsKey(v))
                topologicalVisit(graph, v, visited, stack);
        }

        List<Vertex<T>> temp = new LinkedList<>();
        while (!stack.isEmpty())
            temp.add(stack.pop());
        return temp;
    }

    private static <T> void topologicalVisit(DirectedGraph<T> graph, Vertex<T> v, Map<Vertex<T>, Boolean> visited, Stack<Vertex<T>> stack) {
        if (visited.containsKey(v) && visited.get(v)) // permanent mark
            return;
        if (visited.containsKey(v) && !visited.get(v)) // temporary mark
            throw new IllegalArgumentException("Graph is not a DAG");

        // temporary mark
        visited.put(v, false);

        for (Vertex<T> vtx : graph.getAdjacentVerticesOf(v.getKey()))
            topologicalVisit(graph, vtx, visited, stack);

        // permanent mark
        visited.put(v, true);
        stack.push(v);
    }

    public static <T> List<DirectedGraph<T>> stronglyConnectedComponents(DirectedGraph<T> graph) {
        Map<Vertex<T>, Boolean> visited = new HashMap<>();
        Stack<Vertex<T>> stack = new Stack<>();
        DirectedGraph<T> transposedGraph = transpose(graph);
        Map<Vertex<T>, DirectedGraph<T>> components = new HashMap<>();

        List<Vertex<T>> vertices = graph.getVertices();
        for (Vertex<T> v : vertices)
            sccVisit(graph, v, visited, stack);

        visited.clear(); // will be used to check if vertex is already in a component

        while (!stack.isEmpty()) {
            Vertex<T> u = stack.pop();
            sccAssign(transposedGraph, u, u, visited, components);
        }

        // can be omitted if you dont care about links in subgraphs
        for (Vertex<T> v : vertices) {
            DirectedGraph<T> subGraph = components.get(v);
            for (Vertex<T> u : graph.getAdjacentVerticesOf(v)) {
                if (subGraph.contains(u)) {
                    subGraph.addEdge(v.getKey(), u.getKey());
                }
            }
        }

        return new LinkedList<>(new HashSet<>(components.values()));
    }

    private static <T> void sccVisit(DirectedGraph<T> graph, Vertex<T> v, Map<Vertex<T>, Boolean> visited, Stack<Vertex<T>> stack) {
        if (visited.containsKey(v))
            return;

        visited.put(v, true);

        for (Vertex<T> u : graph.getAdjacentVerticesOf(v))
            sccVisit(graph, u, visited, stack);

        stack.push(v);
    }

    private static <T> void sccAssign(DirectedGraph<T> graph, Vertex<T> v, Vertex<T> root,
                                      Map<Vertex<T>, Boolean> visited, Map<Vertex<T>, DirectedGraph<T>> components) {
        if (visited.containsKey(v))
            return;

        if (!components.containsKey(root))
            components.put(root, new DirectedGraph<>());
            // adding additional entry to map so we can get every vertex component in O(1) time
            // can be omitted if you dont care about links in a subgraph
        else
            components.put(v, components.get(root));

        components.get(root).addVertex(v);
        visited.put(v, true);

        for (Vertex<T> u : graph.getAdjacentVerticesOf(v.getKey()))
            sccAssign(graph, u, root, visited, components);
    }

    private static class Example
    {
        public static void main(String[] args) {
            DirectedGraph<Character> graph = new DirectedGraph<>();
            Character[] V = {'u', 'x', 'v', 'w', 'y', 'z'};
            Character[][] E = {
                    {'u', 'x'}, {'x', 'v'}, {'u', 'v'}, {'v', 'y'},
                    {'y', 'x'}, {'w', 'y'}, {'w', 'z'}, {'z', 'z'}
            };

            for (Character c : V)
                graph.addVertex(c);
            for (Character[] c : E)
                graph.addEdge(c[0], c[1]);

            System.out.println("Graph 0: ");
            System.out.println(graph);
            System.out.println("Breadth first traversal, root: w");
            System.out.println(UndirectedGraph.breadthFirstSearch(graph, new Vertex<>('w')));
            System.out.println("Depth first traversal, root: u");
            System.out.println(UndirectedGraph.depthFirstSearch(graph, new Vertex<>('u')));

            DirectedGraph<Integer> graph1 = new DirectedGraph<>();
            Integer[] V1 = {0, 1, 2, 3, 4, 5};
            Integer[][] E1 = {{5, 2}, {5, 0}, {4, 0}, {4, 1}, {3, 1}, {2, 3}};

            for (Integer i : V1)
                graph1.addVertex(i);
            for (Integer[] i : E1)
                graph1.addEdge(i[0], i[1]);

            System.out.println("\nGraph 1: ");
            System.out.println(graph1);
            System.out.println("Topological sort: ");
            System.out.println(DirectedGraph.topologicalSort(graph1));
            // will throw an exception because first graph is not a DAG (Directed Asyclic Graph)
            // System.out.println(DirectedGraph.topologicalSort(graph));

            DirectedGraph<Character> graph2 = new DirectedGraph<>();

            Character[] V2 = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
            Character[][] E2 = {
                    {'a', 'b'}, {'b', 'c'}, {'c', 'd'}, {'d', 'c'},
                    {'d', 'h'}, {'h', 'h'}, {'c', 'g'}, {'g', 'h'},
                    {'g', 'f'}, {'f', 'g'}, {'b', 'f'}, {'b', 'e'},
                    {'e', 'f'}, {'e', 'a'}
            };

            for (Character c : V2)
                graph2.addVertex(c);
            for (Character[] c : E2)
                graph2.addEdge(c[0], c[1]);

            System.out.println("\nGraph 2: ");
            System.out.println(graph2);

            List<DirectedGraph<Character>> l = stronglyConnectedComponents(graph2);
            System.out.println("Strongly connected components: ");
            for (DirectedGraph<Character> g : l)
                System.out.println("******\n" + g);
        }
    }
}
