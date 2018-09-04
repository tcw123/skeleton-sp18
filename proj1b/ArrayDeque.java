public class ArrayDeque<Item> implements Deque<Item> {
    private Item[] items;
    private int size;
    /** Creat an empty list the starting length of the list is 8. */
    public ArrayDeque() {
        items = (Item []) new Object[8];
        size = 0;
    }

    /** Add an item to the front of the list. */
    @Override public void addFirst(Item i) {
        if (size == items.length) {
            Item[] a = (Item []) new Object[size * 4];
            System.arraycopy(items, 0, a, 1, size);
            items = a;
            items[0] = i;
        }
        else {
            System.arraycopy(items, 0, items, 1, size);
            items[0] = i;
        }
        size += 1;
    }
    /** Add an item to the back of the list. */
    @Override public void addLast(Item i) {
        if (size == items.length) {
            Item[] a = (Item []) new Object[size * 4];
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
    @Override public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        else {
            return false;
        }
    }
    /** return the size of the list. */
    @Override public int size() {
        return size;
    }
    /** print out the all items in the list. */
    @Override public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(items[i] + " ");
        }
    }
    /** remove the first item of the list. */
    @Override  public Item removeFirst() {
        if (size == 0) {
            return null;
        }
        else {
            Item x = items[0];
            System.arraycopy(items, 1, items, 0, size - 1);
            items[size - 1] = null;
            size -= 1;
            checkR();
            return x;
        }
    }
    /** Remove the last item of the list. */
    @Override public Item removeLast() {
        if (size == 0) {
            return null;
        }
        else {
            Item x = items[size - 1];
            items[size - 1] = null;
            size -= 1;
            checkR();
            return x;
        }
    }
    /** Get the item an position index. */
    @Override public Item get(int index) {
        Item x = items[index];
        return x;
    }
    /** Judge whether necessary to shrink the list. */
    private void checkR() {
        double usage_ratio = (size * 1.0) / items.length;
        if ((usage_ratio < 0.25) && (items.length >= 16)) {
            Item[] a = (Item []) new Object[items.length / 2];
            System.arraycopy(items, 0 ,a, 0, size);
            items = a;
        }
    }
}
