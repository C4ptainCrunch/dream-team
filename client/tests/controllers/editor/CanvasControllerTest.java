package controllers.editor;

import misc.utils.ClientTest;
import models.tikz.TikzCircle;
import models.tikz.TikzComponent;
import models.tikz.TikzGraph;
import models.tikz.TikzNode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import views.editor.CanvasView;
import views.editor.canvas.drawables.DrawableTikzComponent;
import views.editor.canvas.drawers.Drawer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by jhellinckx on 12/05/16.
 */
public class CanvasControllerTest extends ClientTest{

    private TikzGraph testedGraph;
    private CanvasView view;
    private CanvasController controller;


    @Before
    public void setUp() throws Exception {
        testedGraph = new TikzGraph();
        view = new CanvasView(null, testedGraph);
        controller = new CanvasController(view, testedGraph);
    }

    private List<TikzComponent> drawablesToComponents(List<DrawableTikzComponent> drawables){
        List<TikzComponent> components = new ArrayList<>();
        for(DrawableTikzComponent drawable : drawables){
            components.add(drawable.getComponent());
        }
        return components;
    }

    @Test
    public void testUpdateDrawables() throws Exception {
        testedGraph.add(new TikzCircle());
        assertNotEquals(drawablesToComponents(controller.getDrawables()), testedGraph.getComponents());
        controller.updateDrawables();
        assertEquals(drawablesToComponents(controller.getDrawables()), testedGraph.getComponents());
    }

    @Test
    public void testAddDrawableComponent() throws Exception {
        TikzComponent component = new TikzCircle();
        testedGraph.add((TikzNode)component);
        DrawableTikzComponent drawable = Drawer.toDrawable(component, view);
        controller.addDrawableComponent(drawable);
        assertEquals(drawablesToComponents(controller.getDrawables()), testedGraph.getComponents());
    }

    @Test
    public void testDeleteItem() throws Exception {
        TikzNode node = new TikzCircle();
        testedGraph.add(node);
        controller.deleteItem(node);
        assertTrue(testedGraph.isEmpty());
    }


    @Test
    public void testAddNodeToGraph() throws Exception {
        TikzNode node = new TikzCircle();

    }

    @Test
    public void testAddEdgeToGraph() throws Exception {

    }

    @Test
    public void testAddGraph() throws Exception {

    }

    @Test
    public void testMoveComponent() throws Exception {

    }
}