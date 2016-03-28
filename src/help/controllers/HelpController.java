package help.controllers;

import help.views.HelpView;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HelpController {
    private HelpView view;

    public HelpController(HelpView view) {
        this.view = view;
    }


    public void treeClicked(TreeSelectionEvent ev) {
        TreePath clickedNode = ev.getPath();

        // This is a simple map : [x.to_string() for x in clickedNode.getPath()]
        // We <3 Java
        List<String> path = Arrays.stream(clickedNode.getPath())
                .map(x -> x.toString())
                .collect(Collectors.toList());

        String filePath = "./help_files/" + String.join("/", path) + ".md";

        try{
            view.setText(new String(Files.readAllBytes(Paths.get(filePath))));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }


    public DefaultMutableTreeNode getTree() {
        DefaultMutableTreeNode tree = new DefaultMutableTreeNode("Help");

        DefaultMutableTreeNode shapesNode = new DefaultMutableTreeNode("Shapes");
        DefaultMutableTreeNode codeNode = new DefaultMutableTreeNode("Code");
        tree.add(shapesNode);
        tree.add(codeNode);

        return tree;
    }
}
