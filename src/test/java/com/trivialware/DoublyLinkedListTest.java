package com.trivialware;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.trivialware.*;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class DoublyLinkedListTest {
    DoublyLinkedList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new DoublyLinkedList<>();
    }

    void addNumbersToList(DoublyLinkedList<Integer> list) {
        int[] numbers = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int num : numbers) {
            list.addLast(num);
        }
    }

    @Test
    void size() {
        assertEquals(0, list.size());
        list.addFirst(1);
        list.addFirst(2);
        assertEquals(2, list.size());
        assertEquals(2,list.remove(0));
        assertEquals(1, list.size());
        list.addFirst(2);
        assertEquals(2, list.size());
        list.clear();
        assertEquals(0, list.size());
    }

    @Test
    void isEmpty() {
        assertTrue(list.isEmpty());
        list.addFirst(2);
        assertFalse(list.isEmpty());
        assertEquals(2,list.remove(0));
        assertTrue(list.isEmpty());
        list.addFirst(2);
        list.addFirst(2);
        assertFalse(list.isEmpty());
        list.clear();
        assertTrue(list.isEmpty());
    }

    @Test
    void contains() {
        assertFalse(list.contains(0));
        list.addFirst(0);
        assertTrue(list.contains(0));
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        list.addFirst(-1);
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
            list.addLast(num);
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
            list.addLast(num);
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
            list.addLast(num);
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
            list.addLast(num);
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
    void set() {
        addNumbersToList(list);
        assertTrue(list.contains(4));
        assertFalse(list.contains(11));
        list.set(3, 11);
        assertFalse(list.contains(4));
        assertTrue(list.contains(11));
    }

    @Test
    void addFirst() {
        addNumbersToList(list);
        assertEquals(1, list.getFirst());
        list.addFirst(-2);
        assertEquals(-2, list.getFirst());
        list.removeFirst();
        assertEquals(1, list.getFirst());
    }

    @Test
    void addLast() {
        assertNull(list.getLast());
        list.addFirst(0);
        assertEquals(0, list.getLast());
        list.addLast(2);
        assertEquals(2, list.getLast());
        assertEquals(0, list.getFirst());
        list.clear();
        addNumbersToList(list);
        assertEquals(9, list.getLast());
        list.addFirst(0);
        list.removeLast();
        list.addLast(10);
        assertEquals(10, list.getLast());
        assertEquals(0, list.getFirst());
    }

    @Test
    void addAtPosition() {
        addNumbersToList(list);
        assertEquals(9, list.size());
        assertEquals(1, list.getFirst());
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
        list.add(1, 55);
        assertEquals(10, list.size());
        assertEquals(55, list.get(1));
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(2));
        assertEquals(3, list.get(3));
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(-2, 56));
        assertEquals(10, list.size());
        list.add(0, 22);
        assertEquals(11, list.size());
        assertEquals(22, list.get(0));
        assertEquals(22, list.getFirst());
        assertEquals(9, list.getLast());
        list.add(list.size() - 1, 99);
        assertEquals(12, list.size());
        assertEquals(9, list.getLast());
        assertEquals(99, list.get(list.size() - 2));
        assertTrue(list.clear());
        assertEquals(0, list.size());
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(1, 5));
        assertEquals(0, list.size());
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(0, 5));
        assertEquals(0, list.size());
        list.addFirst(1);
        assertEquals(1, list.size());
        assertEquals(1, list.getFirst());
        list.add(0, 5);
        assertEquals(2, list.size());
        assertEquals(5, list.getFirst());
    }

    @Test
    void removeElement() {
        addNumbersToList(list);
        assertTrue(list.contains(2));
        assertTrue(list.remove(Integer.valueOf(2)));
        assertFalse(list.contains(2));
        assertFalse(list.contains(15));
        list.addLast(15);
        assertTrue(list.contains(15));
        assertTrue(list.remove(Integer.valueOf(15)));
        assertFalse(list.contains(15));
    }

    @Test
    void removeIndex() {
        addNumbersToList(list);
        assertEquals(1, list.getFirst());
        assertEquals(1,list.remove(0));
        assertEquals(2, list.getFirst());
        assertEquals(9, list.getLast());
        assertEquals(2,list.remove(0));
        list.remove(list.size() - 1);
        assertEquals(3, list.getFirst());
        assertEquals(8, list.getLast());
        assertEquals(6, list.size());
    }

    @Test
    void removeFirst() {
        addNumbersToList(list);
        assertEquals(1, list.getFirst());
        assertEquals(1, list.removeFirst());
        assertEquals(2, list.getFirst());
        assertEquals(9, list.removeLast());
        assertEquals(2, list.getFirst());
        list.clear();
        list.addFirst(2);
        list.addFirst(3);
        assertEquals(3, list.getFirst());
        assertEquals(3, list.removeFirst());
        assertEquals(2, list.getFirst());
    }

    @Test
    void removeLast() {
        addNumbersToList(list);
        assertEquals(9, list.getLast());
        assertEquals(9, list.removeLast());
        assertEquals(8, list.getLast());
        assertEquals(1, list.removeFirst());
        assertEquals(8, list.removeLast());
        list.clear();
        list.addLast(2);
        list.addLast(3);
        assertEquals(3, list.getLast());
        assertEquals(3, list.removeLast());
        assertEquals(2, list.getLast());
    }

    @Test
    void clear() {
        addNumbersToList(list);
        assertEquals(9, list.size());
        assertTrue(list.clear());
        assertEquals(0, list.size());
    }

    @Test
    void get() {
        addNumbersToList(list);
        assertEquals(1, list.get(0));
        assertEquals(9, list.get(list.size() - 1));
        list.removeFirst();
        list.removeLast();
        assertEquals(2, list.get(0));
        assertEquals(8, list.get(list.size() - 1));
        list.remove(0);
        list.remove(list.size() - 1);
        assertEquals(3, list.get(0));
        assertEquals(7, list.get(list.size() - 1));
    }

    @Test
    void getFirst() {
        addNumbersToList(list);
        assertEquals(1, list.getFirst());
        assertEquals(1, list.removeFirst());
        assertEquals(2, list.getFirst());
        assertEquals(9, list.removeLast());
        assertEquals(2, list.removeFirst());
        assertEquals(3, list.getFirst());
    }

    @Test
    void getLast() {
        addNumbersToList(list);
        assertEquals(9, list.getLast());
        assertEquals(9, list.removeLast());
        assertEquals(8, list.getLast());
        assertEquals(1, list.removeFirst());
        assertEquals(8, list.removeLast());
        assertEquals(7, list.getLast());
    }

    @Test
    void iterator() {
        int[] numbers = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int num : numbers) {
            list.addLast(num);
        }
        int idx = 0;
        for (Integer i : list) {
            assertEquals(numbers[idx], i);
            idx += 1;
        }
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            if (next % 2 == 0) {
                iterator.remove();
            }
        }
        for (Integer i : list) {
            assertTrue(i % 2 != 0);
        }
    }

    @Test
    void numberSame() {
        addNumbersToList(list);
        for (int i = 1; i < 10; i++) {
            for (int f = 0; f < i; f++) {
                list.addLast(i);
            }
        }
        for (int i = 1; i < 10; i++) {
            assertEquals(i + 1, list.numberOfSameElements(i));
        }
    }
}