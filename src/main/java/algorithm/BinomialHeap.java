package algorithm;


public class BinomialHeap {
    /**
     * Size of an empty heap
     */
    private static int EMPTY_HEAP_SIZE = 0;

    /**
     * Minimal value of an empty heap
     */
    private static int EMPTY_MIN_VALUE = -1;

    /**
     * Pointer to tree with minimal root value
     */
    private HeapNode min;

    /**
     * Pointer to first tree in a linked list of binomial trees
     */
    private HeapNode first;

    /**
     * Pointer to last tree in a linked list of binomial trees
     */
    private HeapNode last;

    /**
     * Number of elements (non-negative integers) in the heap
     */
    private int size;

    /**
     * Number of binomial trees in the heap
     */
    private int treesNum;

    /**
     * Creates a new empty binomial heap.
     */
    public BinomialHeap() {
        this(null);
    }

    /**
     * Creates a new binomial heap with a given first binomial tree.
     *
     * precondition: node == null || node.validate() != null
     *
     * @param node
     *            Binomial tree
     */
    private BinomialHeap(HeapNode node) {
        this.min = node;
        this.first = node;
        this.last = node;

        // set size and trees count
        if (node != null) {
            this.size = node.getSize();
            treesNum = 1;
        } else {
            this.size = EMPTY_HEAP_SIZE;
            treesNum = 0;
        }
    }

    /**
     * Resets the data structure to contain no data.
     *
     * postcondition: this.empty()
     */
    private void reset() {
        min = null;
        first = null;
        last = null;
        size = EMPTY_HEAP_SIZE;
        treesNum = 0;
    }

    /**
     * public boolean empty()
     *
     * precondition: none
     *
     * The method returns true if and only if the heap is empty.
     *
     * Time complexity: O(1)
     */
    public boolean empty() {
        return this.size == EMPTY_HEAP_SIZE;
    }

    /**
     * public void insert(int value)
     *
     * Insert value into the heap
     *
     * Time complexity: O(logn) w.c., O(1) amortized
     */
    public void insert(int value) {
        BinomialHeap heapToMeld;
        HeapNode newHeapNode = new HeapNode(value);

        if (empty()) { // first element
            this.first = newHeapNode;
            this.last = newHeapNode;
            this.min = newHeapNode;
            this.size = 1;

        } else { // use meld to add element to heap
            heapToMeld = new BinomialHeap(newHeapNode);
            this.meld(heapToMeld);
        }
    }

    /**
     * public void deleteMin()
     *
     * Delete the minimum value
     *
     * Time complexity: O(logn)
     */
    public void deleteMin() {
        if (empty()) {
            return; // nothing to do
        }

        // collect all min's children to new heap
        BinomialHeap h = new BinomialHeap();
        HeapNode p = this.min.getLeftmostChild();
        if (p != null) {
            while (p != null) {
                h.addTree(p);
                p = p.getNext();
            }
        }

        // remove min from trees list
        if (this.min.getPrev() != null) {
            this.min.getPrev().setNext(this.min.getNext());
        }
        if (this.min.getNext() != null) {
            this.min.getNext().setPrev(this.min.getPrev());
        }
        if (this.min == this.first) {
            this.first = this.min.getNext();
        }
        if (this.min == this.last) {
            this.last = this.min.getPrev();
        }

        this.meld(h);
    }

    /**
     * public int findMin()
     *
     * Return the minimum value
     *
     * Time complexity: O(1)
     */
    public int findMin() {
        if (empty()) {
            return EMPTY_MIN_VALUE; // nothing to find
        } else {
            return this.min.getValue();
        }
    }

    /**
     * public void meld (BinomialHeap heap2)
     *
     * Meld the heap with heap2
     *
     * Time complexity: O(logn)
     */
    public void meld(BinomialHeap heap2) {
        if (heap2 == null) {
            return; // nothing to meld
        }

        // add heap2's trees to the trees list
        BinomialHeap h = BinomialHeap.merge(this, heap2);

        if (h.empty()) {
            this.fixProperties();
            return;
        }

        HeapNode prevX = null;
        HeapNode x = h.getFirst();
        HeapNode nextX = x.getNext();

        // successively link nodes of same ranks
        while (nextX != null) {
            // different ranks, no need to link
            if (x.getRank() != nextX.getRank()
                    || (nextX.getNext() != null && nextX.getNext()
                    .getRank() == x.getRank())) {
                prevX = x;
                x = nextX;
            } else {
                // find the larger node to be linked under the other node
                if (x.getValue() <= nextX.getValue()) {
                    x.setNext(nextX.getNext());
                    if (nextX.getNext() != null) {
                        nextX.getNext().setPrev(x);
                    }
                    x.linkWith(nextX);
                } else {
                    if (prevX == null) {
                        h.setFirst(nextX);
                    } else {
                        prevX.setNext(nextX);
                    }
                    nextX.linkWith(x);
                    x = nextX;
                }
            }
            nextX = x.getNext();
        }
        // copy the created heap trees to the trees list
        this.first = h.first;

        // fix size, trees count, min node, last
        this.fixProperties();
    }

    /**
     * Fixes the heap's properties: size, treesNum, min, last.
     * This method should be called after each call to meld(), to
     * maintain the class's invariant.
     *
     * postcondition: this.isValid()
     */
    private void fixProperties() {
        HeapNode p = this.first;
        HeapNode last = p;
        this.size = 0;
        this.treesNum = 0;
        this.min = null;

        while (p != null) {
            this.size += p.getSize();
            this.treesNum++;
            if (this.min == null) {
                this.min = p;
            } else if (p.getValue() < this.min.getValue()) {
                this.min = p;
            }

            last = p;
            p = p.getNext();
        }

        // fix last pointer
        this.setLast(last);

        // fix list ends pointers
        if (this.getLast() != null) {
            this.getLast().setNext(null);
        }
        if (this.getFirst() != null) {
            this.getFirst().setPrev(null);
        }
    }

    /**
     * Merges the trees lists of two binomial heaps into a new heap.
     *
     * NOTE: The created merged heap is not necessarily valid, because it may
     * contain trees of the same rank.
     *
     * @param heap1
     *            First heap to merge
     * @param heap2
     *            Second heap to merge
     * @return Merged heap
     */
    public static BinomialHeap merge(BinomialHeap heap1, BinomialHeap heap2) {
        BinomialHeap h = new BinomialHeap();

        HeapNode p1 = heap1.getFirst();
        HeapNode p2 = heap2.getFirst();

        // merge-sort two linked lists of binomial trees
        while (p1 != null && p2 != null) {
            if (p1.getRank() <= p2.getRank()) {
                h.addTree(p1);
                p1 = p1.getNext();
            } else {
                h.addTree(p2);
                p2 = p2.getNext();
            }
        }

        // concatenate the remainders
        while (p1 != null) {
            h.addTree(p1);
            p1 = p1.getNext();
        }
        while (p2 != null) {
            h.addTree(p2);
            p2 = p2.getNext();
        }

        return h;
    }

    /**
     * Adds a single binomial tree to the heap's trees list.
     *
     * NOTE: The heap is not necessarily valid after calling addTree. see merge
     * for details.
     *
     * precondition: tree != null
     *
     * @param tree
     *            Tree to add
     */
    private void addTree(HeapNode tree) {
        if (empty()) {
            this.first = tree;
            this.last = tree;
        } else {
            this.last.setNext(tree);
            tree.setPrev(this.last);
            this.last = tree;
        }

        this.size += tree.getSize();
    }

    /**
     * Returns the pointer to the first binomial tree in the heap.
     *
     * @return Pointer to the first binomial tree, null if heap is empty.
     */
    public HeapNode getFirst() {
        return first;
    }

    /**
     * Sets a binomial tree as the first in the heap.
     *
     * @param first
     *            Node to set.
     */
    public void setFirst(HeapNode first) {
        this.first = first;
    }

    /**
     * Returns the pointer to the last binomial tree in the heap.
     *
     * @return Pointer to the last binomial tree, null if heap is empty.
     */
    public HeapNode getLast() {
        return last;
    }

    /**
     * Sets a binomial tree as the last in the heap.
     *
     * @param last
     *            Node to set.
     */
    public void setLast(HeapNode last) {
        this.last = last;
    }

    /**
     * public int size()
     *
     * Return the number of elements in the heap
     *
     * Time complexity: O(1)
     */
    public int size() {
        return this.size;
    }


    /**
     * public void arrayToHeap()
     *
     * Insert the array to the heap. Delete previous elements in the heap.
     *
     * Time complexity: O(array.length)
     */
    public void arrayToHeap(int[] array) {
        this.reset();
        for (int i : array) {
            this.insert(i);
        }
    }

    /**
     * public boolean isValid()
     *
     * Returns true if and only if the heap is valid.
     *
     * Time complexity: O(n)
     */
    public boolean isValid() {
        if (empty()) {
            return true; // an empty tree is a valid tree
        }

        // validate size
        int actualTreesCount = 0;
        HeapNode p = this.first;
        while (p != null) {
            actualTreesCount++;
            p = p.getNext();
        }


        // validate binomial trees
        int[] actualRanks = new int[actualTreesCount];
        int actualMin = EMPTY_MIN_VALUE;
        //ValidatedInfo validInfo;
        ValidatedInfo validInfo;
        int actualSize = 0;
        p = this.first;
        int i = 0;

        while (p != null) {
            validInfo = p.validate();
            if (validInfo == null) {
                return false; // invalid binomial minimal tree
            }

            // collect ranks list, total size
            actualRanks[i++] = validInfo.getRank();
            actualSize += validInfo.getSize();

            // collect min value
            if (actualMin == EMPTY_MIN_VALUE || p.getValue() < actualMin) {
                actualMin = p.getValue();
            }
            p = p.getNext();
        }

        // validate heap size
        return actualSize == this.size; // invalid 'size' value

        // all's good
    }
}