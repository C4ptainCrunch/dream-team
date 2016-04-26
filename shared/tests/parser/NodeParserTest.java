package parser;

import java.awt.*;
import java.util.HashMap;

import models.tikz.TikzGraph;

import models.tikz.TikzNode;
import models.tikz.TikzRectangle;
import models.tikz.TikzShape;
import org.junit.Assert;
import org.junit.Test;

public class NodeParserTest {
    @Test
    public void testDecimal() throws Exception {
        Assert.assertEquals(NodeParser.decimal().parse("-457"), new Integer(-457));
        Assert.assertEquals(NodeParser.decimal().parse("457"), new Integer(457));
        Assert.assertEquals(NodeParser.decimal().parse("-457.97"), new Integer(-458));
        Assert.assertEquals(NodeParser.decimal().parse("457.97"), new Integer(458));
    }

    @org.junit.Test
    public void testReference() throws Exception {
        Assert.assertEquals(NodeParser.reference().parse("(hello_world)"), "hello_world");
    }

    @org.junit.Test
    public void testLabel() throws Exception {
        Assert.assertEquals(NodeParser.label().parse("{Hello, my dear little world}"), "Hello, my dear little world");
    }

    @Test
    public void testOptionsParser() throws Exception {
        final HashMap<String, String> parse = NodeParser.optionsParser().parse("[hello, my,name, is,   trololo = 421]");
        Assert.assertEquals(parse.size(), 5);
        Assert.assertEquals(parse.get("hello"), "");
        Assert.assertEquals(parse.get("trololo"), "421");
    }

    @Test
    public void testCoordinates() throws Exception {
        Assert.assertEquals(NodeParser.coordinates().parse("(5,4)"), new Point(5, 4));
    }

    @Test
    public void testNodeFromDraw() throws Exception {
        Assert.assertEquals(NodeParser.nodeFromDraw().parse("(0,0) node [draw] {a}").toString(),
                "Coordinates: java.awt.Point[x=0,y=0], Options: {draw=}, Label: a");
        Assert.assertEquals(NodeParser.nodeFromDraw().parse("(0,0) node {a}").toString(),
                "Coordinates: java.awt.Point[x=0,y=0], Options: {}, Label: a");
        Assert.assertEquals(NodeParser.nodeFromDraw().parse("(0,0) node[circle split, hello] {}").toString(),
                "Coordinates: java.awt.Point[x=0,y=0], Options: {hello=, circle split=}, Label: ");
    }

    @Test
    public void testNodeFromNode() throws Exception {
        TikzGraph graph = new TikzGraph();
        NodeParser.nodeFromNode(graph).parse("\\node[lolilol, draw, rectangle](nametest) at (15,46)");
        NodeParser.nodeFromNode(graph).parse("\\node[lolilol, regular polygon]() at (-15,46)");
        NodeParser.nodeFromNode(graph).parse("\\node[rectangle]() at (250,-200){Demo Label}");
        NodeParser.nodeFromNode(graph).parse("\\node[rectangle]() at (-250,200){}");
        NodeParser.nodeFromNode(graph).parse("\\node[circle]() at (250,200) {Demo Label}");
        Assert.assertEquals(graph.size(), 5);
    }

    @Test
    public void testNodesFromDraw() throws Exception {
        TikzGraph graph = new TikzGraph();
        NodeParser.nodesFromDraw(graph).parse("\\draw[rectangle] (0,0) node[] {a} -- (2,0) node[] {l} -- (0,5) node[draw] {p}");
        Assert.assertEquals(graph.size(), 3);
    }

    @Test
    public void testEdgesFromDraw() throws Exception {
        TikzGraph graph = new TikzGraph();
        graph.add(new TikzRectangle("a"));
        graph.add(new TikzRectangle("b"));
        graph.add(new TikzRectangle("c"));
        NodeParser.edgesFromDraw(graph).parse("\\draw[color=blue, ->] (a) -- (b) -- (c) -- (a)");
        Assert.assertEquals(graph.getEdges().size(),  3);
    }

    @Test
    public void testParseTexDocument() throws Exception {
        TikzGraph graph = new TikzGraph();
        NodeParser.parseTeXDocument(graph)
                .parse("\\documentclass{article}\n" + "\\usepackage{tikz}\n" + "\\begin{document}\n" + "\t\\begin{tikzpicture}\n" + "\n"
                        + "\t\t\\draw[color=red, fill=red, rectangle, minimum width=10, minimum height=25] (0,0) node[draw] {a} -- (2,0) node[draw] {l} -- (0,5) node[draw] {p};\n" + "\n"
                        + "\t\\end{tikzpicture}\n" + "\\end{document}");
        Assert.assertEquals(graph.size(), 3);
        for(TikzNode node : graph.getNodes()){
            TikzShape shape = (TikzShape) node;
            Assert.assertEquals(shape.isRectangle(), true);
            TikzRectangle rectangle = (TikzRectangle) shape;
            Assert.assertEquals(rectangle.getWidth(), 10);
            Assert.assertEquals(rectangle.getLength(), 25);
            Assert.assertEquals(rectangle.getBackgroundColor(), Color.red);
        }

    }

    @Test
    public void testParseDocument() throws Exception {
        TikzGraph graph = new TikzGraph();
        NodeParser.parseDocument(graph)
                .parse("\t\t\\draw[color=red, line width=3, circle] (0,0) node[draw] {a} -- (2,0) node[draw] {l} -- (0,5) node[draw] {p};\n");
        Assert.assertEquals(graph.size(), 3);
        for(TikzNode node : graph.getNodes()){
            Assert.assertEquals(node.getStrokeColor(),Color.red);
            Assert.assertEquals(node.isShape(), true);
            Assert.assertEquals(node.getStroke(), 3);
        }
    }

    @Test
    public void testParseMinimalToString() throws Exception {
        String text = "\\node[rectangle]() at (250,200){Demo Label};";
        TikzGraph graph = new TikzGraph();
        NodeParser.parseDocument(graph).parse(text);
        Assert.assertEquals(graph.size(), 1);
    }

    @Test
    public void testParseMinimal() throws Exception {
        String text = "\\draw[color=red] (0,0) node[rectangle] {a};";
        TikzGraph graph = new TikzGraph();
        NodeParser.parseDocument(graph).parse(text);
        Assert.assertEquals(graph.size(), 1);
    }

    @Test
    public void testParseMinimalWithWhitespace() throws Exception {
        String text = "\\draw[color=red] (0,0) node[draw, rectangle] {a};\n \n \t";
        TikzGraph graph = new TikzGraph();
        NodeParser.parseDocument(graph).parse(text);
        Assert.assertEquals(graph.size(), 1);
    }

    @Test
    public void testAnOption() throws Exception {
        Assert.assertEquals(NodeParser.anOption().parse("helloworld"), "helloworld");
        Assert.assertEquals(NodeParser.anOption().parse("hello=world"), "hello=world");
        Assert.assertEquals(NodeParser.anOption().parse("hello=35"), "hello=35");
        Assert.assertEquals(NodeParser.anOption().parse("hello=54cm"), "hello=54cm");
        Assert.assertEquals(NodeParser.anOption().parse("this is an argument=54.5"), "this is an argument=54.5");
    }

    @Test
    public void testParseDocumentIntro() throws Exception {
        Assert.assertEquals(
                NodeParser.parseTexPrelude().source()
                        .parse("\t\\documentclass{article}\n" + "\n" + "\\usepackage{tikz}\n" + "\n" + "\\begin{document}\n"
                                + "\t\\begin{tikzpicture}\n"),
                "\t\\documentclass{article}\n" + "\n" + "\\usepackage{tikz}\n" + "\n" + "\\begin{document}\n" + "\t\\begin{tikzpicture}\n");
    }

    @Test
    public void testParseDocumentOutro() throws Exception {
        Assert.assertEquals(NodeParser.parseTexPostlude().source().parse("\t\\end{tikzpicture}\n" + "\\end{document}\n"),
                "\t\\end{tikzpicture}\n" + "\\end{document}\n");

    }
}
