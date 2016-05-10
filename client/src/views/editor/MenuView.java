package views.editor;

import javax.swing.*;

import models.project.Project;
import constants.GUI;
import controllers.editor.MenuController;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Implementation of the View (from the MVC architectural pattern) for the Menu.
 * The Menu is the menu bar of the GUI.
 */
public class MenuView extends JMenuBar {
    private final MenuController controller;
    private final Project project;
    private final EditorView parentView;

    /**
     * Constructs a new View for the Menu, with a given Project and parentView
     *
     * @param parentView
     *            The view which contains this view (ie. EditorView)
     * @param project
     *            The Project
     */
    public MenuView(EditorView parentView, Project project) {
        this.parentView = parentView;
        this.project = project;
        this.controller = new MenuController(this, project);
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
                project.undo();
            }
        };
        addShortcutItem(menu, action, GUI.MenuBar.UNDO, key, KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK);
    }

    private void addRedoItem(JMenu menu){
        String key = "Redo";
        Action action = new AbstractAction(key) {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                project.redo();
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

    /**
     * Renders the view by setting the different buttons composing the menu
     */
    private void render() {
        JMenu file_menu = new JMenu(GUI.MenuBar.FILE_MENU);
        this.add(file_menu);

        addSaveItem(file_menu);

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
    }

    /**
     * Calls the saveAndQuit function of the controller of this view. This will saveAndQuit
     * the tikz text into a file in the current project's file
     */
    public void saveAndQuit() {
        controller.saveAndQuit(parentView);
    }

    public void setBlindMode(boolean set_mode) {
        parentView.setTextAreaColorBlindMode(set_mode);
    }
}
