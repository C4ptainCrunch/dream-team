package views.editor;

import javax.swing.*;

import models.project.Diagram;
import constants.GUI;
import controllers.editor.MenuController;

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
     *
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

    /**
     * Renders the view by setting the different buttons composing the menu
     */
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
        exit_item.addActionListener(actionEvent -> controller.saveAndQuit(parentView));
        file_menu.add(exit_item);

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

    public String getDiagramName() {
        String path = JOptionPane.showInputDialog("Enter a diagram name");
        return path;
    }

    /**
     * Calls the saveAndQuit function of the controller of this view. This will saveAndQuit
     * the tikz text into a file in the current diagram's file
     */
    public void saveAndQuit() {
        controller.saveAndQuit(parentView);
    }

    public void setBlindMode(boolean set_mode) {
        parentView.setTextAreaColorBlindMode(set_mode);
    }
}
