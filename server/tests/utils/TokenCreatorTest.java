package utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by bambalaam on 25/04/16.
 */
public class TokenCreatorTest {

    ArrayList<String> tokens;
    TokenCreator tc;

    @Before
    public void setUp() throws Exception {
        tokens = new ArrayList<>();
        tc = new TokenCreator();
        for(int i=0;i<200;i++){
            tokens.add(tc.newToken());
        }
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testRandomness() throws Exception {
        assertFalse(new HashSet<>(tokens).size() != tokens.size());
    }
}
