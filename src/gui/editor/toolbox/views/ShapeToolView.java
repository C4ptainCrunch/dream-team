package gui.editor.toolbox.views;

import java.util.HashMap;

import javax.swing.*;

import gui.editor.toolbox.controllers.AbstractButtonPanelController;
import gui.editor.toolbox.controllers.ShapeToolController;

public class ShapeToolView extends AbstractButtonPanel {
    @Override
    AbstractButtonPanel getDelfaultPanel() {
        TextPanel panel = new TextPanel("Choose a shape");
        return panel;
    }

    @Override
    AbstractButtonPanelController getController() {
        return new ShapeToolController(this);
    }

    @Override
    protected HashMap<String, AbstractButtonPanel> getOptions() {
        HashMap<String, AbstractButtonPanel> ret = new HashMap<>();

        TextPanel rectangle = new TextPanel("Rectangle Box");
        ret.put("Rectangle", rectangle);

        TextPanel circle = new TextPanel("Circle Box");
        ret.put("Circle", circle);

        TextPanel polygon = new TextPanel("Polygon Box");
        ret.put("Polygon", polygon);

        return ret;
    }
}
