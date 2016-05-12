package controllers.editor;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

import javax.swing.*;

import models.project.Diagram;
import utils.Log;
import utils.RecentProjects;
import views.editor.EditorView;
import constants.Errors;
import constants.GUI;

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
    public EditorController(final EditorView view, final Diagram diagram) {
        this.view = view;
        this.diagram = diagram;
        this.diagram.getGraph().addObserver(this);
        this.diagram.getProject().addObserver(this);
        try {
            RecentProjects.addProject(this.diagram.getProject());
        } catch (IOException e) {
            logger.warning("Failed to add the project to the recent list: " + e.toString());
        }
    }

    public void update(final Observable o, final Object arg) {
        try {
            this.diagram.save();
            this.setTitle();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(view, Errors.SAVE_ERROR, Errors.ERROR, JOptionPane.ERROR_MESSAGE);
            logger.severe("Diagram saved failed : " + e.toString());
            e.printStackTrace();
        }
    }

    /**
     * Sets the title of the view to "CreaTikz - project_name"
     */
    public void setTitle() {
        String title = GUI.MenuBar.APP_NAME + " - ";
        if(this.diagram.isTemporary()){
            title = title + "(Unsaved)";
        } else {
            title = title + this.diagram.getProject().getName() + " - " + this.diagram.getName() + ".tikz";
        }
        this.view.setTitle(title);
    }
}
