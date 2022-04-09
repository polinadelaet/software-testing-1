package algorithm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Binomial Heap Test")
public class BinomialHeapTest {

    private BinomialHeap heap;

    @BeforeEach
    void init() {
        heap = new BinomialHeap();
    }

    @Test
    void emptyTest() {
        assertTrue(heap.empty());
    }

    @Test
    void validEmptyTreeTest() {
        assertTrue(heap.isValid());
    }

    @Test
    void heapSizeTest() {
        for (int i = 1; i < 10; i++) {
            heap.insert(i);
        }
        assertEquals(9, heap.size());
    }

    @Test
    void arrayToHeapTest() {
        int[] array = { 7, 2, 5, 14, 1};
        heap.arrayToHeap(array);
        assertEquals(5, heap.size());
    }

    @Test
    void isValidTest() {
        int[] array = { 7, 2, 5, 14, 1, 30 };
        heap.arrayToHeap(array);
        assertTrue(heap.isValid());
    }

    @Test
    void findMinTest() {
        int[] array = { 7, 4, 24, -7, 30, 0, 78 };
        heap.arrayToHeap(array);
        assertEquals(-7, heap.findMin());
    }

    @Test
    void findNoMinTest() {
        heap.findMin();
        assertEquals(-1, heap.findMin());
    }

    @Test
    void deleteMinEmptyHeapTest() {
        heap.deleteMin();
        assertTrue(heap.empty());
    }

    @Test
    void deleteMinAfterOneInsertTest() {
        heap.insert(3);
        heap.deleteMin();
        assertTrue(heap.empty());
    }

    @Test
    void deleteMinAfterTwoInsertsTest() {

        heap.insert(3);
        heap.insert(1);
        heap.deleteMin();

        assertEquals(1, heap.size());
        assertEquals(3, heap.findMin());
        assertTrue(heap.isValid());
    }

    @Test
    void deleteMinArrayTest() {
        int[] array = {7, 2, 5, 14, 1, 30, 12, 19, 24, 7, 2, 5, 14, -41, 30};
        heap.arrayToHeap(array);
        assertTrue(heap.isValid());
        assertEquals(-41, heap.findMin());

        heap.deleteMin();
        assertTrue(heap.isValid());
        assertEquals(1, heap.findMin());
    }

    @Test
    void meldTestSameArray() {
        int[] array = {16, 21, 30, 19, 14, 25, 13, 28};
        heap.arrayToHeap(array);

        BinomialHeap heap2 = new BinomialHeap();
        heap2.arrayToHeap(array);

        heap.meld(heap2);

        assertTrue(heap.isValid());
        assertEquals(16, heap.size());
        assertEquals(13, heap.findMin());
    }

    @Test
    void meldTestAnotherArray() {
        int[] array = {16, 21, 30, 19, 14, 25, 13, 28};
        heap.arrayToHeap(array);

        BinomialHeap heap2 = new BinomialHeap();
        int[] array2 = {43, 28, -2, 12, 0, 3};
        heap2.arrayToHeap(array2);
        heap.meld(heap2);

        assertTrue(heap.isValid());
        assertEquals(14, heap.size());
        assertEquals(-2, heap.findMin());
    }

    @Test
    void emptyMeldTest() {
        BinomialHeap secondHeap = new BinomialHeap();

        heap.meld(secondHeap);
        assertTrue(heap.empty());
    }

    @Test
    void emptyWithNullMeldTest() {
        BinomialHeap secondHeap = new BinomialHeap();

        heap.meld(null);
        assertTrue(heap.empty());

        heap.insert(1);
        heap.meld(secondHeap);
        heap.meld(null);
        assertEquals(1, heap.size());
    }

    @Test
    void isValidTestWithSetLeftmostChild() {
        int[] array = { 7, 2, 5, 14, 1,
                        30, 12, 19, 24,
                        7, 2, 5, 14, 1, 30 };
        heap.arrayToHeap(array);
        assertTrue(heap.isValid());

        heap.getLast().getRightmostChild().getRightmostChild().setLeftmostChild(null);
        assertFalse(heap.isValid());
    }
}
