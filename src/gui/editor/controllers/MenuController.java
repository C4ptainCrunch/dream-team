package gui.editor.controllers;

import static javax.swing.JOptionPane.showMessageDialog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import models.Project;
import models.tikz.TikzGraph;
import utils.PdfCompilationError;
import utils.PdfRenderer;
import gui.editor.views.EditorView;
import gui.editor.views.MenuView;
import gui.help.views.HelpView;
import gui.projectManagement.views.HistoryView;

public class MenuController implements Observer {
    private MenuView view;
    private Project project;

    public MenuController(MenuView view, Project project) {
        this.view = view;

        this.project = project;
        this.project.getGraph().addObserver(this);
    }

    public void update(Observable o, Object arg) {
    }

    public void save() {
        try {
            this.project.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void compileAndOpen() {
        // TODO : should we move this to the model ?
        try {
            PdfRenderer.compileAndOpen(new File(this.project.getPath() + "/tikz.pdf"), this.project.getGraph());
        } catch (PdfCompilationError e) {
            showMessageDialog(null, "Error during compilation");
        }
    }

    public void openHistory() {
        HistoryView histView = new HistoryView(this.project);
    }

    public void showHelp() {
        java.awt.EventQueue.invokeLater(HelpView::new);
    }

    public void exit(EditorView parentView) {
        save();
        parentView.dispose();
    }
}
