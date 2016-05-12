package views.editor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.*;

import constants.Errors;
import models.project.Diagram;
import constants.GUI;
import controllers.editor.MenuController;
import views.management.ProjectManagementView;

/**
 * Implementation of the View (from the MVC architectural pattern) for the Menu.
 * The Menu is the menu bar of the GUI.
 */
public class MenuView extends JMenuBar {
    private final MenuController controller;
    private final Diagram diagram;
    private final EditorView parentView;

    /**
     * Constructs a new View for the Menu, with a given Diagram and parentView
     * @param parentView
     *            The view which contains this view (ie. EditorView)
     * @param diagram
     *            The Diagram
     */
    public MenuView(EditorView parentView, Diagram diagram) {
        this.parentView = parentView;
        this.diagram = diagram;
        this.controller = new MenuController(this, diagram);
        this.render();
    }

    private void addShortcutItem(JMenu menu, Action action, String label, String key, int shortcut_key, int key_mask){
        JMenuItem item = new JMenuItem(label);
        item.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(shortcut_key, key_mask),label);
        item.setAction(action);
        item.getActionMap().put(key, action);
        menu.add(item);
    }

    private void addUndoItem(JMenu menu){
        String key = "Undo";
        Action action = new AbstractAction(key) {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                diagram.undo();
            }
        };
        addShortcutItem(menu, action, GUI.MenuBar.UNDO, key, KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK);
    }

    private void addRedoItem(JMenu menu){
        String key = "Redo";
        Action action = new AbstractAction(key) {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                diagram.redo();
            }
        };
        addShortcutItem(menu, action, GUI.MenuBar.REDO, key, KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK);
    }

    private void addSaveItem(JMenu menu){
        String key = "Save";
        Action action = new AbstractAction(key) {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.save();
            }
        };
        addShortcutItem(menu, action, GUI.MenuBar.SAVE, key, KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK);
    }

    private void addOpenItem(JMenu menu) {
        String key = "Open";

        Action action = new AbstractAction(key) {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.openNew();
            }
        };
        addShortcutItem(menu, action, GUI.MenuBar.OPEN, key, KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK);
    }

    private void addOpenDiagramItem(JMenu menu) {
        String key = "Open Diagram";

        Action action = new AbstractAction(key) {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.openDiagram();
            }
        };
        addShortcutItem(menu, action, GUI.MenuBar.OPEN_DIAGRAM, key, KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK);
    }

    /**
     * Renders the view by setting the different buttons composing the menu
     */
    private void render() {
        JMenu file_menu = new JMenu(GUI.MenuBar.FILE_MENU);
        this.add(file_menu);

        addOpenItem(file_menu);
        addSaveItem(file_menu);
        addOpenDiagramItem(file_menu);

        JMenuItem build_pdf = new JMenuItem(GUI.MenuBar.PDF);
        build_pdf.addActionListener(actionEvent -> controller.compileAndOpen());
        file_menu.add(build_pdf);

        JMenuItem diff_item = new JMenuItem(GUI.MenuBar.DIFF);
        diff_item.addActionListener(actionEvent -> controller.openHistory());
        file_menu.add(diff_item);

        file_menu.addSeparator();

        JMenuItem exit_item = new JMenuItem(GUI.MenuBar.EXIT);
        exit_item.addActionListener(actionEvent -> controller.saveAndQuit(parentView));
        file_menu.add(exit_item);

        JMenu edit_menu = new JMenu(GUI.MenuBar.EDIT_MENU);
        this.add(edit_menu);

        addUndoItem(edit_menu);
        addRedoItem(edit_menu);

        JMenu help_menu = new JMenu(GUI.MenuBar.HELP_MENU);
        this.add(help_menu);

        JMenuItem help_item = new JMenuItem(GUI.MenuBar.HELP);
        help_item.addActionListener(actionEvent -> controller.showHelp());
        help_menu.add(help_item);

        JMenu options_menu = new JMenu(GUI.MenuBar.OPTIONS_MENU);
        this.add(options_menu);

        JMenuItem color_blind_mode_item = new JCheckBoxMenuItem(GUI.MenuBar.COLOR_BLIND);
        color_blind_mode_item.addItemListener(itemEvent -> controller.setColorBlindMode(itemEvent.getStateChange()));
        options_menu.add(color_blind_mode_item);

        JMenu sync_menu = new JMenu(GUI.MenuBar.SYNC);
        this.add(sync_menu);

        JMenuItem sync_item = new JMenuItem(GUI.MenuBar.SYNC + " Project");
        sync_item.addActionListener(actionEvent -> controller.syncProject());
        sync_menu.add(sync_item);
    }

    /**
     * Generates a JOptionPane to prompt the user to give a name to its diagram.
     * @return The diagram name inserted by the user
     */
    public String getDiagramName() {
        String path = null;
        while (path == null || path.equals("")) {
            path = JOptionPane.showInputDialog("Enter a diagram name");
        }
        return path;
    }

    /**
     * Calls the saveAndQuit function of the controller of this view. This will saveAndQuit
     * the tikz text into a file in the current diagram's file
     */
    public void saveAndQuit() {
        controller.saveAndQuit(parentView);
    }

    /**
     * Sets or unsets the colorblind mode
     * @param set_mode Boolean to set or unset
     */
    public void setBlindMode(boolean set_mode) {
        parentView.setTextAreaColorBlindMode(set_mode);
    }

    /**
     * Hides the parent view
     */
    public void disposeParent() {
        this.parentView.dispose();
    }

    /**
     * Gets the diagram being currently edited
     * @return The diagram being edited
     */
    public Diagram getDiagram() {
        return diagram;
    }

    public void syncOKPopup() {
        // TODO: Consants
        JOptionPane.showMessageDialog(this, "Sync is ok", "OK", JOptionPane.INFORMATION_MESSAGE);

    }
}
