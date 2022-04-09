package algorithm;

public class HeapNode {

    /**
     * Node's value
     */
    private int value;

    /**
     * Node's binomial rank
     */
    private int rank;

    /**
     * Number of elements in tree
     */
    private int size;

    /**
     * Pointer to following sibling node
     */
    private HeapNode next;

    /**
     * Pointer to previous sibling node
     */
    private HeapNode prev;

    /**
     * Pointer to leftmost child node
     */
    private HeapNode leftmostChild;

    /**
     * Pointer to rightmost child node
     */
    private HeapNode rightmostChild;

    /**
     * Instantiates a new binomial node with a given value.
     *
     * precondition: value >= 0
     *
     * @param value
     *            Value to store in node
     */
    public HeapNode(int value) {
        this.value = value;
        this.size = 1;
        this.rank = 0;
    }

    /**
     * @return Pointer to following sibling node
     */
    public HeapNode getNext() {
        return next;
    }

    /**
     * Sets following sibling node
     *
     * @param next
     *            Pointer to new following sibling node
     */
    public void setNext(HeapNode next) {
        this.next = next;
    }

    /**
     * @return Pointer to previous sibling node
     */
    public HeapNode getPrev() {
        return prev;
    }

    /**
     * Sets previous sibling node
     *
     * @param prev
     *            Pointer to new previous sibling node
     */
    public void setPrev(HeapNode prev) {
        this.prev = prev;
    }

    /**
     * @return Pointer to leftmost child node
     */
    public HeapNode getLeftmostChild() {
        return leftmostChild;
    }

    /**
     * Sets leftmost child node
     *
     * @param leftmostChild
     *            Pointer to new leftmost child node
     */
    public void setLeftmostChild(HeapNode leftmostChild) {
        this.leftmostChild = leftmostChild;
    }

    /**
     * @return Pointer to rightmost child node
     */
    public HeapNode getRightmostChild() {
        return rightmostChild;
    }

    /**
     * @return Binomial rank of the tree
     */
    public int getRank() {
        return this.rank;
    }

    /**
     * @return Number of elements in the tree
     */
    public int getSize() {
        return this.size;
    }

    /**
     * @return Node's value
     */
    public int getValue() {
        return this.value;
    }



    /**
     * Returns the actual size and rank of the node, independently of the
     * other method's validity.
     *
     * May return null if the node was found invalid, i.e.: its root value
     * is not the minimal value, one of its children is an invalid heap
     * node, or its size isn't 2^rank.
     *
     * @return An object containing the collected actual size and rank of
     *         the node, null if the node was found invalid.
     */
    public ValidatedInfo validate() {
        HeapNode child = this.getLeftmostChild();
        ValidatedInfo childResult;
        int size = 1;
        int rank = 0;

        // traversing child nodes, validating them
        while (child != null) {

            childResult = child.validate();
            if (childResult == null) {
                return null; // child node is invalid
            } else {
                // collect size and rank
                size += childResult.getSize();
                rank++;
            }

            child = child.getNext();
        }

        // verify size and rank relation (size == 2^rank)
        if (size != Math.pow(2, rank)) {
            return null; // invalid size / rank
        }

        return new ValidatedInfo(size, rank);
    }

    /**
     * Links the current node to another node.
     *
     * precondition: other != null
     * precondition: other.getValue() > this.getValue()
     * precondition: other.getRank() == this.getRank()
     * postcondition: this.getRank() = 1 + $prev(this.getRank())
     * postcondition: this.getRightmostChild() == other
     */
    public void linkWith(HeapNode other) {
        other.setNext(null);
        other.setPrev(this.rightmostChild);
        if (this.rightmostChild != null) {
            this.rightmostChild.setNext(other);
        } else {
            this.leftmostChild = other;
        }
        this.rightmostChild = other;


        this.rank++;
        this.size += other.size;
    }
}
