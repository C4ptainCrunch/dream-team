package gui.editor.toolbox.controllers;

import java.util.Observable;
import java.util.Observer;

import models.TikzComponent;
import gui.editor.toolbox.model.ToolModel;
import gui.editor.toolbox.views.SelectorView;

/**
 * Created by aurelien on 13/04/16.
 */
public class SelectorController implements Observer {

    private SelectorView view;
    private ToolModel model;

    public SelectorController(SelectorView v, ToolModel m) {
        view = v;
        model = m;
    }

    public void itemSelected(TikzComponent component) {
        model.setComponent(component);
    }

    @Override
    public void update(Observable o, Object obj) {

    }
}
