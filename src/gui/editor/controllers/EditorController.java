package gui.editor.controllers;

import models.Project;
import gui.editor.views.EditorView;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class EditorController implements Observer{
    private final EditorView view;
    private final Project project;

    public EditorController(EditorView view, Project project) {
        this.view = view;
        this.project = project;
        this.project.getGraph().addObserver(this);
    }

    public void update(Observable o, Object arg) {
        try {
            this.project.save();
        } catch (IOException e) {
            // TODO : warn the user that the save failed
            e.printStackTrace();
        }
    }
}
