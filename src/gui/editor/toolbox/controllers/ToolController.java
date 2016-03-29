package gui.editor.toolbox.controllers;

import javax.swing.*;

import gui.editor.toolbox.views.ToolView;

public class ToolController extends AbstractButtonPanelController {


    public ToolController(ToolView view) {
        super(view);
    }

    @Override
    public void buttonClicked(JToggleButton button) {
        view.redraw();
    }
}
