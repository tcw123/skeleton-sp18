public class LinkedListDeque<T> {
    private class Node {
        public T item;
        public Node prev;
        public Node next;
        public Node(T i, Node p, Node n) {
            item = i;
            prev = p;
            next = n;
        }
    }
    private int size;
    private Node sentinel;
    private Node find;
    /** creat a empty linked list deque. */
    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        find = sentinel;
    }
    /** Adds an item of type T to the front of the deque. */
    public void addFirst(T i) {
        size += 1;
        sentinel.next = new Node(i, sentinel, sentinel.next);
        if (size == 1) {
            sentinel.prev = sentinel.next;
            sentinel.next.next = sentinel;
        }
        else {
            sentinel.next.next.prev = sentinel.next;
        }
    }
    /** Adds an item of type T to the front of the deque. */
    public void addLast(T i) {
        size += 1;
        sentinel.prev.next = new Node(i, sentinel.prev, sentinel);
        sentinel.prev = sentinel.prev.next;
    }
    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        else {
            return false;
        }
    }
    /** Returns true if deque is empty, false otherwise. */
    public int size() {
        return size;
    }
    /** Prints the items in the deque from first to last, separated by a space. */
    public void printDeque() {
        Node temp = sentinel.next;
        for (int i = 0; i < size; i++) {
            System.out.print(temp.item+" ");
            temp = temp.next;
        }
    }
    /** Removes and returns the item at the front of the deque.
     *  If no such item exists, returns null. */
    public T removeFirst() {
        T i;
        if (size == 0) {
            return null;
        } else {
            i = sentinel.next.item;
            size -= 1;
            sentinel.next.next.prev = sentinel;
            sentinel.next = sentinel.next.next;
        }
        return i;
    }

    /** Removes and returns the item at the back of the deque.
     * If no such item exists, returns null. */
    public T removeLast() {
        T i;
        if (size == 0) {
            return null;
        } else {
            i = sentinel.prev.item;
            size -= 1;
            sentinel.prev.prev.next = sentinel;
            sentinel.prev = sentinel.prev.prev;
        }
        return i;
    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque! */

     public T get(int index) {
        Node temp = sentinel.next;
        if (index > size-1) {
            return null;
        }
        else {
            for (int i = 0; i < index; i++) {
                temp = temp.next;
            }
            return temp.item;
        }
    }

    public T getRecursive(int index) {
         find = find.next;
         if (index > size -1) {
             return null;
         }
         else if (index == 0){
             return find.item;
         }
         else {
             return getRecursive(index - 1);
         }
    }
}
