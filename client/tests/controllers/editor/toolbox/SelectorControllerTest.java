package controllers.editor.toolbox;

import static org.junit.Assert.assertEquals;

import java.awt.*;

import misc.utils.ClientTest;
import models.ToolModel;
import models.tikz.TikzCircle;
import models.tikz.TikzComponent;
import models.tikz.TikzNode;

import org.junit.Before;
import org.junit.Test;

import views.editor.toolbox.SelectorView;
import views.editor.toolbox.ToolView;

/**
 * Created by jhellinckx on 12/05/16.
 */
public class SelectorControllerTest extends ClientTest {
    private SelectorController controller;
    private TikzNode node;
    private ToolModel model;
    private SelectorView view;
    private ToolView parentView;

    @Before
    public void setUp() {
        node = new TikzCircle();
        node.setStrokeColor(Color.blue);
        node.setLabel("its a prank");
        model = new ToolModel(node);
        parentView = new ToolView();
        view = new SelectorView(parentView, model);
        controller = new SelectorController(view, model);
    }

    @Test
    public void testItemSelected() throws Exception {
        TikzCircle selectedNode = new TikzCircle();
        controller.itemSelected(selectedNode);
        TikzComponent actualComp = model.getComponentClone();
        actualComp.setReference(selectedNode.getReference());
        assertEquals(actualComp.toString(), selectedNode.toString());
    }
}
