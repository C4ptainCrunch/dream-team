package controllers.help;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import utils.Log;
import views.help.HelpView;

/**
 * Implementation of the Controller (from the MVC architectural pattern) for the
 * Help. The Help Displays a Tree architecture of help files that can be
 * consulted
 */
public class HelpController {
    private final static String HELP_ROOT = "help_files/";
    private final static Logger logger = Log.getLogger(HelpController.class);
    private final HelpView view;

    /**
     * Constructs a new controller for the Help with a given view
     *
     * @param view
     *            The HelpView which is associated with this controller
     */
    public HelpController(HelpView view) {
        this.view = view;
    }

    /**
     * Displays the help text depending on the file tree selected with a given
     * tree selection event
     *
     * @param ev
     *            The tree selection event
     */
    public void treeClicked(TreeSelectionEvent ev) {
        TreePath clickedNode = ev.getPath();

        // This is a simple map : [x.to_string() for x in
        // clickedNode.getProjectPath()]
        // We <3 Java
        List<String> path = Arrays.stream(clickedNode.getPath()).map(Object::toString).collect(Collectors.toList());

        String filePath = HELP_ROOT + String.join("/", path) + ".md";

        InputStream stream = HelpController.class.getClassLoader().getResourceAsStream(filePath);
        Scanner scanner = new Scanner(stream);
        String text = scanner.useDelimiter("\\A").next();
        view.setText(text);
    }

    /**
     * Getter for the help tree
     *
     * @return The help tree
     */
    public DefaultMutableTreeNode getTree() {
        return Filewalker.walkToTree(HELP_ROOT + "Help");
    }
}

/**
 *
 */
class Filewalker {
    /**
     * Constructs a FileWalker
     */
    private Filewalker() {
    }

    /**
     * Searches for a specific help file with a given path
     *
     * @param path
     *            The path
     * @return The found tree node
     */
    public static DefaultMutableTreeNode walkToTree(String path) {
        File root = new File(path);
        File[] list = root.listFiles();

        DefaultMutableTreeNode out = new DefaultMutableTreeNode(root.getName());
        if (list == null) {
            return out;
        }

        for (File f : list) {
            if (f.isDirectory()) {
                out.add(walkToTree(f.getAbsolutePath()));
            } else {
                String name = f.getName();
                int dot = name.lastIndexOf('.');
                if (dot > 0) {
                    name = name.substring(0, dot);
                    File equivalentDir = new File(f.getParent(), name);
                    if (!equivalentDir.isDirectory()) {
                        out.add(new DefaultMutableTreeNode(name));
                    }
                } else {
                    out.add(new DefaultMutableTreeNode(name));
                }

            }
        }

        return out;
    }

}
