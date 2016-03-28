package gui.help.views;

import gui.help.controllers.HelpController;
import org.pegdown.PegDownProcessor;

import javax.swing.*;
import java.awt.*;

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
        this.tree.getSelectionModel().addTreeSelectionListener(e -> {
            this.controller.treeClicked(e);
        });
    }

    private void render() {
        this.setTitle("Help");
        this.getContentPane().setPreferredSize(new Dimension(500, 300));

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
