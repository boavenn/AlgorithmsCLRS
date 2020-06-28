package _22_ElementaryGraphAlgorithms;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static _22_ElementaryGraphAlgorithms.Util.*;
import static _22_ElementaryGraphAlgorithms.Graph.*;

class BreadthFirstSearchTest
{
    @Test
    void returnCorrectOutput() {
        Graph<Character> graph = new Graph<>(false);
        Character[] V = {'v', 'r', 's', 'w', 't', 'x', 'u', 'y'};
        Character[][] E = {
                {'r', 'v'}, {'r', 's'}, {'s', 'w'}, {'w', 'x'}, {'u', 'y'},
                {'w', 't'}, {'t', 'u'}, {'x', 't'}, {'x', 'u'}, {'x', 'y'}
        };

        for (Character c : V)
            graph.addVertex(c);
        for (Character[] c : E)
            graph.addEdge(c[0], c[1]);


        List<Character> expectedOrder = List.of('w', 's', 'x', 't', 'r', 'u', 'y', 'v');
        assertIterableEquals(expectedOrder, BreadthFirstSearch.vertexOrder(graph, createVertex('w')).stream()
                        .map(Vertex::getKey)
                        .collect(Collectors.toList()));

        Map<Vertex<Character>, Vertex<Character>> predecessors = BreadthFirstSearch.predecessors(graph, createVertex('w'));
        assertEquals('s', predecessors.get(createVertex('r')).getKey());
        assertEquals('w', predecessors.get(createVertex('s')).getKey());
        assertEquals('x', predecessors.get(createVertex('u')).getKey());
    }
}