package controllers.editor;

import constants.Errors;
import models.project.Project;
import views.editor.EditorView;
import utils.Log;

import javax.swing.*;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

/**
 * Implementation of the Controller (from the MVC architectural pattern) for the Editor.
 * The Editor is the main element of the GUI which contains the other elements of the GUI.
 */
public class EditorController implements Observer{
    private final EditorView view;
    private final Project project;
    private final static Logger logger = Log.getLogger(EditorController.class);


    /**
     * Constructs a new Controller for the Editor,
     * with a given Project and EditorView
     * @param view The EditorView which is associated with this controller
     * @param project The Project
     */
    public EditorController(EditorView view, Project project) {
        this.view = view;
        this.project = project;
        this.project.getGraph().addObserver(this);
    }

    public void update(Observable o, Object arg) {
        try {
            this.project.save();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(view, Errors.SAVE_ERROR, Errors.ERROR, JOptionPane.ERROR_MESSAGE);
            logger.severe("Project saved failed : " + e.toString());
        }
    }
}
