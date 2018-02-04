public class LinkedListDeque<Gen> {
    //make sure to implement generic structures
    private class Node{
        public Node prev;
        public Gen item;
        public Node next;

        public Node(Node p, Gen i, Node n){
            item =i;
            next =n;
            prev = p;
        }
    }

    private Node sentinel;
    private int size;

    /**empty circular sentinel node*/
    public LinkedListDeque(){
        sentinel = new Node(sentinel,null,sentinel);
        size = 0;
    }

    /**DLList with one element
     public LinkedListDeque(int x){
     sentinel = new IntNode(sentinel,1,sentinel);
     sentinel.next = new IntNode(sentinel.prev, x, null);
     sentinel.prev = new IntNode(null,x, sentinel.next);
     size = 1;
     }*/
    /** Adds x to the front of the list. */
    public void addFirst(Gen x) {

        if(size<1){

            sentinel.next = new Node(sentinel.prev, x, sentinel.next);
            sentinel.prev = sentinel.next;
            sentinel.prev.next= sentinel;
            sentinel.prev.prev= sentinel.next.next;

        }
        else{
            sentinel.next = new Node(sentinel.prev, x, sentinel.next);
            sentinel.next.next.prev = sentinel.next;
            sentinel.next.prev = sentinel.next;
        }
        size += 1;
    }

    /** Returns the first item in the list. */
    public Gen getFirst() {
        return sentinel.next.item;
    }

    /** Adds an item to the end of the list. */
    public void addLast(Gen x) {
        if (size<1){
            sentinel.next = new Node(sentinel.prev, x, sentinel.next);
            sentinel.prev = sentinel.next;
            sentinel.prev.next= sentinel;
            sentinel.prev.prev= sentinel.next.next;
        }

        else if (size<2){
            sentinel.prev = new Node(sentinel.next, x, sentinel);
            sentinel.next.next = sentinel.prev;

        }
        else {
            sentinel.prev = new Node(sentinel.prev, x, sentinel);
            sentinel.prev.prev.next = sentinel.prev;
            sentinel.prev.prev = sentinel.prev.prev.prev.next;
        }
        size += 1;
        /**IntNode p = sentinel;

         //Move p until it reaches the end of the list.
        while (p.next != null) {
            if (p.item == p.next.item){ //if items are the same will fail
                break;
            }

            p = p.next;

        }
        p.prev = new IntNode(sentinel.prev, x,sentinel.next);
        p.next = new IntNode(sentinel.prev, x, sentinel); */
    }

    /**public int getLast(){
     IntNode p = sentinel;
     while (p.next != null) {
     p = p.next;
     }

     return p.next;
     }*/
    public  boolean isEmpty(){
        if (size==0){
            return true;
        }return false;
    }
    public int size() {
        return size;
    }
    public void printDeque(){ //might be flagged b/c it is destructive
        Node sentinelcopy = sentinel;
        while(size!=0){
            System.out.print(sentinelcopy.next.item + " ");
            sentinelcopy = sentinelcopy.next;
            size--;
        }
    }

    public Gen removeFirst(){
        if (isEmpty()){
            return null;

        }
        else{
            Gen x = sentinel.next.item;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size--;
            return x;
        }
    }

    public Gen removeLast(){
        if (isEmpty()){
            return null;

        }
        else{
            Gen x = sentinel.prev.item;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next= sentinel;
            size--;
            return x;
        }
    }

    public Gen get(int index){
        int x =0;
        Node sentinelcopy = sentinel;
        if (index>= size || index<0){
            return null;
        }

        else{
            while( x <index){
                sentinelcopy = sentinelcopy.next;
                x++;

            }
            return sentinelcopy.next.item;

        }
    }


    public Gen getRecursive(int index){

        Node sentinelcopy = sentinel;
        return helper(sentinelcopy.next,index);

    }

    public Gen helper(Node sentinelcopy,int index){
        if (index >= size || index < 0) {
            return null;
        }
        else if(index ==0){
            return sentinelcopy.item;
        }
        return helper(sentinelcopy.next,index-1);
    }

    public static void main(String[] args){
        LinkedListDeque a = new LinkedListDeque();
        a.addFirst(21);
        a.addFirst(20);
        a.addFirst(19);
        a.addFirst(18);
        System.out.println(a.getRecursive(2));
    }
}
