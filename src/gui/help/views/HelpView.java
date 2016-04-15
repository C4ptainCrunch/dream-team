package gui.help.views;

import java.awt.*;

import javax.swing.*;

import org.pegdown.PegDownProcessor;

import gui.help.controllers.HelpController;

public class HelpView extends JFrame {
    private HelpController controller;
    private JTree tree;
    private JLabel htmlView;

    public HelpView() {
        this.controller = new HelpController(this);
        this.render();
        this.addListeners();
        this.setVisible(true);
    }

    private void addListeners() {
        this.tree.getSelectionModel().addTreeSelectionListener(this.controller::treeClicked);
    }

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

    public void setText(String text) {
        PegDownProcessor pegDown = new PegDownProcessor();
        htmlView.setText("<html>" + pegDown.markdownToHtml(text) + "</html>");
    }
}
