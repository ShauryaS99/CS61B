import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testequalChars() {
        assertTrue(offByOne.equalChars('a','b'));
        assertTrue(offByOne.equalChars('r','q'));
        assertTrue(offByOne.equalChars('&','%'));
    }

    @Test
    public void testnotequalChars() {
        assertFalse(offByOne.equalChars('w','r'));
        assertFalse(offByOne.equalChars('q','b'));
        assertFalse(offByOne.equalChars('a','B'));
    }


}
