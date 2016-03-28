package help.controllers;

import help.views.HelpView;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class HelpController {
    private HelpView view;
    private final static String HELP_ROOT = "./assets/help_files/";

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

        String filePath = HELP_ROOT + String.join("/", path) + ".md";

        try{
            view.setText(new String(Files.readAllBytes(Paths.get(filePath))));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }


    public DefaultMutableTreeNode getTree() {
        return Filewalker.walkToTree(HELP_ROOT + "Help");
    }
}

class Filewalker {
    public static DefaultMutableTreeNode walkToTree(String path) {
        File root = new File(path);
        File[] list = root.listFiles();

        DefaultMutableTreeNode out = new DefaultMutableTreeNode(root.getName());
        if (list == null) return out;

        for ( File f : list ) {
            if ( f.isDirectory() ) {
                out.add(walkToTree(f.getAbsolutePath()));
            }
            else {
                String name = f.getName();
                int dot = name.lastIndexOf('.');
                if (dot > 0) {
                    name = name.substring(0, dot);
                    File equivalentDir = new File(f.getParent(), name);
                    if(!equivalentDir.isDirectory()){
                        out.add(new DefaultMutableTreeNode(name));
                    }
                }
                else{
                    out.add(new DefaultMutableTreeNode(name));
                }

            }
        }

        return out;
    }

}
