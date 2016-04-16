package gui.editor.controllers;

import static javax.swing.JOptionPane.showMessageDialog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import models.TikzGraph;
import utils.PdfCompilationError;
import utils.PdfRenderer;
import gui.editor.views.EditorView;
import gui.editor.views.MenuView;
import gui.help.views.HelpView;
import gui.projectManagement.views.HistoryView;

/**
 * Implementation of the Controller (from the MVC architectural pattern) for the Menu.
 * The Menu is the menu bar of the GUI.
 */
public class MenuController implements Observer {
    private final MenuView view;
    private final TikzGraph graph;

    /**
     * Constructs a new Controller for the Menu,
     * with a given TikzGraph and EditorView
     * @param view The MenuView which is associated with this controller
     * @param graph The TikzGraph
     */
    public MenuController(MenuView view, TikzGraph graph) {
        this.view = view;

        this.graph = graph;
        this.graph.addObserver(this);
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
        String path = view.getProjectPath();
        String tikzText = graph.toString();

        try {
            FileWriter f = new FileWriter(path + "/tikz.save");
            BufferedWriter bufferedWriter = new BufferedWriter(f);
            bufferedWriter.write(tikzText);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Compiles and renders the Tikz into a PDF file
     */
    public void compileAndOpen() {
        try {
            PdfRenderer.compileAndOpen(new File(view.getProjectPath() + "/tikz.pdf"), graph);
        } catch (PdfCompilationError e) {
            showMessageDialog(null, "Error during compilation");
        }
    }

    /**
     * Opens the History window whichs shows the modifications done on the working project
     */
    public void openHistory() {

        HistoryView histView = new HistoryView(this.view.getProjectPath());
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
