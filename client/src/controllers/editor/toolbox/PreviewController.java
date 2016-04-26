package controllers.editor.toolbox;

import java.util.Observable;
import java.util.Observer;

import models.ToolModel;
import models.tikz.TikzComponent;
import models.tikz.TikzGraph;
import models.tikz.TikzNode;
import views.editor.toolbox.PreviewView;

/**
 * Implementation of the Controller (from the MVC architectural pattern) for the
 * Preview. The Preview is part of the toolbox used to show a preview of the
 * component being edited.
 */
public class PreviewController implements Observer {

    private final PreviewView view;
    private final ToolModel model;

    /**
     * Constructs a new Controller for the Preview with a given PreviewView and
     * a ToolModel
     *
     * @param v
     *            The preview view which is associated with this controller
     * @param m
     *            The tool model
     */
    public PreviewController(PreviewView v, ToolModel m) {
        view = v;
        model = m;
        model.addObserver(this);
    }

    /**
     * Gets the previewed component and converts it to a Graph that contains
     * only one element.
     *
     * @return The component being edited
     */
    public TikzGraph getModelAsGraph() {
        TikzComponent comp = model.getComponentClone();
        TikzNode node = null;
        if (comp != null && comp.isNode()) {
            node = (TikzNode) comp;
            return node.toGraph();
        }
        else {
            return new TikzGraph();
        }
    }

    public void resetModel() {
        model.reset();
    }

    /**
     * Called when Observables linked to this Observer call notify(), sets the
     * component being edited repaints the preview
     *
     * @param o
     *            The Observable
     * @param obj
     *            The Object given by the Observable
     */
    @Override
    public void update(Observable o, Object obj) {
        view.setComponent(model.getComponentClone());
        view.repaint();
    }
}
