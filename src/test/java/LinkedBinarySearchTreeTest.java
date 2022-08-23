import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.trivialware.*;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class LinkedBinarySearchTreeTest {
    BinarySearchTreeADT<Integer> tree;

    void insertElements(BinarySearchTreeADT<Integer> tree) {
        tree.insert(20);
        tree.insert(7);
        tree.insert(30);
        tree.insert(5);
        tree.insert(25);
        tree.insert(31);
        tree.insert(4);
        tree.insert(6);
        tree.insert(24);
        tree.insert(26);
        tree.insert(32);
        tree.insert(27);
    }

    @BeforeEach
    void setUp() {
        tree = new LinkedBinarySearchTree<>();
    }


    @Test
    void isEmpty() {
        assertTrue(tree.isEmpty());
        tree.insert(5);
        assertFalse(tree.isEmpty());
        tree.makeEmpty();
        assertTrue(tree.isEmpty());
        tree.insert(4);
        tree.insert(5);
        assertFalse(tree.isEmpty());
        tree.remove(4);
        assertFalse(tree.isEmpty());
    }

    @Test
    void makeEmpty() {
        tree.insert(5);
        tree.insert(4);
        tree.insert(3);
        assertFalse(tree.isEmpty());
        tree.makeEmpty();
        assertTrue(tree.isEmpty());
    }

    @Test
    void contains() {
        assertFalse(tree.contains(2));
        tree.insert(3);
        assertFalse(tree.contains(2));
        assertTrue(tree.contains(3));
        tree.makeEmpty();
        for (int i = 0; i < 5; i++) {
            tree.insert(i);
        }
        for (int i = 0; i < 5; i++) {
            assertTrue(tree.contains(i));
        }
    }

    @Test
    void iteratorPreOrder() {
        insertElements(tree);
        int[] expectedOrder = new int[]{20, 7, 5, 4, 6, 30, 25, 24, 26, 27, 31, 32};
        int currentIndex = 0;
        Iterator<Integer> iterator = tree.iteratorPreOrder();
        while (iterator.hasNext()) {
            assertEquals(expectedOrder[currentIndex++], iterator.next());
        }
    }

    @Test
    void iteratorInOrder() {
        insertElements(tree);
        int[] expectedOrder = new int[]{4, 5, 6, 7, 20, 24, 25, 26, 27, 30, 31, 32};
        int currentIndex = 0;
        Iterator<Integer> iterator = tree.iteratorInOrder();
        while (iterator.hasNext()) {
            assertEquals(expectedOrder[currentIndex++], iterator.next());
        }
    }

    @Test
    void iteratorPostOrder() {
        insertElements(tree);
        int[] expectedOrder = new int[]{4, 6, 5, 7, 24, 27, 26, 25, 32, 31, 30, 20};
        int currentIndex = 0;
        Iterator<Integer> iterator = tree.iteratorPostOrder();
        while (iterator.hasNext()) {
            assertEquals(expectedOrder[currentIndex++], iterator.next());
        }
    }

    @Test
    void iteratorLevelOrder() {
        insertElements(tree);
        int[] expectedOrder = new int[]{20, 7, 30, 5, 25, 31, 4, 6, 24, 26, 32, 27};
        int currentIndex = 0;
        Iterator<Integer> iterator = tree.iteratorLevelOrder();
        while (iterator.hasNext()) {
            assertEquals(expectedOrder[currentIndex++], iterator.next());
        }
        tree.remove(30);
        expectedOrder = new int[]{20, 7, 31, 5, 25, 32, 4, 6, 24, 26, 27};
        currentIndex = 0;
        iterator = tree.iteratorLevelOrder();
        while (iterator.hasNext()) {
            assertEquals(expectedOrder[currentIndex++], iterator.next());
        }
    }

    @Test
    void insert() {
        assertTrue(tree.isEmpty());
        tree.insert(5);
        tree.insert(4);
        tree.insert(6);
        assertFalse(tree.isEmpty());
    }

    @Test
    void remove() {
        assertTrue(tree.isEmpty());
        tree.insert(5);
        tree.insert(4);
        tree.insert(6);
        assertFalse(tree.isEmpty());
        tree.remove(4);
        tree.remove(5);
        tree.remove(6);
        assertTrue(tree.isEmpty());
    }

    @Test
    void findMin() {
        insertElements(tree);
        assertEquals(4, tree.findMin());
        tree.remove(4);
        assertEquals(5, tree.findMin());
    }

    @Test
    void findMax() {
        insertElements(tree);
        assertEquals(32, tree.findMax());
        tree.remove(32);
        assertEquals(31, tree.findMax());
        tree.remove(31);
        assertEquals(30, tree.findMax());
        tree.remove(30);
        assertEquals(27, tree.findMax());
    }

    @Test
    void removeMin() {
        insertElements(tree);
        assertEquals(4, tree.findMin());
        assertEquals(4, tree.removeMin());
        assertEquals(5, tree.findMin());
    }

    @Test
    void removeMax() {
        insertElements(tree);
        assertEquals(32, tree.findMax());
        assertEquals(32, tree.removeMax());
        assertEquals(31, tree.findMax());
    }

    @Test
    void testContains() {
        insertElements(tree);
        assertTrue(tree.contains(27));
        assertTrue(tree.contains(4));
        assertTrue(tree.contains(32));
        assertTrue(tree.contains(7));
        assertFalse(tree.contains(0));
        assertFalse(tree.contains(-2));
    }

    @Test
    void height() {
        tree.insert(1);
        assertEquals(0, tree.height());
        tree.insert(0);
        tree.insert(2);
        assertEquals(1, tree.height());
        tree.insert(3);
        assertEquals(2, tree.height());
        tree.makeEmpty();
        insertElements(tree);
        assertEquals(4, tree.height());
        tree.remove(27);
        assertEquals(3, tree.height());
    }

    @Test
    void size(){
        insertElements(tree);
        assertEquals(12,tree.size());
        tree.remove(27);
        assertEquals(11,tree.size());
        tree.remove(25);
        assertEquals(10,tree.size());
        assertEquals(20,tree.getRootElement());
        tree.remove(20);;
        assertEquals(9,tree.size());
        assertEquals(24,tree.getRootElement());
    }
}