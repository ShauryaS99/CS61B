public class LinkedListDeque<T> implements Deque<T> {
    //make sure to implement generic structures
    private class Node {
        private Node prev;
        private T item;
        private Node next;

        Node(Node p, T i, Node n) {
            item = i;
            next = n;
            prev = p;
        }
    }

    private Node sentinel;
    private int size;

    /**empty circular sentinel node*/
    public LinkedListDeque() {
        sentinel = new Node(sentinel, null, sentinel);
        size = 0;
    }

    @Override
    public void addFirst(T x) {

        if (size < 1) {
            sentinel.next = new Node(sentinel.prev, x, sentinel.next);
            sentinel.prev = sentinel.next;
            sentinel.prev.next = sentinel;
            sentinel.prev.prev = sentinel.next.next;
        } else {
            sentinel.next = new Node(sentinel.prev, x, sentinel.next);
            sentinel.next.next.prev = sentinel.next;
            sentinel.next.prev = sentinel.next;
        }
        size += 1;
    }

    /** Adds an item to the end of the list. */
    @Override
    public void addLast(T x) {
        if (size < 1) {
            sentinel.next = new Node(sentinel.prev, x, sentinel.next);
            sentinel.prev = sentinel.next;
            sentinel.prev.next = sentinel;
            sentinel.prev.prev = sentinel.next.next;
        } else if (size < 2) {
            sentinel.prev = new Node(sentinel.next, x, sentinel);
            sentinel.next.next = sentinel.prev;

        } else {
            sentinel.prev = new Node(sentinel.prev, x, sentinel);
            sentinel.prev.prev.next = sentinel.prev;
            sentinel.prev.prev = sentinel.prev.prev.prev.next;
        }
        size += 1;
    }

    @Override
    public  boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        Node sentinelcopy = sentinel;
        while (size != 0) {
            System.out.print(sentinelcopy.next.item + " ");
            sentinelcopy = sentinelcopy.next;
            size--;
        }
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        } else {
            T x = sentinel.next.item;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size--;
            return x;
        }
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        } else {
            T x = sentinel.prev.item;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            size--;
            return x;
        }
    }

    @Override
    public T get(int index) {
        int x = 0;
        Node sentinelcopy = sentinel;
        if (index >= size || index < 0) {
            return null;
        } else {
            while (x < index) {
                sentinelcopy = sentinelcopy.next;
                x++;
            }
            return sentinelcopy.next.item;
        }
    }

    public T getRecursive(int index) {
        Node sentinelcopy = sentinel;
        return helper(sentinelcopy.next, index);
    }

    private T helper(Node sentinelcopy, int index) {
        if (index >= size || index < 0) {
            return null;
        } else if (index == 0) {
            return sentinelcopy.item;
        }
        return helper(sentinelcopy.next, index - 1);
    }

}
