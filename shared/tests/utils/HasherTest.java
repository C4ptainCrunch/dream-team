package utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class HasherTest {
    private static final String HASH = "$p5k2$3e8$longlongsalt$-1a89558e9b6542993789d3c844422f818ad5732bd957c441";

    @Test
    public void isEqual() throws Exception {
        assertTrue(Hasher.isEqual(HASH, "My Passw0rd"));
    }

    @Test
    public void isEqualDifferentPass() throws Exception {
        assertFalse(Hasher.isEqual(HASH, "My Passw0rd2"));
    }

    @Test
    public void skipStatic() throws Exception {
        assertEquals(Hasher.skipStatic(HASH), "longlongsalt$-1a89558e9b6542993789d3c844422f818ad5732bd957c441");
    }

    @Test
    public void extractSalt() throws Exception {
        assertEquals(Hasher.extractSalt(HASH), "longlongsalt");
    }

    @Test
    public void extractHmac() throws Exception {
        assertEquals(Hasher.extractHmac(HASH), "-1a89558e9b6542993789d3c844422f818ad5732bd957c441");
    }

    @Test
    public void hash() throws Exception {
        assertEquals(
                Hasher.hash("My Passw0rd", "longlongsalt"),
                HASH
        );
    }

    @Test
    public void hashDifferentSalt() throws Exception {
        assertNotEquals(
                Hasher.hash("My Passw0rd", "longlongsalt"),
                Hasher.hash("My Passw0rd", "shortsalt")
        );
    }

    @Test
    public void hashDifferentPass() throws Exception {
        assertNotEquals(
                Hasher.hash("My Passw0rd", "longlongsalt"),
                Hasher.hash("My Passw0rd1", "longlongsalt")
        );
    }



}
