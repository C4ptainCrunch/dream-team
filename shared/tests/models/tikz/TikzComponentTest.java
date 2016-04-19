package models.tikz;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static java.awt.Color.black;
import static org.junit.Assert.*;

/**
 * Created by jhellinckx on 29/02/16.
 */
public class TikzComponentTest {
    private TikzComponent tikzComponent;

    private String label;

    @Before
    public void setUp() throws Exception {
        tikzComponent = new TikzRectangle();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testColor() throws Exception {
        tikzComponent.setColor(black);
        assertEquals(tikzComponent.getColor(), black);
    }


    @Test
    public void testLabel() throws Exception {
        String label = "slt";
        tikzComponent.setLabel(label);
        assertEquals(tikzComponent.getLabel(), label);
    }

    @Test
    public void testGetReference() throws Exception {
        TikzRectangle rectangle = new TikzRectangle();
        TikzCircle circle = new TikzCircle();
        TikzEdge edge = new TikzDirectedEdge(rectangle, circle);

        assertNotEquals(rectangle.getReference(), circle.getReference());
        assertNotEquals(edge.getReference(), circle.getReference());
        assertNotEquals(edge.getReference(), rectangle.getReference());
    }

    @Test
    public void testGetReferenceWithDefaultConstructor() throws Exception {
        String reference = "My reference a moi";
        TikzPolygon polygon = new TikzPolygon(reference);
        assertEquals(polygon.getReference(), reference);
    }
}
