package gui.editor.toolbox.views;

import java.util.HashMap;

import javax.swing.*;

import gui.editor.toolbox.controllers.AbstractButtonPanelController;
import gui.editor.toolbox.controllers.ToolController;

public class ToolView extends AbstractButtonPanel {
    @Override
    JPanel getDelfaultPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Choose a tool"));
        return panel;
    }

    @Override
    AbstractButtonPanelController getController() {
        return new ToolController(this);
    }

    @Override
    HashMap<String, JPanel> getOptions() {
        HashMap<String, JPanel> ret = new HashMap<>();
        ret.put("Node", new ShapeToolView());

        JPanel edge = new JPanel();
        edge.add(new JLabel("Edge"));
        ret.put("Edge", edge);

        return ret;
    }
}
