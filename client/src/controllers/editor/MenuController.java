package controllers.editor;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.event.ItemEvent;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

import javax.swing.*;

import models.project.Diagram;
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
    private final Diagram diagram;

    /**
     * Constructs a new Controller for the Menu, with a given Diagram and
     * EditorView
     *
     * @param view
     *            The MenuView which is associated with this controller
     * @param diagram
     *            The Diagram
     */
    public MenuController(MenuView view, Diagram diagram) {
        this.view = view;

        this.diagram = diagram;
        this.diagram.getGraph().addObserver(this);
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
            if(this.diagram.isTemporary()){
                // TODO andré : choisir un nom
                this.diagram.rename("Nouveau_nom");
                this.diagram.save();
                boolean addToExistingProject = false;
                if(addToExistingProject){
                    // TODO nikita
                } else {
                    File newDir = new FileChooseView("Save diagram", JFileChooser.DIRECTORIES_ONLY).ask();
                    if(newDir != null){
                        this.diagram.getProject().move(newDir);
                    }
                }
            }
            this.diagram.save();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(view, Errors.SAVE_ERROR, Errors.ERROR, JOptionPane.ERROR_MESSAGE);
            logger.severe("Diagram saved failed : " + e.toString());
        }
    }

    /**
     * Compiles and renders the Tikz into a PDF file
     */
    public void compileAndOpen() {
        // TODO : should we move this to the model ?
        try {
            PdfRenderer.compileAndOpen(new File(this.diagram.getProject().getDirectory() + "/tikz.pdf"), this.diagram.getGraph());
        } catch (PdfCompilationError e) {
            showMessageDialog(null, "Error during compilation");
        }
    }

    /**
     * Opens the History window whichs shows the modifications done on the
     * working diagram
     */
    public void openHistory() {
        HistoryView histView = new HistoryView(this.diagram);
    }

    /**
     * Opens the Help window
     */
    public void showHelp() {
        java.awt.EventQueue.invokeLater(HelpView::new);
    }

    /**
     * Saves the diagram and closes the editor window.
     * If the diagram has no name/path, it asks the user if
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
        if(this.diagram.isTemporary()){
            int r = JOptionPane.showConfirmDialog(this.view, "Would you like to save your diagram ?");
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
