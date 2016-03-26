package parser;

import java.awt.*;
import java.util.ArrayList;

import models.TikzGraph;

import org.junit.Assert;
import org.junit.Test;

public class NodeParserTest {

	@org.junit.Test
	public void testReference() throws Exception {
		Assert.assertEquals(NodeParser.reference().parse("(hello_world)"), "hello_world");
	}

	@org.junit.Test
	public void testLabel() throws Exception {
		Assert.assertEquals(NodeParser.label().parse("{Hello, my dear little world}"), "Hello, my dear little world");
	}

	@Test
	public void testOptions() throws Exception {
		ArrayList<String> testarray = new ArrayList<>();
		testarray.add("hello");
		testarray.add("my");
		testarray.add("name");
		testarray.add("is");
		testarray.add("trololo");
		Assert.assertEquals(NodeParser.options().parse("[hello, my,name, is,   trololo]"), testarray);
	}

	@Test
	public void testCoordinates() throws Exception {
		Assert.assertEquals(NodeParser.coordinates().parse("(5,4)"), new Point(5, 4));
	}

	@Test
	public void testNodeFromDraw() throws Exception {
		Assert.assertEquals(NodeParser.nodeFromDraw().parse("(0,0) node [draw] {a}").toString(),
				"Coordinates: java.awt.Point[x=0,y=0], Options: [draw], Label: a");
		Assert.assertEquals(NodeParser.nodeFromDraw().parse("(0,0) node {a}").toString(),
				"Coordinates: java.awt.Point[x=0,y=0], Options: [], Label: a");
		Assert.assertEquals(NodeParser.nodeFromDraw().parse("(0,0) node[circle split, hello] {}").toString(),
				"Coordinates: java.awt.Point[x=0,y=0], Options: [circle split, hello], Label: ");
	}

	@Test
	public void testNodeFromNode() throws Exception {
		TikzGraph graph = new TikzGraph();
		NodeParser.nodeFromNode(graph).parse("\\node[lolilol, triangle](nametest) at (15,46)");
		NodeParser.nodeFromNode(graph).parse("\\node[lolilol, triangle]() at (15,46)");
		NodeParser.nodeFromNode(graph).parse("\\node[rectangle]() at (250,200){Demo Label}");
		NodeParser.nodeFromNode(graph).parse("\\node[rectangle]() at (250,200){}");
		NodeParser.nodeFromNode(graph).parse("\\node[]() at (250,200) {Demo Label}");
		Assert.assertEquals(graph.size(), 5);
	}

	@Test
	public void testNodesFromDraw() throws Exception {
		TikzGraph graph = new TikzGraph();
		NodeParser.nodesFromDraw(graph)
				.parse("\\draw[zedjio] (0,0) node[draw] {a} -- (2,0) node[draw] {l} -- (0,5) node[draw] {p}");
		Assert.assertEquals(graph.size(), 3);
	}

	@Test
	public void testEdgesFromDraw() throws Exception {
		TikzGraph graph = new TikzGraph();
		NodeParser.edgesFromDraw(graph).parse("\\draw[color=blue, ->] (0,0) -- (5,5) -- (5,7) -- (8,2)");
		Assert.assertEquals(graph.size(), 4);
	}

	@Test
	public void testParseTexDocument() throws Exception {
		TikzGraph graph = new TikzGraph();
		NodeParser.parseTeXDocument(graph)
				.parse("\\documentclass{article}\n" +
						"\\usepackage{tikz}\n" +
						"\\begin{document}\n" +
						"\t\\begin{tikzpicture}\n" +
						"\n" +
						"\t\t\\draw[color=red] (0,0) node[draw] {a} -- (2,0) node[draw] {l} -- (0,5) node[draw] {p};\n" +
						"\n" +
						"\t\\end{tikzpicture}\n" +
						"\\end{document}");
		Assert.assertEquals(graph.size(), 3);
	}

	@Test
	public void testParseDocument() throws Exception {
		TikzGraph graph = new TikzGraph();
		NodeParser.parseDocument(graph)
				.parse("\t\t\\draw[color=red] (0,0) node[draw] {a} -- (2,0) node[draw] {l} -- (0,5) node[draw] {p};\n");
		Assert.assertEquals(graph.size(), 3);
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
        String text = "\\draw[color=red] (0,0) node[draw] {a};";
        TikzGraph graph = new TikzGraph();
        NodeParser.parseDocument(graph).parse(text);
        Assert.assertEquals(graph.size(), 1);
    }

    @Test
    public void testParseMinimalWithWhitespace() throws Exception {
        String text = "\\draw[color=red] (0,0) node[draw] {a};\n \n \t";
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
		Assert.assertEquals(NodeParser.parseTexPrelude().source().parse(
				"\t\\documentclass{article}\n" +
						"\n" +
						"\\usepackage{tikz}\n" +
						"\n" +
						"\\begin{document}\n" +
						"\t\\begin{tikzpicture}\n"
		),"\t\\documentclass{article}\n" +
				"\n" +
				"\\usepackage{tikz}\n" +
				"\n" +
				"\\begin{document}\n" +
				"\t\\begin{tikzpicture}\n");
	}

	@Test
	public void testParseDocumentOutro() throws Exception {
		Assert.assertEquals(NodeParser.parseTexPostlude().source().parse(
				"\t\\end{tikzpicture}\n" +
						"\\end{document}\n"
		),"\t\\end{tikzpicture}\n" +
				"\\end{document}\n");


	}
}
