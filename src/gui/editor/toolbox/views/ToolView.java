package gui.editor.toolbox.views;

import java.util.HashMap;

import javax.swing.*;

import gui.editor.toolbox.controllers.AbstractButtonPanelController;
import gui.editor.toolbox.controllers.ToolController;

public class ToolView extends AbstractButtonPanel {
    @Override
    AbstractButtonPanel getDelfaultPanel() {
        TextPanel panel = new TextPanel("Choose a tool");
        return panel;
    }

    @Override
    AbstractButtonPanelController getController() {
        return new ToolController(this);
    }

    @Override
    protected HashMap<String, AbstractButtonPanel> getOptions() {
        HashMap<String, AbstractButtonPanel> ret = new HashMap<>();
        ret.put("Node", new ShapeToolView());

        TextPanel edge = new TextPanel("Edge");
        ret.put("Edge", edge);

        return ret;
    }
}
