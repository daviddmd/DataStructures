package com.trivialware;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class OrderedSinglyLinkedListTest {
    OrderedListADT<Integer> list;

    @BeforeEach
    void setUp() {
        list = new OrderedSinglyLinkedList<>();
    }

    @Test
    void add() {
        assertTrue(list.isEmpty());
        assertEquals(0,list.size());
        list.add(5);
        assertEquals(5,list.getFirst());
        assertEquals(5,list.getLast());
        list.add(4);
        assertEquals(4,list.getFirst());
        assertEquals(5,list.getLast());
        list.add(9);
        assertEquals(4,list.getFirst());
        assertEquals(9,list.getLast());
        list.add(0);
        assertEquals(0,list.getFirst());
        assertEquals(9,list.getLast());
        Iterator<Integer> iterator = list.iterator();
        int[] expected = new int[]{0,4,5,9};
        int currentPos = 0;
        while(iterator.hasNext()){
            assertEquals(expected[currentPos++],iterator.next());
        }
    }
}