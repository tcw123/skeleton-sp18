public class ArrayDequeTest {
    public static void main(String args[]) {
        ArrayDeque<Integer> ad= new ArrayDeque<>();
        ad.addFirst(1);
        ad.addFirst(2);
        ad.addLast(3);
        ad.removeFirst();
        ad.removeLast();
        ad.removeLast();
        System.out.println(ad.isEmpty());
    }
}
