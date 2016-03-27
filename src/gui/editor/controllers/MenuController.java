package gui.editor.controllers;

import gui.editor.views.MenuView;
import gui.editor.views.SourceView;
import models.TikzGraph;
import utils.PdfCompilationError;
import utils.PdfRenderer;
import java.io.File;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

import static javax.swing.JOptionPane.showMessageDialog;

public class MenuController implements Observer {
    private MenuView view;
    private TikzGraph graph;

    public MenuController(MenuView view, TikzGraph graph) {
        this.view = view;

        this.graph = graph;
        this.graph.addObserver(this);
    }

    public void update(Observable o, Object arg){}

    public void compileAndOpen(){
        try {
            PdfRenderer.compileAndOpen(new File("/home/nikita/lol.pdf"), graph);
        }
        catch (PdfCompilationError e){
            showMessageDialog(null, "Error during compilation");
        }
    }
}
