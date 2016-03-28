package gui.editor.controllers;

import javax.swing.*;

import gui.editor.views.ShapeToolView;
import gui.editor.views.ToolView;

public class ShapeToolController extends AbstractButtonPanelController{


    public ShapeToolController(ShapeToolView view) {
        super(view);
    }

    @Override
    public void buttonClicked(JToggleButton button) {
        view.redraw();
    }
}
