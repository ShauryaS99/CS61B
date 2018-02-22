// Make sure to make this class a part of the synthesizer package
package synthesizer;
import java.util.Iterator;

//Make sure to make this class and all of its methods public
//Make sure to make this class extend synthesizer.AbstractBoundedQueue<t>
public class ArrayRingBuffer<T> extends synthesizer.AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from synthesizer.AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
        first = 0;
        last = 0;
        fillCount = 0;
        this.capacity = capacity;
        this.rb = (T[]) new Object[capacity];
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        if (this.isFull()) {
            throw new RuntimeException("Ring buffer overflow"); //throw runtime exception
        }
        rb[last] = x;
        last += 1;
        if (last == capacity) {
            last = 0;
        }
        fillCount += 1;
        //  Enqueue the item. Don't forget to increase fillCount and update last.
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        if (this.isEmpty()) {
            throw new RuntimeException("Ring buffer underflow"); //throw runtime exception
        }
        T firstitem = rb[first];
        rb[first] = null;
        first += 1;
        if (first == capacity) {
            first = 0;
        }
        fillCount -= 1;
        return  firstitem;
        // Dequeue the first item. Don't forget to decrease fillCount and update
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        if (this.isEmpty()) {
            throw new RuntimeException("Ring buffer underflow"); //throw runtime exception
        }
        return rb[first];
        // Return the first item. None of your instance variables should change.
    }


    // When you get to part 5, implement the needed code to support iteration.
    public Iterator<T> iterator() {
        return new Blah();
    }

    private class Blah implements Iterator<T> {
        private int index;

        Blah() {
            index = 0;
        }

        public boolean hasNext() {
            return index < fillCount;
        }

        public T next() {
            T returnValue = rb[index];
            index += 1;
            return returnValue;
        }
    }

}

