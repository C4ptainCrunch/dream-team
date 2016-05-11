package parser;

import constants.Models;
import models.tikz.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.SharedTest;

import java.awt.*;
import java.util.List;

/**
 * Created by jhellinckx on 26/04/16.
 */
public class TikzFormatterTest extends SharedTest {
    private TikzGraph graph;

    @Before
    public void setUp() throws Exception {
        graph = new TikzGraph();
    }

    @After
    public void tearDown() throws Exception {
    }

    private TikzShape getFirstShape() {
        List<TikzNode> nodes = graph.getNodes();
        return nodes.isEmpty() ? new TikzCircle() : (TikzShape) nodes.get(0);
    }

    private TikzEdge getFirstEdge() {
        List<TikzEdge> edges = graph.getEdges();
        return edges.isEmpty() ? new TikzDirectedEdge() : edges.get(0);
    }

    @Test
    public void testStringBackgroundColor() throws Exception{
        final Color testedColor = Color.red;
        TikzCircle circle = new TikzCircle();
        circle.setBackgroundColor(testedColor);
        String tikzSource = circle.toString();
        NodeParser.parseDocument(graph).parse(tikzSource);
        TikzCircle parsedCircle = (TikzCircle) getFirstShape();
        assertEquals(parsedCircle.getBackgroundColor(),testedColor);
    }

    @Test
    public void testStringStrokeWidth() throws Exception {
        final int testedStroke = 5;
        TikzCircle circle = new TikzCircle();
        circle.setStroke(testedStroke);
        String tikzSource = circle.toString();
        NodeParser.parseDocument(graph).parse(tikzSource);
        TikzCircle parsedCircle = (TikzCircle) getFirstShape();
        assertEquals(parsedCircle.getStroke(),testedStroke);
    }

    @Test
    public void testStringStrokeColor() throws Exception{
        final Color testedStrokeColor = Color.blue;
        TikzCircle circle = new TikzCircle();
        circle.setStrokeColor(testedStrokeColor);
        String tikzSource = circle.toString();
        NodeParser.parseDocument(graph).parse(tikzSource);
        TikzCircle parsedCircle = (TikzCircle) getFirstShape();
        assertEquals(parsedCircle.getStrokeColor(),testedStrokeColor);
    }

    @Test
    public void testStringRectangleBounds() throws Exception{
        final int testeWidth = 10;
        final int testedHeight = 15;
        TikzRectangle rectangle = new TikzRectangle();
        rectangle.setWidth(testeWidth);
        rectangle.setLength(testedHeight);
        String tikzSource = rectangle.toString();
        NodeParser.parseDocument(graph).parse(tikzSource);
        TikzRectangle parsedRectangle = (TikzRectangle) getFirstShape();
        assertEquals(parsedRectangle.getWidth(),testeWidth, 0.01);
        assertEquals(parsedRectangle.getLength(),testedHeight, 0.01);
    }

    @Test
    public void testStringCircleRadius() throws Exception{
        final int testedRadius = 123;
        TikzCircle circle = new TikzCircle();
        circle.setRadius(testedRadius);
        String tikzSource = circle.toString();
        NodeParser.parseDocument(graph).parse(tikzSource);
        TikzCircle parsedCircle = (TikzCircle) getFirstShape();
        assertEquals(parsedCircle.getRadius(),testedRadius, 0.01);
    }

    @Test
    public void testStringPolygonSides() throws Exception{
        final int numSidesTested = 100;
        TikzPolygon polygon = new TikzPolygon();
        polygon.setSides(numSidesTested);
        String tikzSource = polygon.toString();
        NodeParser.parseDocument(graph).parse(tikzSource);
        TikzPolygon parsedPolygon = (TikzPolygon) getFirstShape();
        assertEquals(parsedPolygon.getSides(),numSidesTested);
    }

    @Test
    public void testStringPolygonLength() throws Exception{
        final int testedLength = 66;
        TikzPolygon polygon = new TikzPolygon();
        polygon.setLength(testedLength);
        String tikzSource = polygon.toString();
        NodeParser.parseDocument(graph).parse(tikzSource);
        TikzPolygon parsedPolygon = (TikzPolygon) getFirstShape();
        assertEquals(parsedPolygon.getLength(),testedLength, 0.01);
    }

    @Test
    public void testStringDefaultOptions() throws Exception{
        TikzCircle circle = new TikzCircle();
        String tikzSource = circle.toString();
        NodeParser.parseDocument(graph).parse(tikzSource);
        TikzCircle parsedCircle = (TikzCircle) getFirstShape();
        assertEquals(parsedCircle.getRadius(), Models.DEFAULT.LENGTH);
        assertEquals(parsedCircle.getBackgroundColor(), Models.DEFAULT.BACKGROUND_COLOR);
        assertEquals(parsedCircle.getStrokeColor(), Models.DEFAULT.COLOR);
        assertEquals(parsedCircle.getStroke(), Models.DEFAULT.STROKE);
    }

    @Test
    public void testDirectedEdgeFormat() throws Exception{
        TikzDirectedEdge edge = new TikzDirectedEdge(new TikzCircle("3"), new TikzCircle("2"), "1");
        String edgeSource = edge.toString();
        NodeParser.parseDocument(graph).parse(edgeSource);
        TikzEdge parsedEdge = getFirstEdge();
        assertEquals(edge, parsedEdge);
    }
}
