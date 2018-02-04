public class ArrayDeque<T> {
    private T[] items;
    private int size;

    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T []) new Object[8];
        size = 0;

        nextFirst = 0;
        nextLast = 0;


    }


    //reorganizes deque for resizing
    private void reorganize() {
        T[] t = (T []) new Object[items.length];
        int pointerfirst = nextFirst + 1;
        if (pointerfirst > items.length) {
            pointerfirst = 0;
        }
        for (int i = 0; items.length > i; i++) {
            t[i] = items[pointerfirst];
            pointerfirst++;
            if (pointerfirst >= items.length) {
                pointerfirst = 0;
            }

        }
        this.items = t;

    }
    //resizes array
    //@source andrew helped me understand concept behind resizing
    private void resize(int capacity) {

        T[] a = (T []) new Object[capacity];
        reorganize();
        System.arraycopy(items, 0, a, 0, size);
        items = a;
        nextFirst = items.length - 1;
        nextLast = size;

    }

    public void addFirst(T x) {
        if (size == items.length) {
            resize(size * 2);
        }
        if (nextFirst < 0) {
            nextFirst = items.length - 1; //sets first element to the back of the array
        }
        items[nextFirst] = x;
        nextFirst--;
        size++;
    }



    public void addLast(T x) {
        if (size == items.length) {
            resize(size * 2);
        }
        if (nextLast == items.length) {
            nextLast = 0; //sets nextLast element to the front of the array
        }
        items[nextLast] = x;
        nextLast++;
        size++;
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    public void printDeque() {
        int startingpoint = nextFirst + 1;
        while (items.length != startingpoint) {
            System.out.print(items[startingpoint] + " ");
            startingpoint++;
            if (startingpoint == items.length) {
                startingpoint = 0;
            }
            if (startingpoint == nextFirst + 1) {
                break;
            }
        }
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        if (nextFirst >= items.length - 1) {
            nextFirst = 0;
            T x = items[nextFirst];
            items[nextFirst] = null;
            size--;
            nextFirst++;
            return x;
        }
        T x = items[nextFirst + 1];
        items[nextFirst + 1] = null;
        size--;
        nextFirst++;
        if (size < 0.25 * items.length && items.length >= 16) {
            resize(items.length / 2);
        }
        return x;
    }


    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        if (nextLast <= 0) {
            nextLast = items.length;
        }
        T x = items[nextLast - 1];
        items[nextLast - 1] = null;
        size--;
        nextLast--;
        if (size < 0.25 * items.length && items.length >= 16) {
            resize(items.length / 2);
        }
        return x;
    }


    public T get(int index) {
        int numberget = nextFirst + 1 + index; //formula for getting values
        if (index > items.length - 1 && index < 0) {
            return null;
        }
        if ((nextFirst + 1 + index) > items.length) {
            return items[numberget - items.length];
        }
        return items[nextFirst + 1 + index];
    }

    public int size() {
        return size;
    }

}
