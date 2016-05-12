package controllers.editor.toolbox;

import constants.Models;
import misc.utils.ClientTest;
import models.ToolModel;
import models.tikz.TikzCircle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import views.editor.toolbox.AttributesChooserView;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * Created by jhellinckx on 12/05/16.
 */
public class AttributesChooserControllerTest extends ClientTest {
    private ToolModel model;
    private TikzCircle component;
    private AttributesChooserController controller;
    private AttributesChooserView view;
    private String testLabel;
    private int testStroke;
    private Color testColor;

    @Before
    public void setUp(){
        testLabel = "testLabel";
        testStroke = 5;
        testColor = Color.yellow;
        component = new TikzCircle();
        component.setLabel(testLabel);
        component.setStroke(testStroke);
        component.setStrokeColor(testColor);
        model = new ToolModel(component);
        view = new AttributesChooserView(model);
        controller = new AttributesChooserController(view, model);
    }

    @After
    public void tearDown(){

    }


    @Test
    public void testLabelEntered() throws Exception {
        controller.labelEntered();
        assertEquals(component.getLabel(), "");
    }

    @Test
    public void testStrokeChanged() throws Exception {
        controller.strokeChanged();
        assertEquals(component.getStroke(), Models.DEFAULT.STROKE);
    }
}