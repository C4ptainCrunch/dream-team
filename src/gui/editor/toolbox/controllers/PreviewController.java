package gui.editor.toolbox.controllers;

import gui.editor.toolbox.views.PreviewView;
import models.TikzComponent;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by aurelien on 12/04/16.
 */
public class PreviewController implements Observer {

    private PreviewView view;

    public PreviewController(PreviewView v){
        view = v;
    }

    @Override
    public void update(Observable o, Object obj){
        view.setComponent((TikzComponent)obj);
        view.repaint();
    }
}
