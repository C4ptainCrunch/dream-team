package gui.editor.toolbox.views;

import java.util.HashMap;

import javax.swing.*;

import gui.editor.toolbox.controllers.AbstractButtonPanelController;
import gui.editor.toolbox.controllers.ShapeToolController;

public class ShapeToolView extends AbstractButtonPanel {
    @Override
    JPanel getDelfaultPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Choose a shape"));
        return panel;
    }

    @Override
    AbstractButtonPanelController getController() {
        return new ShapeToolController(this);
    }

    @Override
    HashMap<String, JPanel> getOptions() {
        HashMap<String, JPanel> ret = new HashMap<>();

        JPanel rectangle = new JPanel();
        rectangle.add(new JLabel("Rectangle Box"));
        ret.put("Rectangle", rectangle);

        JPanel circle = new JPanel();
        circle.add(new JLabel("Circle Box"));
        ret.put("Circle", circle);

        JPanel polygon = new JPanel();
        polygon.add(new JLabel("Polygon Box"));
        ret.put("Polygon", polygon);

        return ret;
    }
}
