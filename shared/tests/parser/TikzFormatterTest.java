package parser;

import junit.framework.Assert;
import models.tikz.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by jhellinckx on 26/04/16.
 */
public class TikzFormatterTest {
    private TikzGraph graph;
    @Before
    public void setUp() throws Exception {
        TikzGraph graph = new TikzGraph();
    }

    @After
    public void tearDown() throws Exception {

    }

    private TikzShape getFirstShape() {
        List<TikzNode> nodes = graph.getNodes();
        return nodes.isEmpty() ? new TikzCircle() : (TikzShape) nodes.get(0);
    }

    @Test
    public void testStringBackgroundColor() throws Exception{
        final Color testedColor = Color.red;
        TikzCircle circle = new TikzCircle();
        circle.setBackgroundColor(testedColor);
        String tikzSource = circle.toString();
        NodeParser.nodeFromNode(graph).parse(tikzSource);
        TikzCircle parsedCircle = (TikzCircle) getFirstShape();
        Assert.assertEquals(parsedCircle.getBackgroundColor(),testedColor);
    }

    @Test
    public void testStringStrokeWidth() throws Exception {
        final int testedStroke = 5;
        TikzCircle circle = new TikzCircle();
        circle.setStroke(testedStroke);
        String tikzSource = circle.toString();
        NodeParser.nodeFromNode(graph).parse(tikzSource);
        TikzCircle parsedCircle = (TikzCircle) getFirstShape();
        Assert.assertEquals(parsedCircle.getStroke(),testedStroke);
    }

    @Test
    public void testStringStrokeColor() throws Exception{
        final Color testedStrokeColor = Color.blue;
        TikzCircle circle = new TikzCircle();
        circle.setStrokeColor(testedStrokeColor);
        String tikzSource = circle.toString();
        NodeParser.nodeFromNode(graph).parse(tikzSource);
        TikzCircle parsedCircle = (TikzCircle) getFirstShape();
        Assert.assertEquals(parsedCircle.getStrokeColor(),testedStrokeColor);
    }

    @Test
    public void testStringRectangleBounds() throws Exception{

    }

    @Test
    public void testStringCircleRadius() throws Exception{

    }

    @Test
    public void testStringPolygonSides() throws Exception{

    }

    @Test
    public void testStringPolygonLength() throws Exception{

        
    }

    @Test
    public void testStringDefaultOptions() throws Exception{

    }
}
