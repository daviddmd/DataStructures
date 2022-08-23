import com.trivialware.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArraySortsTest {
    Integer[] arr;
    Integer[] expected;
    Integer[] arr1;
    Integer[] expected1;
    @BeforeEach
    void setUp() {
        arr = new Integer[]{4, 2, 8, 7, 9, 15, 0, 5, 3};
        expected = new Integer[]{0,2,3,4,5,7,8,9,15};
        arr1 = new Integer[]{1,2,7,6,4,0,3,9,15,14,3,5,7};
        expected1 = new Integer[]{0,1,2,3,3,4,5,6,7,7,9,14,15};
    }
    @Test
    void swap() {
        assertEquals(4, arr[0]);
        assertEquals(2, arr[1]);
        ArraySorts.swap(arr, 0, 1);
        assertEquals(2, arr[0]);
        assertEquals(4, arr[1]);
    }

    @Test
    void selectionSort() {
        ArraySorts.selectionSort(arr);
        ArraySorts.selectionSort(arr1);
        for (int i = 0; i < arr.length; i++){
            assertEquals(expected[i],arr[i]);
        }
        for (int i = 0; i < arr1.length; i++){
            assertEquals(expected1[i],arr1[i]);
        }
    }

    @Test
    void insertionSort() {
        ArraySorts.insertionSort(arr);
        ArraySorts.insertionSort(arr1);
        for (int i = 0; i < arr.length; i++){
            assertEquals(expected[i],arr[i]);
        }
        for (int i = 0; i < arr1.length; i++){
            assertEquals(expected1[i],arr1[i]);
        }
    }

    @Test
    void bubbleSort() {
        ArraySorts.bubbleSort(arr);
        ArraySorts.bubbleSort(arr1);
        for (int i = 0; i < arr.length; i++){
            assertEquals(expected[i],arr[i]);
        }
        for (int i = 0; i < arr1.length; i++){
            assertEquals(expected1[i],arr1[i]);
        }
    }

    @Test
    void quickSort() {
        ArraySorts.quickSort(arr);
        ArraySorts.quickSort(arr1);
        for (int i = 0; i < arr.length; i++){
            assertEquals(expected[i],arr[i]);
        }
        for (int i = 0; i < arr1.length; i++){
            assertEquals(expected1[i],arr1[i]);
        }
    }

    @Test
    void mergeSort() {
        ArraySorts.mergeSort(arr);
        ArraySorts.mergeSort(arr1);
        for (int i = 0; i < arr.length; i++){
            assertEquals(expected[i],arr[i]);
        }
        for (int i = 0; i < arr1.length; i++){
            assertEquals(expected1[i],arr1[i]);
        }
    }
}