package gui.editor.views;

import javax.swing.*;

import models.TikzGraph;
import constants.GUI;
import gui.editor.controllers.MenuController;

/**
 * Implementation of the View (from the MVC architectural pattern) for the Menu.
 * The Menu is the menu bar of the GUI.
 */
public class MenuView extends JMenuBar {
    private final MenuController controller;
    private final TikzGraph graph;
    private final EditorView parentView;

    /**
     * Constructs a new View for the Menu,
     * with a given TikzGraph and parentView
     * @param parentView The view which contains this view (ie. EditorView)
     * @param graph The TikzGraph
     */
    public MenuView(EditorView parentView, TikzGraph graph) {
        this.parentView = parentView;
        this.graph = graph;
        this.controller = new MenuController(this, graph);
        this.render();
    }

    /**
     * Renders the view by setting the different buttons composing the menu
     */
    private void render() {
        JMenu file_menu = new JMenu(GUI.Text.FILE_MENU);
        this.add(file_menu);

        JMenuItem save_item = new JMenuItem(GUI.Text.SAVE);
        save_item.addActionListener(e -> controller.save());
        file_menu.add(save_item);

        JMenuItem build_pdf = new JMenuItem(GUI.Text.PDF);
        build_pdf.addActionListener(actionEvent -> controller.compileAndOpen());
        file_menu.add(build_pdf);

        JMenuItem diff_item = new JMenuItem(GUI.Text.DIFF);
        diff_item.addActionListener(actionEvent -> controller.openHistory());
        file_menu.add(diff_item);

        file_menu.addSeparator();

        JMenuItem exit_item = new JMenuItem(GUI.Text.EXIT);
        exit_item.addActionListener(actionEvent -> controller.exit(parentView));
        file_menu.add(exit_item);

        JMenu help_menu = new JMenu(GUI.Text.HELP_MENU);
        this.add(help_menu);

        JMenuItem help_item = new JMenuItem(GUI.Text.HELP);
        help_item.addActionListener(actionEvent -> controller.showHelp());
        help_menu.add(help_item);
    }

    /**
     * Calls the save function of the controller of this view.
     * This will save the tikz text into a file in the current project's file
     */
    public void save() {
        controller.save();
    }

    /**
     * Getter for the current project path
     * @return The path of the current project
     */
    public String getProjectPath() {
        return parentView.getProjectPath();
    }
}
