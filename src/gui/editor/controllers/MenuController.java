package gui.editor.controllers;

import gui.editor.views.EditorView;
import gui.editor.views.MenuView;
import gui.editor.views.SourceView;
import gui.projectManagement.views.HistoryView;
import gui.help.views.HelpView;
import models.TikzGraph;
import utils.PdfCompilationError;
import utils.PdfRenderer;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

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

    public void save(){
        String path = view.getPath();
        String tikzText = graph.toString();

        try{
            FileWriter f = new FileWriter(path+"/tikz.save");
            BufferedWriter bufferedWriter = new BufferedWriter(f);
            bufferedWriter.write(tikzText);
            bufferedWriter.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void compileAndOpen(){
        try {
            PdfRenderer.compileAndOpen(new File(view.getPath() + "/tikz.pdf"), graph);
        }
        catch (PdfCompilationError e){
            showMessageDialog(null, "Error during compilation");
        }
    }

    public void openHistory() {

        HistoryView histView = new HistoryView(view.getPath());
    }

    public void showHelp(){
        java.awt.EventQueue.invokeLater(HelpView::new);
    }

    public void exit(EditorView parentView) {
        save();
        parentView.dispose();
    }
}
