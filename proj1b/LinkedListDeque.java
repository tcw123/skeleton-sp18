public class LinkedListDeque<Item> implements Deque<Item> {
    private class Node {
        Item item;
        Node prev;
        Node next;
        public Node(Item i, Node p, Node n) {
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
    @Override public void addFirst(Item i) {
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
    @Override public void addLast(Item i) {
        size += 1;
        sentinel.prev.next = new Node(i, sentinel.prev, sentinel);
        sentinel.prev = sentinel.prev.next;
    }
    /** Returns true if deque is empty, false otherwise. */
    @Override public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        else {
            return false;
        }
    }
    /** Returns true if deque is empty, false otherwise. */
    @Override public int size() {
        return size;
    }
    /** Prints the items in the deque from first to last, separated by a space. */
    @Override public void printDeque() {
        Node temp = sentinel.next;
        for (int i = 0; i < size; i++) {
            System.out.print(temp.item + " ");
            temp = temp.next;
        }
    }
    /** Removes and returns the item at the front of the deque.
     *  If no such item exists, returns null. */
    @Override public Item removeFirst() {
        Item i;
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
    @Override public Item removeLast() {
        Item i;
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

    @Override public Item get(int index) {
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

    public Item getRecursive(int index) {
        find = find.next;
        if (index > size -1) {
            return null;
        }
        else if (index == 0) {
            Node temp = find;
            find = sentinel;
            return temp.item;
        }

        else {
            return getRecursive(index - 1);
        }
    }
}
