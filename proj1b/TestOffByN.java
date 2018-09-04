import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    static CharacterComparator offByN = new OffByN(5);
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testPalindrome3() {
        String word = "abcdejihgf";
        boolean result = palindrome.isPalindrome(word, offByN);
        assertTrue(result);
        System.out.println(result);
    }


}
