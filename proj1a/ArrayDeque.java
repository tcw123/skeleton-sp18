public class ArrayDeque<T> {
    public T[] items;
    public int size;
    public static double usage_ratio;
    public ArrayDeque() {
        items = (T []) new Object[8];
        size = 0;
    }
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
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        else {
            return false;
        }
    }
    public int size() {
        return size;
    }
    public void printDeque() {
        for (int i = 0; i < size; i++) System.out.print(items[i] + " ");
    }

    public T removeFirst() {
        T x = items[0];
        System.arraycopy(items, 1, items, 0, size - 1);
        items[size - 1] = null;
        size -= 1;
        checkR();
        return x;
    }
    public T removeLast() {
        T x = items[size - 1];
        items[size - 1] = null;
        size -= 1;
        checkR();
        return x;
    }
    public T get(int index) {
        T x = items[index];
        return x;
    }
    public void checkR() {
        usage_ratio = size / items.length;
        if ((usage_ratio < 0.25) && (items.length >= 16)) {
            T[] a = (T []) new Object[size / 2];
            System.arraycopy(items, 0 ,a, 0, size);
            items = a;
        }
    }



}
