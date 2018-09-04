public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> l = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            l.addLast(word.charAt(i));
        }
        return l;
    }
    public boolean isPalindrome(String word) {
        boolean result = false;
        if ((word.length() == 1) || (word.length() == 0)) {
            result = true;
        }
        String back_word = getBack(word);
        if (word.equals(back_word)) {
            result = true;
        }
        return result;
    }
    /** Verify whether the word is palindrome with every character different by exactly one. */
    //判断单词word是不是前面的字母和后面的字母总相差1的回文单词。
    public boolean isPalindrome(String word, CharacterComparator cc) {
        boolean result = false;
        if ((word.length() == 1) || (word.length() == 0)) {
            result = true;
        }
        String back_word = getBack(word);
        for (int i = 0; i < (word.length() / 2); i++) {
            result = cc.equalChars(back_word.charAt(i), word.charAt(i));
            if (result == true) {

            }
            else {
                return false;
            }
        }
        return result;
    }
    /** Turn the word upside down. */
    private String getBack(String word) {
        Deque<Character> l = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            l.addFirst(word.charAt(i));
        }
        String back_word = "";
        for (int i = 0; i < word.length(); i++) {
            back_word += l.removeFirst();
        }
        return back_word;
    }

}
