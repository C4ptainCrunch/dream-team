package gui;


import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;

public class HelpPanel extends JFrame {
    private JTree tree;
    private JTextArea helpText;

    public HelpPanel() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");

        DefaultMutableTreeNode shapesNode = new DefaultMutableTreeNode("Shapes");
        DefaultMutableTreeNode codeNode = new DefaultMutableTreeNode("Code");
        root.add(shapesNode);
        root.add(codeNode);

        this.setLayout(new GridLayout(1, 2));

        tree = new JTree(root);
        helpText = new JTextArea();
        add(tree);
        add(helpText);

        tree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                String filePath = "./help_files/"+ selectedNode.getUserObject().toString() + ".md";
                try{
                    helpText.read(new FileReader(filePath),null);
                }
                catch(IOException ioe){
                    // As always, no idea what to catch here...
                }
            }
        });

        this.setTitle("Help Panel");
        this.getContentPane().setPreferredSize(new Dimension(500, 300));
        this.pack();
        this.setVisible(true);
    }
}