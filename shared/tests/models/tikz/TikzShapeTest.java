package models.tikz;

import org.junit.After;
import org.junit.Before;

/**
 * Created by etnarek on 2/29/16.
 */
public class TikzShapeTest {

    TikzShape shape;

    @Before
    public void setUp() throws Exception {
        shape = new TikzCircle();
    }

    @After
    public void tearDown() throws Exception {

    }
}
