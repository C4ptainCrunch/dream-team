package views.editor;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Map;
import java.util.Set;

import javax.swing.*;

import models.project.Diagram;
import models.tikz.TikzComponent;
import models.tikz.TikzGraph;
import views.editor.toolbox.ToolBoxView;
import controllers.editor.EditorController;

/**
 * Implementation of the View (from the MVC architectural pattern) for the
 * Editor. The Editor is the main element of the GUI which contains the other
 * elements of the GUI.
 */
public class EditorView extends JFrame {
    private final CanvasView canvasView;
    private final SourceView sourceView;
    private final MenuView menuView;
    private final ToolBoxView toolBoxView;
    private Diagram diagram;
    private EditorController controller;

    /**
     * Constructs a new View for the Editor, with a given Diagram. Creates all
     * the views that are contained within this view.
     *
     * @param diagram
     *            The diagram
     * @param render
     *            Option to activate the render or not
     */
    public EditorView(final Diagram diagram, final Boolean render) {
        TikzGraph graph = diagram.getGraph();
        this.diagram = diagram;
        this.controller = new EditorController(this, diagram);
        this.canvasView = new CanvasView(this, graph);
        this.sourceView = new SourceView(this, graph);
        this.menuView = new MenuView(this, diagram);
        this.toolBoxView = new ToolBoxView();
        if (render) {
            render();
        }
    }

    /**
     * Constructs a new View for the Editor, with a given Diagram. Creates all
     * the views that are contained within this view.
     *
     * @param diagram
     *            The diagram
     */
    public EditorView(final Diagram diagram) {
        this(diagram, true);
    }

    /**
     * Renders the view by initializing it and its pane, corresponding to the
     * views that are contained within this view
     */
    public final void render() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent windowEvent) {
                menuView.saveAndQuit();
                super.windowClosing(windowEvent);
            }
        });
        this.controller.setTitle();

        DisplayMode gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();

        this.setSize(new Dimension(gd.getWidth(), gd.getHeight()));
        this.setLocationRelativeTo(null);

        Container pane = getContentPane();
        pane.setLayout(new BorderLayout());

        pane.add(this.toolBoxView, BorderLayout.WEST);
        pane.add(this.canvasView, BorderLayout.CENTER);
        pane.add(this.sourceView, BorderLayout.EAST);

        this.setJMenuBar(menuView);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setVisible(true);
        canvasView.repaint();
    }

    public CanvasView getCanvasView() {
        return canvasView;
    }

    public SourceView getSourceView() {
        return sourceView;
    }

    public MenuView getMenuView() {
        return menuView;
    }

    public ToolBoxView getToolBoxView() {
        return toolBoxView;
    }

    public Diagram getDiagram() {
        return diagram;
    }

    public final Map<String, Object> getCurrentToolProperties() {
        return null;
    }

    /**
     * Getter for the selected tool
     *
     * @return the tikz component that has been selected from the toolbox
     */
    public final TikzComponent getSelectedTool() {
        return toolBoxView.getSelectedTool();
    }

    public final void resetTool() {
        toolBoxView.resetTool();
    };

    public final void addTemplateToToolBox(final File file) {
        toolBoxView.addTemplateToView(file);
    }

    public final void highlightTextLine(final TikzComponent comp) {
        sourceView.highlightCorrespondingLine(comp);
    }

    public final void highlightTextZone(final Set<TikzComponent> selectedComponents) {
        sourceView.highlightCorrespondingZone(selectedComponents);
    }

    public final void removeHighlights() {
        sourceView.removeHighlights();
    }

    public final void setTextAreaColorBlindMode(final boolean set_mode) {
        sourceView.setColorBlindMode(set_mode);

    }
}
