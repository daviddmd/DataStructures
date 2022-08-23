import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.trivialware.*;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class StackQueueTest {
    QueueADT<Integer> queue;
    void addNumbersToQueue(QueueADT<Integer> queue) {
        int[] numbers = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int num : numbers) {
            queue.enqueue(num);
        }
    }

    @BeforeEach
    void setUp() {
        queue = new StackQueue<>();
    }

    @Test
    void enqueue() {
        assertTrue(queue.enqueue(5));
        assertEquals(5, queue.first());
        assertTrue(queue.enqueue(4));
        assertEquals(5, queue.first());
        assertEquals(2,queue.size());
        assertEquals(5, queue.dequeue());
        assertEquals(1,queue.size());
        assertEquals(4, queue.dequeue());
        assertEquals(0,queue.size());
    }

    @Test
    void dequeue() {
        addNumbersToQueue(queue);
        for (int i = 1; i < 10; i++) {
            assertEquals(i, queue.dequeue());
        }
    }

    @Test
    void first() {
        addNumbersToQueue(queue);
        for (int i = 1; i < 10; i++) {
            assertEquals(i, queue.first());
            assertEquals(i, queue.dequeue());
        }
    }

    @Test
    void isEmpty() {
        assertTrue(queue.isEmpty());
        addNumbersToQueue(queue);
        assertFalse(queue.isEmpty());
        queue.clear();
        assertTrue(queue.isEmpty());

    }

    @Test
    void size() {
        assertEquals(0, queue.size());
        addNumbersToQueue(queue);
        assertEquals(9, queue.size());
        queue.clear();
        assertEquals(0, queue.size());
        queue.enqueue(1);
        assertEquals(1, queue.size());
        queue.enqueue(3);
        assertEquals(2, queue.size());
    }

    @Test
    void clear() {
        assertTrue(queue.isEmpty());
        addNumbersToQueue(queue);
        assertFalse(queue.isEmpty());
        queue.clear();
        assertTrue(queue.isEmpty());
        queue.enqueue(5);
        assertFalse(queue.isEmpty());
    }

    @Test
    void iterator() {
        Iterator<Integer> iterator = queue.iterator();
        int current = 1;
        while (iterator.hasNext()) {
            assertEquals(current, iterator.next());
            current += 1;
        }
    }
}