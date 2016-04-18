package gui.editor.controllers;

import gui.editor.views.EditorView;
import gui.editor.views.MenuView;
import gui.help.views.HelpView;
import gui.projectManagement.views.HistoryView;
import models.Project;
import utils.PdfCompilationError;
import utils.PdfRenderer;

import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Implementation of the Controller (from the MVC architectural pattern) for the Menu.
 * The Menu is the menu bar of the GUI.
 */
public class MenuController implements Observer {
    private final MenuView view;
    private final Project project;

    /**
     * Constructs a new Controller for the Menu,
     * with a given Project and EditorView
     * @param view The MenuView which is associated with this controller
     * @param project The Project
     */
    public MenuController(MenuView view, Project project) {
        this.view = view;

        this.project = project;
        this.project.getGraph().addObserver(this);
    }

    /**
     * Called when Observables linked to this Observer call notify(),
     * @param o The Observable
     * @param arg The arguments given by the Observable
     */
    public void update(Observable o, Object arg) {
        // this was left intentionally blank
    }

    /**
     * Saves the Tikz text into a file
     */
    public void save() {
        try {
            this.project.save();
        } catch (IOException e) {
            e.printStackTrace();
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
     * Opens the History window whichs shows the modifications done on the working project
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
     * Exits the program
     * @param parentView The view in which the menu view associated with this controller is contained
     */
    public void exit(EditorView parentView) {
        save();
        parentView.dispose();
    }
}
