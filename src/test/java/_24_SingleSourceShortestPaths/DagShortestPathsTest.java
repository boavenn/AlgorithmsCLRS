package _24_SingleSourceShortestPaths;

import _22_ElementaryGraphAlgorithms.Graph;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static _22_ElementaryGraphAlgorithms.Util.createVertex;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

class DagShortestPathsTest
{
    @Test
    void returnCorrectOutput() {
        Graph<Character> graph = new Graph<>(true);

        Character[] V = {'r', 's', 't', 'x', 'y', 'z'};
        Character[][] E = {
                {'r', 's'}, {'r', 't'}, {'s', 'x'}, {'s', 't'}, {'t', 'x'},
                {'t', 'y'}, {'t', 'z'}, {'x', 'z'}, {'x', 'y'}, {'y', 'z'}
        };
        Integer[] W = {5, 3, 6, 2, 7, 4, 2, 1, -1, -2};

        for (Character c : V)
            graph.addVertex(c);
        for (int i = 0; i < E.length; i++)
            graph.addEdge(E[i][0], E[i][1], W[i]);

        Map<Graph.Vertex<Character>, Integer> distances = DagShortestPaths.distances(graph, createVertex('s'));
        assertEquals(Integer.MAX_VALUE, distances.get(createVertex('r')));
        assertEquals(0, distances.get(createVertex('s')));
        assertEquals(2, distances.get(createVertex('t')));
        assertEquals(6, distances.get(createVertex('x')));
        assertEquals(5, distances.get(createVertex('y')));
        assertEquals(3, distances.get(createVertex('z')));

        List<Graph.Vertex<Character>> path = Util.shortestPath(DagShortestPaths.predecessors(graph, createVertex('s')),
                createVertex('s'), createVertex('z'));
        assertIterableEquals(List.of('s', 'x', 'y', 'z'), path.stream().map(Graph.Vertex::getKey).collect(toList()));
    }
}