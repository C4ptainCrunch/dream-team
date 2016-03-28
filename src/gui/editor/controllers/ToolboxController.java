package gui.editor.controllers;

import gui.editor.views.ToolboxView;

public class ToolboxController {
    private ToolboxView view;

    public ToolboxController(ToolboxView view) {
        this.view = view;
    }

    public void toolClicked() {
        view.redraw();
    }
}
