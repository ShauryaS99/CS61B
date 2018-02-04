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

    private void resize(int capacity) {
        Obj[] a = (Obj []) new Object[capacity];
        System.arraycopy(items, 0, a, 0, size);
        items = a;

    }

    public void addFirst(Obj x){
        if (size == items.length) {
            resize(size*2);
        }
        if (nextFirst<0) {
            nextFirst = nextFirst + items.length-1; //sets first element to the back of the array
        }
        items[nextFirst] = x;
        nextFirst--;
        size++;
    }



    public void addLast(Obj x) {
        if (size == items.length) {
            resize(size *2);
        }
        if (nextLast==items.length) {
            nextLast = 0; //sets nextLast element to the front of the array
        }
        items[nextLast] = x;
        nextLast++;
        size++;
    }

    public boolean isEmpty(){
        if (size==0){
            return true;
        }
        return false;
    }

    public void printDeque(){
        int startingpoint = nextFirst;
        while(items.length != startingpoint) {
            System.out.print(items[startingpoint] + " ");
            startingpoint++;
            if (startingpoint == items.length) {
                startingpoint =0;
            }
            if (startingpoint == nextFirst) {
                break;
            }
        }
    }

    public Obj removeFirst(){
        if (isEmpty()){
            return null;
        }
        Obj x = items[nextFirst];
        size--;
        if (size < 0.25*items.length && items.length>=16) {
            resize(size/2);
        }
        return x;
    }

    public Obj getLast() {
        return items[size - 1];
    }

    public Obj get(int index) {
        
        return items[nextFirst+1+index];
    }

    public int size() {
        return size;
    }

    /** Deletes item from back of the list and
     * returns deleted item. */
    public Obj removeLast() {
        if (isEmpty()){
            return null;
        }
        Obj x = getLast();
        size--;
        if (size < 0.25*items.length && items.length>=16) {
            resize(size/2);
        }
        return x;
    }

    public static void main(String[] args){

    }

}
