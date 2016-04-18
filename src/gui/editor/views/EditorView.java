package gui.editor.views;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Paths;
import java.util.Map;

import javax.swing.*;

import models.Project;
import models.tikz.TikzComponent;
import models.tikz.TikzGraph;
import constants.GUI;
import gui.editor.controllers.EditorController;
import gui.editor.toolbox.views.ToolBoxView;

public class EditorView extends JFrame {
    private Project project;

    private final CanvasView canvasView;
    private final SourceView sourceView;
    private final MenuView menuView;
    private final ToolBoxView toolBoxView;

    private EditorController controller;

    public EditorView(Project project){
        TikzGraph graph = project.getGraph();
        this.project = project;
        this.controller = new EditorController(this, project);

        this.canvasView = new CanvasView(this, graph);
        this.sourceView = new SourceView(this, graph);
        this.menuView = new MenuView(this, project);
        this.toolBoxView = new ToolBoxView();


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                menuView.save();
                super.windowClosing(windowEvent);
            }
        });

        this.render();
        this.setVisible(true);
    }

    public final void render() {
        this.setTitle(GUI.MenuBar.APP_NAME);

        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setLocationRelativeTo(null);

        Container pane = getContentPane();
        pane.setLayout(new BorderLayout());

        pane.add(this.toolBoxView, BorderLayout.WEST);
        pane.add(this.canvasView, BorderLayout.CENTER);
        pane.add(this.sourceView, BorderLayout.EAST);

        this.setJMenuBar(menuView);
    }

    public final Map<String, Object> getCurrentToolProperties() {
        return null;
    }

    public final TikzComponent getSelectedTool() {
        return toolBoxView.getSelectedTool();
    }

    public final void resetTool() { toolBoxView.resetTool(); };
}
