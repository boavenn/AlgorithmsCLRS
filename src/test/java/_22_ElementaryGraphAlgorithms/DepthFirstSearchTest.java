package _22_ElementaryGraphAlgorithms;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static _22_ElementaryGraphAlgorithms.Util.*;
import static _22_ElementaryGraphAlgorithms.Graph.*;

class DepthFirstSearchTest
{
    @Test
    void returnCorrectOutput() {
        Graph<Character> graph = new Graph<>(true);
        Character[] V = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        Character[][] E = {
                {'a', 'd'}, {'d', 'e'}, {'d', 'c'}, {'e', 'f'}, {'f', 'h'},
                {'f', 'b'}, {'b', 'a'}, {'b', 'g'}, {'c', 'g'}, {'g', 'h'}
        };

        for (Character c : V)
            graph.addVertex(c);
        for (Character[] c : E)
            graph.addEdge(c[0], c[1]);

        List<Character> expectedOrder = List.of('a', 'd', 'c', 'g', 'h', 'e', 'f', 'b');
        assertIterableEquals(expectedOrder, DepthFirstSearch.vertexOrder(graph, createVertex('a')).stream()
                .map(Vertex::getKey)
                .collect(Collectors.toList()));

        Map<Vertex<Character>, Vertex<Character>> predecessors = DepthFirstSearch.predecessors(graph, createVertex('a'));
        assertEquals('f', predecessors.get(createVertex('b')).getKey());
        assertEquals('g', predecessors.get(createVertex('h')).getKey());
        assertEquals('a', predecessors.get(createVertex('d')).getKey());
    }
}