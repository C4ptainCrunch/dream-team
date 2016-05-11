package utils;

import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by bambalaam on 25/04/16.
 */
public class TokenCreatorTest {

    ArrayList<String> tokens;
    TokenCreator tc;

    @Before
    public void setUp() throws Exception {
        tokens = new ArrayList<>();
        for(int i=0;i<200;i++){
            tokens.add(TokenCreator.newToken());
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
