public class OffByN implements CharacterComparator {
    private int n;
    public OffByN(int N) {
        n = N;
    }
    @Override public boolean equalChars(char x, char y) {
        boolean result = false;
        if (Math.abs(x - y) == n) {
            result = true;
        }
        return result;
    }
}
