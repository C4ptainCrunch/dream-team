package gui.editor.toolbox.controllers;

import java.util.Observable;
import java.util.Observer;

import gui.editor.toolbox.model.ToolModel;
import gui.editor.toolbox.views.PreviewView;
import models.casters.NodeToGraphCaster;
import models.tikz.TikzGraph;
import models.tikz.TikzNode;

/**
 * Implementation of the Controller (from the MVC architectural pattern) for the Preview.
 * The Preview is part of the toolbox used to show
 * a preview of the component being edited.
 */
public class PreviewController implements Observer {

    private final PreviewView view;
    private final ToolModel model;

    /**
     * Constructs a new Controller for the Preview
     * with a given PreviewView and a ToolModel
     * @param v The preview view which is associated with this controller
     * @param m The tool model
     */
    public PreviewController(PreviewView v, ToolModel m) {
        view = v;
        model = m;
        model.addObserver(this);
    }

    /**
     * Getter for the component that is being edited
     * @return The component being edited
     */
    public TikzGraph getModelAsGraph() {
        TikzNode node = (TikzNode) model.getComponentClone();      // TODO: Refactor this.
        return NodeToGraphCaster.cast(node);
    }

    public void resetModel(){
        model.reset();
    }

    /**
     * Called when Observables linked to this Observer call notify(),
     * sets the component being edited
     * repaints the preview
     *
     * @param o The Observable
     * @param obj The Object given by the Observable
     */
    @Override
    public void update(Observable o, Object obj) {
        view.setComponent(model.getComponentClone());
        view.repaint();
    }
}
