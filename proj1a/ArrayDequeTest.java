public class ArrayDequeTest {
    public static void main(String args[]) {
        ArrayDeque<Integer> ad= new ArrayDeque<>();
        ad.addLast(0);
        ad.addLast(1);
        ad.addLast(2);
        ad.addLast(3);
        System.out.println(ad.size());
        ad.addFirst(5);
        ad.addLast(6);
        ad.addLast(7);
        ad.addLast(8);
        ad.addFirst(9);

    }
}
