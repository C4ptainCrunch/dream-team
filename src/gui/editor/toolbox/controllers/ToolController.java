package gui.editor.toolbox.controllers;

import gui.editor.toolbox.views.ToolView;

import javax.swing.*;

public class ToolController extends AbstractButtonPanelController {


    public ToolController(ToolView view) {
        super(view);
    }

    @Override
    public void buttonClicked(JToggleButton button) {
        view.redraw();
    }
}
