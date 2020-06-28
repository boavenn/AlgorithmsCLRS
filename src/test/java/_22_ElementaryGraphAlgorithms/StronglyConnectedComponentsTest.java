package _22_ElementaryGraphAlgorithms;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static _22_ElementaryGraphAlgorithms.Graph.*;
import static java.util.stream.Collectors.*;

class StronglyConnectedComponentsTest
{
    @Test
    void returnCorrectOutput() {
        Graph<Character> graph = new Graph<>(true);

        Character[] V2 = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        Character[][] E2 = {
                {'a', 'b'}, {'b', 'c'}, {'c', 'd'}, {'d', 'c'},
                {'d', 'h'}, {'h', 'h'}, {'c', 'g'}, {'g', 'h'},
                {'g', 'f'}, {'f', 'g'}, {'b', 'f'}, {'b', 'e'},
                {'e', 'f'}, {'e', 'a'}
        };

        for (Character c : V2)
            graph.addVertex(c);
        for (Character[] c : E2)
            graph.addEdge(c[0], c[1]);

        List<List<Vertex<Character>>> components = StronglyConnectedComponents.find(graph);
        components.forEach(System.out::println);
        assertIterableEquals(List.of('h'), components.get(0).stream().map(Vertex::getKey).collect(toList()));
        assertIterableEquals(List.of('c', 'd'), components.get(1).stream().map(Vertex::getKey).collect(toList()));
        assertIterableEquals(List.of('f', 'g'), components.get(2).stream().map(Vertex::getKey).collect(toList()));
        assertIterableEquals(List.of('a', 'b', 'e'), components.get(3).stream().map(Vertex::getKey).collect(toList()));
    }
}