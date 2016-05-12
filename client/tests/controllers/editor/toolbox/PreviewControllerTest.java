package controllers.editor.toolbox;

import static org.junit.Assert.*;

import java.util.List;

import misc.utils.ClientTest;
import models.ToolModel;
import models.tikz.TikzGraph;
import models.tikz.TikzNode;
import models.tikz.TikzRectangle;

import org.junit.Before;
import org.junit.Test;

import views.editor.toolbox.PreviewView;

/**
 * Created by jhellinckx on 12/05/16.
 */
public class PreviewControllerTest extends ClientTest {
    public PreviewView view;
    public PreviewController controller;
    public ToolModel model;
    public TikzNode node;

    public TikzNode firstNode(TikzGraph g) {
        List<TikzNode> nodes = g.getNodes();
        return nodes.size() > 0 ? nodes.get(0) : null;
    }

    @Before
    public void setUp() {
        node = new TikzRectangle();
        model = new ToolModel(node);
        view = new PreviewView(model);
        controller = new PreviewController(view, model);
    }

    @Test
    public void testGetModelAsGraph() throws Exception {
        TikzGraph expectedGraph = node.toGraph();
        TikzGraph actualGraph = controller.getModelAsGraph();
        TikzNode actualNode = firstNode(actualGraph);
        assertNotEquals(node.getReference(), actualNode.getReference());
        actualNode.setReference(node.getReference());
        assertEquals(actualGraph.toString(), expectedGraph.toString());
    }

    @Test
    public void testResetModel() throws Exception {
        controller.resetModel();
        assertNull(model.getComponentClone());
    }
}
