package gui.editor.views;

import gui.editor.controllers.CanvasController;
import models.TikzComponent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by mrmmtb on 17.04.16.
 */
public class PopupMenuView extends JPopupMenu {
    private CanvasController controller;
    private JMenuItem delete;
    private TikzComponent component;

    public PopupMenuView(CanvasController controller) {
        this.controller = controller;
        this.component = null;
        initMenuItems();
    }

    private void initMenuItems() {
        this.delete = new JMenuItem("Delete");
        this.delete.addActionListener(actionEvent -> controller.deleteItem(component));
        add(delete);
    }

    public void setComponent(TikzComponent component) {
        this.component = component;
    }


}
