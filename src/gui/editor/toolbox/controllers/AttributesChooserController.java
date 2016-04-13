package gui.editor.toolbox.controllers;

import gui.editor.toolbox.model.ToolModel;
import gui.editor.toolbox.views.AttributesChooserView;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by aurelien on 13/04/16.
 */
public class AttributesChooserController implements Observer {

    private AttributesChooserView view;
    private ToolModel model;

    public AttributesChooserController(AttributesChooserView v, ToolModel m){
        view = v;
        model = m;
    }

    public void colorSelected(Color color){
        model.setComponentColor(color);
    }

    public void labelEntered(String label){
        model.setComponentLabel(label);
    }

    @Override
    public void update(Observable o, Object obj){

    }

}
