package gui.editor.views;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Paths;
import java.util.Map;

import javax.swing.*;

import models.TikzComponent;
import models.TikzGraph;
import constants.GUI;
import gui.editor.controllers.EditorController;
import gui.editor.toolbox.views.ToolBoxView;

/**
 * Implementation of the View (from the MVC architectural pattern) for the Editor.
 * The Editor is the main element of the GUI which contains the other elements of the GUI.
 */
public class EditorView extends JFrame {
    private TikzGraph graph;

    private final CanvasView canvasView;
    private final SourceView sourceView;
    private final MenuView menuView;
    private String projectPath;
    private final ToolBoxView toolBoxView;

    private EditorController controller;

    /**
     * Constructs a new View for the Editor,
     * with a given file path and the information whether the view
     * is constructed with an imported project or a new one.
     * Creates all the views that are contained within this view.
     * @param filePath The path where the project is located
     * @param isImport The boolean that tells whether the project is imported or created
     */
    public EditorView(String filePath, Boolean isImport) {
        this.projectPath = filePath;
        if (isImport) {
            this.graph = new TikzGraph(filePath);
            this.projectPath = Paths.get(getProjectPath()).getParent().toString();
        } else {
            this.graph = new TikzGraph();
        }

        this.canvasView = new CanvasView(this, graph);
        this.sourceView = new SourceView(this, graph);
        this.menuView = new MenuView(this, graph);
        this.toolBoxView = new ToolBoxView();

        this.controller = new EditorController(this, graph);

        this.graph.replace(this.graph);// update du tikZ text

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

    /**
     * Renders the view by initializing it and its pane, corresponding
     * to the views that are contained within this view
     */
    public final void render() {
        this.setTitle(GUI.Text.APP_NAME);

        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setLocationRelativeTo(null);

        Container pane = getContentPane();
        pane.setLayout(new BorderLayout());

        pane.add(this.toolBoxView, BorderLayout.WEST);
        pane.add(this.canvasView, BorderLayout.CENTER);
        pane.add(this.sourceView, BorderLayout.EAST);

        this.setJMenuBar(menuView);
    }

    /**
     * Getter for the current project path
     * @return The path of the current project
     */
    public final String getProjectPath() {
        return projectPath;
    }

    /**
     * Getter for the current tool properties
     * @return TODO null?
     */
    public final Map<String, Object> getCurrentToolProperties() {
        return null;
    }

    /**
     * Getter for the selected tool
     * @return the tikz component that has been selected from the toolbox
     */
    public final TikzComponent getSelectedTool() {
        return toolBoxView.getSelectedTool();
    }
}
