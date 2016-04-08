package gui.editor.views;

import constants.GUI;
import gui.editor.controllers.MenuController;
import models.TikzGraph;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuView extends JMenuBar {
    private MenuController controller;
    private EditorView parentView;
    private TikzGraph graph;
    private final String path;

    public MenuView(EditorView parentView, TikzGraph graph, String path){
        this.parentView = parentView;
        this.graph = graph;
        this.path = path;
        this.controller = new MenuController(this, graph);
        this.render();
    }

    private void render() {
        JMenu file_menu = new JMenu(GUI.Text.FILE_MENU);
        this.add(file_menu);

        JMenuItem save_item = new JMenuItem(GUI.Text.SAVE);
        save_item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.save();
            }
        });
        file_menu.add(save_item);

        JMenuItem build_pdf = new JMenuItem(GUI.Text.PDF);
        build_pdf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.compileAndOpen();
            }
        });
        file_menu.add(build_pdf);
        file_menu.addSeparator();

        JMenuItem exit_item = new JMenuItem(GUI.Text.EXIT);
        file_menu.add(exit_item);
    }

    public String getPath() {
        return path;
    }
}
