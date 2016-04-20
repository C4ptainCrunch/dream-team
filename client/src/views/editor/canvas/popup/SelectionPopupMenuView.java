package views.editor.canvas.popup;

import controllers.editor.CanvasController;
import models.tikz.TikzGraph;

import javax.swing.*;

/**
 * Created by aurelien on 19/04/16.
 */
public class SelectionPopupMenuView extends JPopupMenu {

    private JMenuItem export;
    private TikzGraph graph;
    private CanvasController controller;

    public SelectionPopupMenuView(CanvasController controller){
        this.graph = null;
        this.export = new JMenuItem("Export as template");
        this.controller = controller;
        this.export.addActionListener(actionEvent -> controller.exportSelectionAsTemplate());
        add(export);
    }
}
