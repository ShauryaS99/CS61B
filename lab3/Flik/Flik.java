import org.junit.Test;

import static org.junit.Assert.*;

/** An Integer tester created by Flik Enterprises. */
public class Flik {
    public static boolean isSameNumber(Integer a, Integer b) {
        return a == b;
    }

    @Test
    public void testHorribleSteve() {
        String expected = "i is " + 500;
        org.junit.Assert.assertEquals(expected, HorribleSteve.class);
    }
}
