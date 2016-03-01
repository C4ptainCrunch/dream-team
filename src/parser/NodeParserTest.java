package parser;

import java.util.ArrayList;

import org.codehaus.jparsec.functors.Pair;
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
		Assert.assertEquals(NodeParser.coordinates().parse("(5,4)"), new Pair<Double, Double>(5., 4.));
	}

    @Test
    public void testNodeFromDraw() throws Exception {
        Assert.assertEquals(NodeParser.nodeFromDraw().parse("(0,0) node[draw] {a}"), "coord: (0.0, 0.0), options: [draw], label: a");
        Assert.assertEquals(NodeParser.nodeFromDraw().parse("(0,0) node {a}"), "coord: (0.0, 0.0), options: [], label: a");
    }
}