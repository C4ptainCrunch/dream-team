package controllers.editor;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

import javax.swing.*;

import constants.GUI;
import models.project.Diagram;
import utils.Log;
import views.editor.EditorView;
import constants.Errors;

/**
 * Implementation of the Controller (from the MVC architectural pattern) for the
 * Editor. The Editor is the main element of the GUI which contains the other
 * elements of the GUI.
 */
public class EditorController implements Observer {
    private final static Logger logger = Log.getLogger(EditorController.class);
    private final EditorView view;
    private final Diagram diagram;

    /**
     * Constructs a new Controller for the Editor, with a given Diagram and
     * EditorView
     *
     * @param view
     *            The EditorView which is associated with this controller
     * @param diagram
     *            The Diagram
     */
    public EditorController(EditorView view, Diagram diagram) {
        this.view = view;
        this.diagram = diagram;
        this.diagram.getGraph().addObserver(this);
        this.diagram.addObserver(this);
    }

    public void update(Observable o, Object arg) {
        try {
            this.diagram.save();
            this.setTitle();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(view, Errors.SAVE_ERROR, Errors.ERROR, JOptionPane.ERROR_MESSAGE);
            logger.severe("Diagram saved failed : " + e.toString());
        }
    }

    /**
     * Sets the title of the view to "CreaTikz - project_name"
     */
    public void setTitle() {
        String title = GUI.MenuBar.APP_NAME + " - ";
        if(this.diagram.getProject().isTemporary()){
            title = title + "(Unsaved)";
        } else {
            title = title + this.diagram.getName() + ".tikz";
        }
        this.view.setTitle(title);
    }
}
