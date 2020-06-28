package _24_SingleSourceShortestPaths;

import _22_ElementaryGraphAlgorithms.Graph;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static _22_ElementaryGraphAlgorithms.Util.createVertex;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

class DijkstraTest
{
    @Test
    void returnCorrectOutput() {
        Graph<Character> graph = new Graph<>(true);

        Character[] V = {'s', 't', 'x', 'y', 'z'};
        Character[][] E = {
                {'s', 't'}, {'s', 'y'}, {'t', 'x'}, {'t', 'y'}, {'y', 't'},
                {'y', 'x'}, {'y', 'z'}, {'z', 'x'}, {'z', 's'}, {'x', 'z'}
        };
        Integer[] W = {10, 5, 1, 2, 3, 9, 2, 6, 7, 4};
        for (Character c : V) {
            graph.addVertex(c);
        }
        for (int i = 0; i < E.length; i++) {
            graph.addEdge(E[i][0], E[i][1], W[i]);
        }

        Map<Graph.Vertex<Character>, Integer> distances = Dijkstra.distances(graph, createVertex('s'));
        assertEquals(0, distances.get(createVertex('s')));
        assertEquals(8, distances.get(createVertex('t')));
        assertEquals(9, distances.get(createVertex('x')));
        assertEquals(5, distances.get(createVertex('y')));
        assertEquals(7, distances.get(createVertex('z')));

        List<Graph.Vertex<Character>> path = Util.shortestPath(Dijkstra.predecessors(graph, createVertex('s')),
                createVertex('s'), createVertex('x'));
        assertIterableEquals(List.of('s', 'y', 't', 'x'), path.stream().map(Graph.Vertex::getKey).collect(toList()));
    }
}