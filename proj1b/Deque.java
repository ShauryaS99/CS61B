public interface Deque<T> {

        /**Inserts x to the front of the list*/
        public void addFirst(T x);

        /**Inserts x to the back of the list*/
        public void addLast(T x);

        /**Checks if deque is empty*/
        public boolean isEmpty();

        /**Prints deque in order*/
        public void printDeque();

        /**Removes x from the back of the list*/
        public T removeLast();

        /**Removes x from the front of the list*/
        public T removeFirst();

        /**Gets the item at the given index*/
        public T get(int index);

        /**Returns the size of the list*/
        public int size();

}

