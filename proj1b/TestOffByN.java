import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {

    static CharacterComparator offByN = new OffByN(3);

    @Test
    public void testequalChars() {
        assertTrue(offByN.equalChars('a', 'd'));
        assertTrue(offByN.equalChars('e', 'b'));
    }

    @Test
    public void testnotequalChars() {
        assertFalse(offByN.equalChars('w', 'r'));
        assertFalse(offByN.equalChars('q', 'b'));
    }



}
