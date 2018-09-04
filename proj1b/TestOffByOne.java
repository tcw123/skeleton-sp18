import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();
    static Palindrome palindrome = new Palindrome();


    // Your tests go here.
    @Test
    public void testEqualChars() {
        assertTrue(offByOne.equalChars('b', 'b'));
        System.out.println(offByOne.equalChars('x', 'y'));
    }
    
}
