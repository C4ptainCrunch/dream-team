package gui.help.views;

import java.awt.*;

import javax.swing.*;

import org.pegdown.PegDownProcessor;

import gui.help.controllers.HelpController;

/**
 * Implementation of the View (from the MVC architectural pattern) for the Help.
 * The Help Displays a Tree architecture of help files that can be consulted
 */
public class HelpView extends JFrame {
    private final HelpController controller;
    private JTree tree;
    private JLabel htmlView;

    /**
     * Constructs a new view for the Help
     */
    public HelpView() {
        this.controller = new HelpController(this);
        this.render();
        this.addListeners();
        this.setVisible(true);
    }

    /**
     * Adds listeners for selected help files
     */
    private void addListeners() {
        this.tree.getSelectionModel().addTreeSelectionListener(this.controller::treeClicked);
    }

    /**
     * Renders the view with the tree help files
     */
    private void render() {
        this.setTitle("Help");
        this.getContentPane().setPreferredSize(new Dimension(600, 400));

        this.setLayout(new GridLayout(1, 2));

        this.tree = new JTree(controller.getTree());
        this.add(tree);

        this.htmlView = new JLabel();
        this.add(htmlView);

        this.pack();
    }

    /**
     * Setter for the text to be displayed that comes from the selected help file
     * @param text The help text
     */
    public void setText(String text) {
        PegDownProcessor pegDown = new PegDownProcessor();git status

        htmlView.setText("<html>" + pegDown.markdownToHtml(text) + "</html>");
    }
}
