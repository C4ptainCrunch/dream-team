package help.views;

import help.controllers.HelpController;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class HelpView extends JFrame {
    private HelpController controller;
    private JTree tree;
    private JTextArea textArea;

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

        this.textArea = new JTextArea();
        this.add(textArea);

        this.pack();
    }

    public void setText(String text) {
        textArea.setText(text);
    }
}
