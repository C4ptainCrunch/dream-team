package controllers.editor;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.event.ItemEvent;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

import javax.swing.*;

import models.project.Project;
import utils.Log;
import utils.PdfCompilationError;
import utils.PdfRenderer;
import views.editor.EditorView;
import views.editor.MenuView;
import views.help.HelpView;
import views.management.FileChooseView;
import views.management.HistoryView;
import constants.Errors;

/**
 * Implementation of the Controller (from the MVC architectural pattern) for the
 * Menu. The Menu is the menu bar of the GUI.
 */
public class MenuController implements Observer {
    private final static Logger logger = Log.getLogger(MenuController.class);
    private final MenuView view;
    private final Project project;

    /**
     * Constructs a new Controller for the Menu, with a given Project and
     * EditorView
     *
     * @param view
     *            The MenuView which is associated with this controller
     * @param project
     *            The Project
     */
    public MenuController(MenuView view, Project project) {
        this.view = view;

        this.project = project;
        this.project.getGraph().addObserver(this);
    }

    /**
     * Called when Observables linked to this Observer call notify(),
     *
     * @param o
     *            The Observable
     * @param arg
     *            The arguments given by the Observable
     */
    public void update(Observable o, Object arg) {
        // this was left intentionally blank
    }

    /**
     * Saves the Tikz text into a file
     */
    public void save() {
        try {
            if(this.project.isTemporary()){
                File newDir = new FileChooseView("Save project", JFileChooser.DIRECTORIES_ONLY).ask();
                if(newDir != null){
                    this.project.rename(newDir);
                }
            }
            this.project.save();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(view, Errors.SAVE_ERROR, Errors.ERROR, JOptionPane.ERROR_MESSAGE);
            logger.severe("Project saved failed : " + e.toString());
        }
    }

    /**
     * Compiles and renders the Tikz into a PDF file
     */
    public void compileAndOpen() {
        // TODO : should we move this to the model ?
        try {
            PdfRenderer.compileAndOpen(new File(this.project.getPath() + "/tikz.pdf"), this.project.getGraph());
        } catch (PdfCompilationError e) {
            showMessageDialog(null, "Error during compilation");
        }
    }

    /**
     * Opens the History window whichs shows the modifications done on the
     * working project
     */
    public void openHistory() {
        HistoryView histView = new HistoryView(this.project);
    }

    /**
     * Opens the Help window
     */
    public void showHelp() {
        java.awt.EventQueue.invokeLater(HelpView::new);
    }

    /**
     * Saves the project and closes the editor window.
     * If the project has no name/path, it asks the user if
     * he wants to save the file to the disk.
     * If the user cancels, no windows are closed and this
     * method does nothing.
     *
     * @param parentView
     *            The view in which the menu view associated with this
     *            controller is contained
     */
    public void saveAndQuit(EditorView parentView) {
        boolean shouldQuit = true;
        if(this.project.isTemporary()){
            int r = JOptionPane.showConfirmDialog(this.view, "Would you like to save your project ?");
            if(r == JOptionPane.NO_OPTION){
            } else if (r == JOptionPane.CANCEL_OPTION){
                shouldQuit = false;
            } else if (r == JOptionPane.YES_OPTION) {
                save();
            }
        } else {
            save();
        }
        if(shouldQuit){
            parentView.dispose();
        }
    }

    public void setColorBlindMode(int stateChange) {
        boolean set_mode = (stateChange == ItemEvent.SELECTED ? true : false);
        view.setBlindMode(set_mode);
    }
}
