package views.editor.canvas.popup;

import javax.swing.*;

import models.tikz.TikzComponent;
import controllers.editor.CanvasController;

/**
 * A PopupMenu that allows the user to modify a component on the Canvas.
 */

public class PopupMenuView extends JPopupMenu {
    private CanvasController controller;
    private JMenuItem delete;
    private JMenuItem edit;
    private TikzComponent component;

    public PopupMenuView(CanvasController controller) {
        this.controller = controller;
        this.component = null;
        initMenuItems();
    }

    private void initMenuItems() {
        this.delete = new JMenuItem("Delete");
        this.edit = new JMenuItem("Edit");

        this.delete.addActionListener(actionEvent -> controller.deleteItem(component));
        this.edit.addActionListener(actionEvent -> controller.editItem(component));

        add(edit);
        add(delete);
    }

    public void setComponent(TikzComponent component) {
        this.component = component;
    }

}
