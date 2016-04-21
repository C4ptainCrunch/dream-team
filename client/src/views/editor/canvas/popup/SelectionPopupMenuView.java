package views.editor.canvas.popup;

import javax.swing.*;

import models.tikz.TikzGraph;
import controllers.editor.CanvasController;

/**
 * A PopupMenu that allows the user to export a CanvasSelection as a Template.
 */
public class SelectionPopupMenuView extends JPopupMenu {

    private JMenuItem export;
    private TikzGraph graph;
    private CanvasController controller;

    public SelectionPopupMenuView(CanvasController controller) {
        this.graph = null;
        this.export = new JMenuItem("Export as template");
        this.controller = controller;
        this.export.addActionListener(actionEvent -> controller.exportSelectionAsTemplate());
        add(export);
    }
}
