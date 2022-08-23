import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.trivialware.*;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class ArrayHeapTest {
    HeapADT<Integer> heap;

    @BeforeEach
    void setUp() {
        heap = new ArrayHeap<>();
    }

    void addElements(HeapADT<Integer> heap) {
        heap.insert(13);
        heap.insert(14);
        heap.insert(16);
        heap.insert(19);
        heap.insert(21);
        heap.insert(19);
        heap.insert(68);
        heap.insert(65);
        heap.insert(26);
        heap.insert(32);
        heap.insert(31);
    }

    @Test
    void isEmpty() {
        assertTrue(heap.isEmpty());
        heap.insert(1);
        heap.insert(2);
        heap.insert(3);
        assertFalse(heap.isEmpty());
    }

    @Test
    void size() {
        assertEquals(0, heap.size());
        for (int i = 0; i < 5; i++) {
            heap.insert(i);
        }
        assertEquals(5, heap.size());
        heap.deleteMinimum();
        assertEquals(4, heap.size());
        heap.makeEmpty();
        assertEquals(0, heap.size());
        for (int i = 0; i < 5; i++) {
            heap.insert(i);
        }
        assertEquals(5, heap.size());
    }

    @Test
    void height() {
        int numberOfElementsToInsert, expectedHeight;
        numberOfElementsToInsert = 15;
        expectedHeight = ArrayHeap.expectedCompleteBinaryTreeHeight(numberOfElementsToInsert);
        assertEquals(0, heap.height());
        for (int i = 0; i < numberOfElementsToInsert; i++) {
            heap.insert(i);
        }
        assertEquals(expectedHeight, heap.height());
        numberOfElementsToInsert *= 2;
        expectedHeight = ArrayHeap.expectedCompleteBinaryTreeHeight(numberOfElementsToInsert);
        heap.makeEmpty();
        for (int i = 0; i < numberOfElementsToInsert; i++) {
            heap.insert(i);
        }
        assertEquals(expectedHeight, heap.height());
    }

    @Test
    void makeEmpty() {
        assertTrue(heap.isEmpty());
        assertEquals(0, heap.size());
        for (int i = 0; i < 10; i++) {
            heap.insert(i);
        }
        assertFalse(heap.isEmpty());
        assertEquals(10, heap.size());
        heap.makeEmpty();
        assertEquals(0, heap.size());
        assertTrue(heap.isEmpty());
    }

    @Test
    void contains() {
        assertFalse(heap.contains(1));
        for (int i = 0; i < 9; i++) {
            heap.insert(i);
        }
        assertTrue(heap.contains(2));
        assertTrue(heap.contains(8));
        for (int i = 15; i < 25; i++) {
            heap.insert(i);
        }
        assertTrue(heap.contains(0));
        assertTrue(heap.contains(24));
        assertTrue(heap.contains(2));
        assertFalse(heap.contains(50));
    }

    @Test
    void iteratorPreOrder() {
        addElements(heap);
        int[] expectedOrder;
        int currentIndex;
        expectedOrder = new int[]{13, 14, 19, 65, 26, 21, 32, 31, 16, 19, 68};
        Iterator<Integer> iterator;
        currentIndex = 0;
        iterator = heap.iteratorPreOrder();
        while (iterator.hasNext()) {
            assertEquals(expectedOrder[currentIndex++], iterator.next());
        }
        assertEquals(13, heap.deleteMinimum());
        expectedOrder = new int[]{14, 19, 26, 65, 31, 21, 32, 16, 19, 68};
        currentIndex = 0;
        iterator = heap.iteratorPreOrder();
        while (iterator.hasNext()) {
            assertEquals(expectedOrder[currentIndex++], iterator.next());
        }
    }

    @Test
    void iteratorInOrder() {
        addElements(heap);
        int[] expectedOrder;
        int currentIndex;
        expectedOrder = new int[]{65, 19, 26, 14, 32, 21, 31, 13, 19, 16, 68};
        Iterator<Integer> iterator;
        currentIndex = 0;
        iterator = heap.iteratorInOrder();
        while (iterator.hasNext()) {
            assertEquals(expectedOrder[currentIndex++], iterator.next());
        }
        assertEquals(13, heap.deleteMinimum());
        expectedOrder = new int[]{65, 26, 31, 19, 32, 21, 14, 19, 16, 68};
        currentIndex = 0;
        iterator = heap.iteratorInOrder();
        while (iterator.hasNext()) {
            assertEquals(expectedOrder[currentIndex++], iterator.next());
        }
    }

    @Test
    void iteratorPostOrder() {
        addElements(heap);
        int[] expectedOrder;
        int currentIndex;
        expectedOrder = new int[]{65, 26, 19, 32, 31, 21, 14, 19, 68, 16, 13};
        Iterator<Integer> iterator;
        currentIndex = 0;
        iterator = heap.iteratorPostOrder();
        while (iterator.hasNext()) {
            assertEquals(expectedOrder[currentIndex++], iterator.next());
        }
        assertEquals(13, heap.deleteMinimum());
        expectedOrder = new int[]{65, 31, 26, 32, 21, 19, 19, 68, 16, 14};
        currentIndex = 0;
        iterator = heap.iteratorPostOrder();
        while (iterator.hasNext()) {
            assertEquals(expectedOrder[currentIndex++], iterator.next());
        }
    }

    @Test
    void iteratorLevelOrder() {
        addElements(heap);
        int[] expectedOrder;
        int currentIndex;
        expectedOrder = new int[]{13, 14, 16, 19, 21, 19, 68, 65, 26, 32, 31};
        Iterator<Integer> iterator;
        currentIndex = 0;
        iterator = heap.iteratorLevelOrder();
        while (iterator.hasNext()) {
            assertEquals(expectedOrder[currentIndex++], iterator.next());
        }
        assertEquals(13, heap.deleteMinimum());
        expectedOrder = new int[]{14, 19, 16, 26, 21, 19, 68, 65, 31, 32};
        currentIndex = 0;
        iterator = heap.iteratorLevelOrder();
        while (iterator.hasNext()) {
            assertEquals(expectedOrder[currentIndex++], iterator.next());
        }

    }

    @Test
    void insert() {
        heap.insert(1);
        assertTrue(heap.contains(1));
        assertEquals(1, heap.deleteMinimum());
        assertFalse(heap.contains(1));
        assertTrue(heap.isEmpty());
        for (int i = 2; i < 22; i++) {
            heap.insert(i);
            assertTrue(heap.contains(i));
        }
        for (int i = 2; i < 22; i++) {
            assertEquals(22 - i, heap.size());
            assertTrue(heap.contains(i));
            assertEquals(i, heap.findMinimum());
            assertEquals(i, heap.deleteMinimum());
            assertFalse(heap.contains(i));
            assertNotEquals(i, heap.findMinimum());
        }
    }

    @Test
    void deleteMinimum() {
        for (int i = 0; i < 30; i++) {
            heap.insert(i);
            assertTrue(heap.contains(i));
            assertEquals(i, heap.findMinimum());
            assertEquals(i, heap.deleteMinimum());
            assertNotEquals(i, heap.findMinimum());
            assertFalse(heap.contains(i));
        }
    }

    @Test
    void findMinimum() {
        heap.insert(1);
        heap.insert(3);
        heap.insert(5);
        assertEquals(1, heap.findMinimum());
        heap.insert(4);
        heap.insert(6);
        assertEquals(1, heap.findMinimum());
        heap.insert(0);
        assertEquals(0, heap.findMinimum());
        assertEquals(0, heap.deleteMinimum());
        assertEquals(1, heap.findMinimum());
        assertEquals(1, heap.deleteMinimum());
        assertEquals(3, heap.findMinimum());
    }

    @Test
    void heapSort() {
        Integer[] arr = new Integer[]{7, 4, 3, 15, 0, 8, 2, 1, 87, -5};
        int[] expected = new int[]{-5, 0, 1, 2, 3, 4, 8, 7, 15, 87};
        HeapADT<Integer> heapTest = new ArrayHeap<>(arr);
        int currentIndex = 0;
        while (!heap.isEmpty()) {
            assertEquals(expected[currentIndex], heapTest.deleteMinimum());
            currentIndex += 1;
        }
    }
}