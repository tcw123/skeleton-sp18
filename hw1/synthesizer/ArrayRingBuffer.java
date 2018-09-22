package synthesizer;
import java.util.Iterator;


public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    private int first;
    private int last;
    private T[] rb;

    @Override
    public Iterator<T> iterator() {
        return new KeyIterator();
    }

    public class KeyIterator implements Iterator<T> {
        private int ptr;
        public KeyIterator() {
            ptr = 0;
        }
        public boolean hasNext() {
            return (ptr != capacity);
        }
        public T next() {
            T tmp = rb[ptr];
            ptr += 1;
            return tmp;
        }

    }



    public ArrayRingBuffer(int capacity) {
        first = 0;
        last = 0;
        fillCount = 0;
        this.capacity = capacity;
        rb = (T []) new Object[capacity];
    }

    @Override
    public T peek() {
            return rb[first];
    }

    @Override
    public T dequeue() {
        if (fillCount == 0) {
            throw new RuntimeException("Ring buffer overflow");
        }
        T tmp = rb[first];
        rb[first] = null;
        if (first == rb.length - 1) {
            first = 0;
        }
        else {
            first += 1;
        }
        fillCount -= 1;
        return tmp;
    }
    @Override
    public void enqueue(T x) {
        if (fillCount == capacity) {
            throw new RuntimeException("Ring buffer overflow");
        }
        rb[last] = x;
        if (last == rb.length - 1) {
            last = 0;
        }
        else {
            last += 1;
        }
        fillCount += 1;
    }

}
