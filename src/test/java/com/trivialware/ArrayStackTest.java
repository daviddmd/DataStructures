package com.trivialware;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.trivialware.*;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class ArrayStackTest {
    StackADT<Integer> stack;

    @BeforeEach
    void setUp() {
        stack = new ArrayStack<>();
        for (int i = 0; i < 10; i++) {
            assertEquals(i, stack.push(i));
        }
    }

    @Test
    void empty() {
        assertFalse(stack.empty());
        for (int i = 0; i < 10; i++) {
            assertEquals(9-i, stack.pop());
        }
        assertTrue(stack.empty());
    }

    @Test
    void peek() {
        assertEquals(9,stack.peek());
        assertEquals(9,stack.pop());
        assertEquals(8,stack.peek());
        assertEquals(360,stack.push(360));
        assertEquals(360,stack.peek());
    }

    @Test
    void pop() {
        for (int i = 9; i >= 0; i--){
            assertEquals(i,stack.pop());
        }
        assertEquals(42,stack.push(42));
        assertEquals(42,stack.pop());
    }

    @Test
    void push() {
        assertEquals(10,stack.push(10));
        assertEquals(10,stack.peek());
        assertEquals(10,stack.pop());
        assertEquals(11,stack.push(11));
        assertEquals(11,stack.peek());
    }

    @Test
    void size() {
        assertEquals(10,stack.size());
        assertEquals(9,stack.pop());
        assertEquals(9,stack.size());
        for (int i = 0; i < 11; i++){
            assertEquals(i,stack.push(i));
        }
        assertEquals(20,stack.size());
    }

    @Test
    void iterator() {
        Iterator<Integer> iterator = stack.iterator();
        int tmp = 9;
        while(iterator.hasNext()){
            assertEquals(tmp,iterator.next());
            tmp-=1;
        }
    }
}