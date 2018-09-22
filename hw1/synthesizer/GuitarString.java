package synthesizer;


import java.util.ArrayList;

public class GuitarString {
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor
    private BoundedQueue<Double> buffer;
    private ArrayList<Double> list = new ArrayList<>();

    public GuitarString(double frequency) {
        buffer = new ArrayRingBuffer<>((int) Math.round(SR / frequency));
        while (buffer.fillCount() != buffer.capacity()) {
            buffer.enqueue(0.0);
        }
    }

    public void pluck() {
        while (buffer.fillCount() != 0) {
            buffer.dequeue();
        }
        while (buffer.fillCount() != buffer.capacity()) {
            double tmp = Math.random() - 0.5;
            if (!list.contains(tmp)) {
                buffer.enqueue(tmp);
                list.add(tmp);
            }
        }
    }
    public void tic() {
        double tmp = buffer.dequeue();
        double tmp2 = buffer.peek();
        double tmp3 = DECAY * ((tmp + tmp2) / 2.0);
        buffer.enqueue(tmp3);
    }

    public double sample() {
        return buffer.peek();
    }


}
