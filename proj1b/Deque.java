public interface Deque<T> {


        /**Inserts x to the front of the list*/
    void addFirst(T x);

        /**Inserts x to the back of the list*/
    void addLast(T x);

        /**Checks if deque is empty*/
    boolean isEmpty();

        /**Prints deque in order*/
    void printDeque();

        /**Removes x from the back of the list*/
    T removeLast();

        /**Removes x from the front of the list*/
    T removeFirst();

        /**Gets the item at the given index*/
    T get(int index);

        /**Returns the size of the list*/
    int size();

}

