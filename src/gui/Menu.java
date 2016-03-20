package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Menu extends JMenuBar{
    private static final String FILE_MENU = "File";
    private static final String SAVE = "Save";
    private static final String PDF = "Build PDF";
    private static final String EXIT = "Exit";
    private static final String VIEW_MENU = "View";
    private static final String GRID_VISIBILTY = "Show/Hide Grid";
    private static final String HELP_MENU = "Help";
    private static final String HELP = "See help panel";

    private JMenu file_menu;
    private JMenuItem save_item;
    private JMenuItem build_pdf;
    private JMenuItem exit_item;
    private JMenu view_menu;
    private JCheckBoxMenuItem grid_item;
    private JMenu help_menu;
    private JMenuItem help_panel;
    private onItemClickListener listener;

    public Menu(onItemClickListener listen){
        initFileMenu();
        initViewMenu();
        initHelpMenu();
        addListeners();
        listener = listen;
    }

    private void initFileMenu(){
        file_menu = new JMenu(FILE_MENU);
        this.add(file_menu);
        save_item = new JMenuItem(SAVE);
        exit_item = new JMenuItem(EXIT);
        build_pdf = new JMenuItem(PDF);
        file_menu.add(save_item);
        file_menu.addSeparator();
        file_menu.add(build_pdf);
        file_menu.addSeparator();
        file_menu.add(exit_item);
    }

    private void initViewMenu(){
        view_menu = new JMenu(VIEW_MENU);
        this.add(view_menu);
        grid_item = new JCheckBoxMenuItem(GRID_VISIBILTY);
        grid_item.setSelected(true);
        view_menu.add(grid_item);
    }

    private void initHelpMenu(){
        help_menu = new JMenu(HELP_MENU);
        this.add(help_menu);
        help_panel = new JMenuItem(HELP);
        help_menu.add(help_panel);
    }

    public interface onItemClickListener{
        public void onExit();
        public void onShowGrid();
        public void onHideGrid();
        public void onBuildPDF();
        public void onHelp();
    }

    private void addExitListener(){
        exit_item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                listener.onExit();
            }
        });
    }

    private void addGridListener(){
        grid_item.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getStateChange() == ItemEvent.SELECTED){
                    listener.onShowGrid();
                }
                else if (itemEvent.getStateChange() == ItemEvent.DESELECTED){
                    listener.onHideGrid();
                }
            }
        });
    }

    private void addBuildPDFListener(){
        build_pdf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                listener.onBuildPDF();
            }
        });
    }

    private void addHelpListener(){
        help_panel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                listener.onHelp();
            }
        });
    }

    private void addListeners(){
        addExitListener();
        addGridListener();
        addBuildPDFListener();
        addHelpListener();
    }
}
