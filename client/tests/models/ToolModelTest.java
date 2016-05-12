package models;

import misc.utils.ClientTest;
import models.tikz.TikzCircle;
import models.tikz.TikzComponent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;


public class ToolModelTest extends ClientTest {

    private TikzComponent component;
    private ToolModel model;
    private TikzComponent other_component;

    @Before
    public void setUp() throws Exception{
        component = new TikzCircle();
        model = new ToolModel(component);
    }

    @After
    public void tearDown() throws Exception{

    }

    @Test
    public void testSetComponentLabel() throws Exception {
        model.setComponentLabel("test");
        other_component = model.getComponentClone();
        assertEquals(component.getLabel(), other_component.getLabel());
    }

    @Test
    public void testSetComponentColor() throws Exception {
        model.setComponentColor(Color.BLACK);
        other_component = model.getComponentClone();
        assertEquals(component.getStrokeColor(), other_component.getStrokeColor());
    }

    @Test
    public void testReset() throws Exception {
        model.reset();
        assertEquals(model.getComponentClone(), null);
    }

    @Test
    public void testSetComponentStrokeWidth() throws Exception {
        model.setComponentStrokeWidth(5);
        other_component = model.getComponentClone();
        assertEquals(other_component.getStroke(), component.getStroke());
    }
}