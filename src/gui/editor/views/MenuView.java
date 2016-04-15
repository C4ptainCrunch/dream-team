package gui.editor.views;

import constants.GUI;
import gui.editor.controllers.MenuController;
import models.TikzGraph;

import javax.swing.*;


public class MenuView extends JMenuBar {
    private MenuController controller;
    private TikzGraph graph;
    private EditorView parentView;

    public MenuView(EditorView parentView, TikzGraph graph){
        this.parentView = parentView;
        this.graph = graph;
        this.controller = new MenuController(this, graph);
        this.render();
    }

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

    public void save(){
        controller.save();
    }

    public String getProjectPath() {
        return parentView.getProjectPath();
    }
}
