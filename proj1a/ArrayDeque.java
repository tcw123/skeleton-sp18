public class ArrayDeque<T> {
    private T[] items;
    private int size;
    /** Creat an empty list the starting length of the list is 8. */
    public ArrayDeque() {
        items = (T []) new Object[8];
        size = 0;
    }
    /** Add an item to the front of the list. */
    public void addFirst(T i) {
        size += 1;
        if (size == items.length) {
            T[] a = (T []) new Object[size * 2];
            System.arraycopy(items, 0, a, 1, size);
            items = a;
            items[0] = i;
        }
        else {
            System.arraycopy(items, 0, items, 1, size);
            items[0] = i;
        }
    }
    /** Add an item to the back of the list. */
    public void addLast(T i) {
        if (size == items.length) {
            T[] a = (T []) new Object[size * 2];
            System.arraycopy(items, 0, a, 0, size);
            items = a;
            items[size] = i;
        }
        else {
            items[size] = i;
        }
        size += 1;
    }
    /** Judge whether the list is empty, if empty returns true, else returns false. */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        else {
            return false;
        }
    }
    /** return the size of the list. */
    public int size() {
        return size;
    }
    /** print out the all items in the list. */
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(items[i] + " ");
        }
    }
    /** remove the first item of the list. */
    public T removeFirst() {
        T x = items[0];
        System.arraycopy(items, 1, items, 0, size - 1);
        items[size - 1] = null;
        size -= 1;
        checkR();
        return x;
    }
    /** Remove the last item of the list. */
    public T removeLast() {
        T x = items[size - 1];
        items[size - 1] = null;
        size -= 1;
        checkR();
        return x;
    }
    /** Get the item an position index. */
    public T get(int index) {
        T x = items[index];
        return x;
    }
    /** Judge whether necessary to shrink the list. */
    private void checkR() {
        double usage_ratio = size / items.length;
        if ((usage_ratio < 0.25) && (items.length >= 16)) {
            T[] a = (T []) new Object[items.length / 2];
            System.arraycopy(items, 0 ,a, 0, size);
            items = a;
        }
    }



}
