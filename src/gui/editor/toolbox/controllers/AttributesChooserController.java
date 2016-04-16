package gui.editor.toolbox.controllers;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import gui.editor.toolbox.model.ToolModel;
import gui.editor.toolbox.views.AttributesChooserView;

/**
 * Created by aurelien on 13/04/16.
 */
public class AttributesChooserController implements Observer {

    private static final String COLOR_DIALOG_TITLE = "Choose Stroke Color";

    private final AttributesChooserView view;
    private final ToolModel model;
    private Color chosen_color;

    public AttributesChooserController(AttributesChooserView v, ToolModel m) {
        view = v;
        model = m;
    }

    private void colorSelected(Color color) {
        model.setComponentColor(color);
    }

    public void labelEntered(String label) {
        model.setComponentLabel(label);
    }

    public void strokeWidth(int width) {
        model.setComponentStrokeWidth(width);
    }

    public void chooseColor() {
        chosen_color = JColorChooser.showDialog(this.view, COLOR_DIALOG_TITLE, Color.GRAY);
        view.setColorFieldColor(chosen_color);
        colorSelected(chosen_color);
    }

    @Override
    public void update(Observable o, Object obj) {
        view.repaint();
    }
}
