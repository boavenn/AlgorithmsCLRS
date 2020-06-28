package _24_SingleSourceShortestPaths;

import _22_ElementaryGraphAlgorithms.Graph;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static _22_ElementaryGraphAlgorithms.Util.*;
import static _22_ElementaryGraphAlgorithms.Graph.*;
import static java.util.stream.Collectors.*;

class BellmanFordTest
{
    @Test
    void returnCorrectOutput() {
        Graph<Character> graph = new Graph<>(true);

        Character[] V = {'s', 't', 'x', 'y', 'z'};
        Character[][] E = {
                {'s', 't'}, {'s', 'y'}, {'t', 'x'}, {'x', 't'}, {'t', 'y'},
                {'t', 'z'}, {'y', 'x'}, {'y', 'z'}, {'z', 'x'}, {'z', 's'}
        };
        Integer[] W = {6, 7, 5, -2, 8, -4, -3, 9, 7, 2};

        for (Character c : V)
            graph.addVertex(c);
        for (int i = 0; i < E.length; i++)
            graph.addEdge(E[i][0], E[i][1], W[i]);

        Map<Vertex<Character>, Integer> distances = BellmanFord.distances(graph, createVertex('s'));
        assertEquals(0, distances.get(createVertex('s')));
        assertEquals(2, distances.get(createVertex('t')));
        assertEquals(4, distances.get(createVertex('x')));
        assertEquals(7, distances.get(createVertex('y')));
        assertEquals(-2, distances.get(createVertex('z')));

        List<Vertex<Character>> path = Util.shortestPath(BellmanFord.predecessors(graph, createVertex('s')),
                createVertex('s'), createVertex('t'));
        assertIterableEquals(List.of('s', 'y', 'x', 't'), path.stream().map(Vertex::getKey).collect(toList()));
    }
}