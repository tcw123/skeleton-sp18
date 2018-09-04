import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();
    static CharacterComparator offByOne = new OffByOne();
    static CharacterComparator offByN = new OffByN(5);


    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        String word1 = "cat";
        String word2 = "aba";
        String word3 = "Aba";
        assertFalse(palindrome.isPalindrome(word1));
        assertTrue(palindrome.isPalindrome(word2));
        assertFalse(palindrome.isPalindrome(word3));
    }
    @Test
    public void testPalindrome2() {
        assertTrue(palindrome.isPalindrome("flake", offByOne));
        System.out.println(palindrome.isPalindrome("flakf", offByOne));
    }
    
    @Test
    public void testPalindrome3() {
        String word = "abcdejihgf";
        boolean result = palindrome.isPalindrome(word, offByN);
        assertTrue(result);
        System.out.println(result);
    }

}
