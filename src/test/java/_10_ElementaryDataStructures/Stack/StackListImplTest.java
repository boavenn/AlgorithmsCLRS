package _10_ElementaryDataStructures.Stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StackListImplTest
{
    private StackListImpl<Integer> stack;

    @BeforeEach
    void setUp() {
        stack = new StackListImpl<>();
        for (int i : new int[]{1, 2, 3}) {
            stack.push(i);
        }
    }

    @Test
    void elementsShouldBeCorrectlyStacked() {
        assertEquals(3, stack.top());
        assertEquals(3, stack.pop());
        assertEquals(2, stack.top());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.top());
        assertEquals(1, stack.pop());
    }

    @Test
    void sizeShouldReturnCorrectSize() {
        assertFalse(stack.isEmpty());
        assertEquals(3, stack.size());
        stack.pop();
        stack.pop();
        stack.pop();
        assertEquals(0, stack.size());
        assertTrue(stack.isEmpty());
    }
}