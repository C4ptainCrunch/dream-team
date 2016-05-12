package controllers.editor;

import models.tikz.TikzCircle;
import models.tikz.TikzComponent;
import models.tikz.TikzGraph;
import org.junit.Before;
import org.junit.Test;
import views.editor.SourceView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class SourceControllerTest {

    private SourceController controller;
    private TikzGraph graph;
    private Set<TikzComponent> components;

    @Before
    public void setUp(){
        graph = new TikzGraph();
        TikzCircle circle = new TikzCircle();
        TikzCircle comp = new TikzCircle();
        components = new HashSet<>();
        components.add(circle);
        components.add(comp);
        graph.add(circle);
        graph.add(comp);
        controller = new SourceController(null, graph);
    }

    @Test
    public void testGetLines() throws Exception {
        controller.updateLineToComponent();
        List<Integer> positions = controller.getLines(components);
        List<Integer> expected_positions = new ArrayList<>();
        for (int i = 0; i < components.size(); i++){
            expected_positions.add(i);
        }
        assertArrayEquals(positions.toArray(), expected_positions.toArray());


    }
}