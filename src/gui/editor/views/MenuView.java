package gui.editor.views;

import javax.swing.*;

import models.Project;
import models.tikz.TikzGraph;
import constants.GUI;
import gui.editor.controllers.MenuController;

public class MenuView extends JMenuBar {
    private final MenuController controller;
    private final Project project;
    private final EditorView parentView;

    public MenuView(EditorView parentView, Project project) {
        this.parentView = parentView;
        this.project = project;
        this.controller = new MenuController(this, project);
        this.render();
    }

    private void render() {
        JMenu file_menu = new JMenu(GUI.MenuBar.FILE_MENU);
        this.add(file_menu);

        JMenuItem save_item = new JMenuItem(GUI.MenuBar.SAVE);
        save_item.addActionListener(e -> controller.save());
        file_menu.add(save_item);

        JMenuItem build_pdf = new JMenuItem(GUI.MenuBar.PDF);
        build_pdf.addActionListener(actionEvent -> controller.compileAndOpen());
        file_menu.add(build_pdf);

        JMenuItem diff_item = new JMenuItem(GUI.MenuBar.DIFF);
        diff_item.addActionListener(actionEvent -> controller.openHistory());
        file_menu.add(diff_item);

        file_menu.addSeparator();

        JMenuItem exit_item = new JMenuItem(GUI.MenuBar.EXIT);
        exit_item.addActionListener(actionEvent -> controller.exit(parentView));
        file_menu.add(exit_item);

        JMenu help_menu = new JMenu(GUI.MenuBar.HELP_MENU);
        this.add(help_menu);

        JMenuItem help_item = new JMenuItem(GUI.MenuBar.HELP);
        help_item.addActionListener(actionEvent -> controller.showHelp());
        help_menu.add(help_item);
    }

    public void save() {
        controller.save();
    }
}
