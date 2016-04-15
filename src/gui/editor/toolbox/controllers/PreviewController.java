package gui.editor.toolbox.controllers;

import gui.editor.toolbox.model.ToolModel;
import gui.editor.toolbox.views.PreviewView;
import models.TikzComponent;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by aurelien on 12/04/16.
 */
public class PreviewController implements Observer {

    private PreviewView view;
    private ToolModel model;

    public PreviewController(PreviewView v, ToolModel m){
        view = v;
        model = m;
        model.addObserver(this);
    }

    public TikzComponent getComponent(){
        return model.getComponent();
    }

    @Override
    public void update(Observable o, Object obj){
        view.setComponent(model.getComponent());
        view.repaint();
    }
}
