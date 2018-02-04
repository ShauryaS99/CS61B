public class ArrayDeque<Obj> {
    private Obj[] items;
    private int size;

    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (Obj []) new Object[8];
        size = 0;

        nextFirst = 0;
        nextLast = 0;


    }


    //reorganizes deque for resizing
    public void reorganize() {
        Obj[] t = (Obj []) new Object[items.length];
        int pointerfirst = nextFirst + 1;
        for (int i = 0; items.length > i; i++) {
            t[i] = items[pointerfirst];
            pointerfirst++;
            if (pointerfirst == items.length) {
                pointerfirst = 0;
            }

        }
        this.items = t;

    }
    //resizes array
    //@source andrew helped me understand concept behind resizing
    private void resize(int capacity) {
        Obj[] a = (Obj []) new Object[capacity];
        reorganize();
        System.arraycopy(items, 0, a, 0, size);
        items = a;
        nextFirst = items.length - 1;
        nextLast = size;

    }

    public void addFirst(Obj x) {
        if (size == items.length) {
            resize(size * 2);
        }
        if (nextFirst < 0) {
            nextFirst = nextFirst + items.length - 1; //sets first element to the back of the array
        }
        items[nextFirst] = x;
        nextFirst--;
        size++;
    }



    public void addLast(Obj x) {
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

    public Obj removeFirst() {
        if (isEmpty()) {
            return null;
        }
        Obj x = items[nextFirst];
        items[nextFirst] = null;
        size--;
        nextFirst++;
        if (size < 0.25 * items.length && items.length >= 16) {
            resize(items.length / 2);
        }
        if (nextFirst > items.length) {
            nextFirst = nextFirst - items.length;
        }
        return x;
    }


    public Obj removeLast() {
        if (isEmpty()) {
            return null;
        }
        Obj x = getLast();
        size--;
        nextLast--;
        if (size < 0.25 * items.length && items.length >= 16) {
            resize(items.length / 2);
        }
        if (nextLast == 0) {
            nextLast = items.length - 1;
        }
        return x;
    }

    public Obj getLast() {
        return items[size - 1];
    }

    public Obj get(int index) {
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


    public static void main(String[] args) {

    }

}
