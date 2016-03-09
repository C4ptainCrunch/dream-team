package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Menu extends JMenuBar{
    private static final String FILE_MENU = "File";
    private static final String SAVE = "Save";
    private static final String EXIT = "Exit";
    private static final String VIEW_MENU = "View";
    private static final String GRID_VISIBILTY = "Show/Hide Grid";

    private JMenu file_menu;
    private JMenuItem save_item;
    private JMenuItem exit_item;
    private JMenu view_menu;
    private JCheckBoxMenuItem grid_item;
    private onItemClickListener listener;

    public Menu(onItemClickListener listen){
        initFileMenu();
        initViewMenu();
        addListeners();
        listener = listen;
    }

    private void initFileMenu(){
        file_menu = new JMenu(FILE_MENU);
        this.add(file_menu);
        save_item = new JMenuItem(SAVE);
        exit_item = new JMenuItem(EXIT);
        file_menu.add(save_item);
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

    public interface onItemClickListener{
        public void onExit();
        public void onShowGrid();
        public void onHideGrid();
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

    private void addListeners(){
        addExitListener();
        addGridListener();
    }
}
