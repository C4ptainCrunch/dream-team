package views.editor;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Map;

import javax.swing.*;

import models.project.Project;
import models.tikz.TikzComponent;
import models.tikz.TikzGraph;
import constants.GUI;
import controllers.editor.EditorController;
import views.editor.toolbox.ToolBoxView;

/**
 * Implementation of the View (from the MVC architectural pattern) for the Editor.
 * The Editor is the main element of the GUI which contains the other elements of the GUI.
 */
public class EditorView extends JFrame {
    private Project project;

    private final CanvasView canvasView;
    private final SourceView sourceView;
    private final MenuView menuView;
    private final ToolBoxView toolBoxView;

    private EditorController controller;

    /**
     * Constructs a new View for the Editor,
     * with a given Project.
     * Creates all the views that are contained within this view.
     * @param project The project
     */
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

    /**
     * Renders the view by initializing it and its pane, corresponding
     * to the views that are contained within this view
     */
    public final void render() {
        this.setTitle(GUI.MenuBar.APP_NAME);

        DisplayMode gd = GraphicsEnvironment.
                getLocalGraphicsEnvironment().
                getDefaultScreenDevice().
                getDisplayMode();

        this.setSize(new Dimension(gd.getWidth(), gd.getHeight()));
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

    /**
     * Getter for the selected tool
     * @return the tikz component that has been selected from the toolbox
     */
    public final TikzComponent getSelectedTool() {
        return toolBoxView.getSelectedTool();
    }

    public final void resetTool() { toolBoxView.resetTool(); };

    public final void addTemplateToToolBox(File file){
        toolBoxView.addTemplateToView(file);
    }
}
