package gui.editor.toolbox.views;

import gui.editor.toolbox.controllers.AbstractButtonPanelController;
import gui.editor.toolbox.controllers.ToolController;
import gui.editor.toolbox.views.AbstractButtonPanel;
import gui.editor.toolbox.views.ShapeToolView;

import javax.swing.*;
import java.util.HashMap;

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
