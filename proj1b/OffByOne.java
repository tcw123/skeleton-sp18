public class OffByOne implements CharacterComparator {
    @Override public boolean equalChars(char x, char y) {
        boolean result = false;
        if (Math.abs(x - y) == 1) {
            result = true;
        }
        return result;
    }
}
