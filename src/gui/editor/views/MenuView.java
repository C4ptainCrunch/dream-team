package gui.editor.views;

import constants.GUI;
import models.TikzGraph;

import javax.swing.*;

public class MenuView extends JMenuBar {
    private TikzGraph graph;

    public MenuView(TikzGraph graph){
        this.graph = graph;
        this.render();
    }

    private void render() {
        JMenu file_menu = new JMenu(GUI.Text.FILE_MENU);
        this.add(file_menu);

        JMenuItem save_item = new JMenuItem(GUI.Text.SAVE);
        file_menu.add(save_item);

        JMenuItem build_pdf = new JMenuItem(GUI.Text.PDF);
        file_menu.add(build_pdf);
        file_menu.addSeparator();

        JMenuItem exit_item = new JMenuItem(GUI.Text.EXIT);
        file_menu.add(exit_item);
    }
}
