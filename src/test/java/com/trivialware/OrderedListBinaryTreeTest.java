package com.trivialware;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.trivialware.*;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class OrderedListBinaryTreeTest {
    OrderedListADT<Integer> list;

    @BeforeEach
    void setUp() {
        list = new OrderedListBinaryTree<>();
    }

    void addNumbersToList(OrderedListADT<Integer> list) {
        list.add(20);
        list.add(7);
        list.add(30);
        list.add(5);
        list.add(25);
        list.add(31);
        list.add(4);
        list.add(6);
        list.add(24);
        list.add(26);
        list.add(32);
        list.add(27);
    }
    @Test
    void size() {
        assertEquals(0, list.size());
        list.add(1);
        list.add(2);
        assertEquals(2, list.size());
        list.remove(0);
        assertEquals(1, list.size());
        list.add(2); //repetidos não existem em BSTs
        assertEquals(1, list.size());
        list.clear();
        assertEquals(0, list.size());
    }

    @Test
    void isEmpty() {
        assertTrue(list.isEmpty());
        list.add(2);
        assertFalse(list.isEmpty());
        list.remove(0);
        assertTrue(list.isEmpty());
        list.add(2);
        list.add(2);
        assertFalse(list.isEmpty());
        list.clear();
        assertTrue(list.isEmpty());
    }

    @Test
    void contains() {
        assertFalse(list.contains(0));
        list.add(0);
        assertTrue(list.contains(0));
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(-1);
        assertTrue(list.contains(-1));
        assertTrue(list.contains(3));
        assertTrue(list.contains(0));
        list.clear();
        assertFalse(list.contains(0));
    }

    @Test
    void toArray() {
        int[] numbers = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int num : numbers) {
            list.add(num);
        }
        Object[] arr = list.toArray();
        for (int i = 0; i < arr.length; i++) {
            assertEquals(numbers[i], arr[i]);
        }
    }

    @Test
    void toArrayUntil() {
        int[] numbers = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int num : numbers) {
            list.add(num);
        }
        Object[] arr = list.toArrayUntil(3);
        assertEquals(4, arr.length);
        for (int i = 0; i < arr.length; i++) {
            assertEquals(numbers[i], arr[i]);
        }
    }

    @Test
    void toArrayAfter() {
        int[] numbers = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int num : numbers) {
            list.add(num);
        }
        Object[] arr = list.toArrayAfter(3);
        assertEquals(6, arr.length);
        for (int i = 3; i < arr.length; i++) {
            assertEquals(numbers[i], arr[i - 3]);
        }
    }

    @Test
    void toArrayBetween() {
        int[] numbers = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int num : numbers) {
            list.add(num);
        }
        Object[] arr = list.toArrayBetween(2, 6);
        assertEquals(5, arr.length);
        for (int i = 2; i < arr.length; i++) {
            assertEquals(numbers[i], arr[i - 2]);
        }
        arr = list.toArrayBetween(0, 0);
        assertEquals(numbers[0], arr[0]);
        arr = list.toArrayBetween(numbers.length - 1, numbers.length - 1);
        assertEquals(numbers[numbers.length - 1], arr[0]);
        assertThrows(IndexOutOfBoundsException.class, () -> list.toArrayBetween(3, 2));
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
        Iterator<Integer>iterator = list.iterator();
        int[] expected = new int[]{0,4,5,9};
        int currentPos = 0;
        while(iterator.hasNext()){
            assertEquals(expected[currentPos++],iterator.next());
        }
    }

    @Test
    void removeElement() {
        addNumbersToList(list);
        assertTrue(list.contains(7));
        list.remove(Integer.valueOf(7));
        assertFalse(list.contains(7));
        assertTrue(list.contains(27));
        list.remove(Integer.valueOf(27));
        assertFalse(list.contains(27));
    }

    @Test
    void removeIndex() {
        addNumbersToList(list);
        assertEquals(4, list.getFirst());
        list.remove(0);
        assertEquals(5, list.getFirst());
        assertEquals(32, list.getLast());
        list.remove(0);
        list.remove(list.size() - 1);
        assertEquals(6, list.getFirst());
        assertEquals(31, list.getLast());
        assertEquals(9, list.size());
    }

    @Test
    void removeFirst() {
        addNumbersToList(list);
        assertEquals(4, list.getFirst());
        assertEquals(4, list.removeFirst());
        assertEquals(5, list.getFirst());
        assertEquals(32, list.removeLast());
        assertEquals(5, list.getFirst());
        list.clear();
        list.add(2);
        list.add(3);
        assertEquals(2, list.getFirst());
        assertEquals(2, list.removeFirst());
        assertEquals(3, list.getFirst());
    }

    @Test
    void removeLast() {
        addNumbersToList(list);
        assertEquals(32, list.getLast());
        assertEquals(32, list.removeLast());
        assertEquals(31, list.getLast());
        assertEquals(4, list.removeFirst());
        assertEquals(31, list.removeLast());
        list.clear();
        list.add(2);
        list.add(3);
        assertEquals(3, list.getLast());
        assertEquals(3, list.removeLast());
        assertEquals(2, list.getLast());
    }

    @Test
    void clear() {
        addNumbersToList(list);
        assertEquals(12, list.size());
        assertTrue(list.clear());
        assertEquals(0, list.size());
    }

    @Test
    void get() {
        addNumbersToList(list);
        assertEquals(4, list.get(0));
        assertEquals(32, list.get(list.size() - 1));
        assertEquals(32, list.removeLast());
        assertEquals(4, list.removeFirst());
        assertEquals(5, list.get(0));
        assertEquals(31, list.get(list.size() - 1));
        assertEquals(31, list.remove(list.size()-1));
        assertEquals(5, list.remove(0));
        assertEquals(6, list.get(0));
        assertEquals(30, list.get(list.size() - 1));
    }

    @Test
    void getFirst() {
        addNumbersToList(list);
        assertEquals(4, list.getFirst());
        assertEquals(4, list.removeFirst());
        assertEquals(5, list.getFirst());
        assertEquals(32, list.removeLast());
        assertEquals(5, list.removeFirst());
        assertEquals(6, list.getFirst());
    }

    @Test
    void getLast() {
        addNumbersToList(list);
        assertEquals(32, list.getLast());
        assertEquals(32, list.removeLast());
        assertEquals(31, list.getLast());
        assertEquals(4, list.removeFirst());
        assertEquals(31, list.removeLast());
        assertEquals(30, list.getLast());
    }

    @Test
    void iterator() {
        addNumbersToList(list);
        Iterator<Integer> iterator = list.iterator();
        int[] expected = new int[]{4,5,6,7,20,24,25,26,27,30,31,32};
        int index = 0;
        while (iterator.hasNext()){
            assertEquals(expected[index++],iterator.next());
        }
    }
}