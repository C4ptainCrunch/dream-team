package gui.editor.toolbox.controllers;

import javax.swing.*;

import gui.editor.toolbox.views.ShapeToolView;

public class ShapeToolController extends AbstractButtonPanelController {


    public ShapeToolController(ShapeToolView view) {
        super(view);
    }

    @Override
    public void buttonClicked(JToggleButton button) {
        view.redraw();
    }
}
