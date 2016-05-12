package controllers.help;

import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
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
    public HelpController(final HelpView view) {
        this.view = view;
    }

    /**
     * Displays the help text depending on the file tree selected with a given
     * tree selection event
     *
     * @param ev
     *            The tree selection event
     */
    public void treeClicked(final TreeSelectionEvent ev) {
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
    public static DefaultMutableTreeNode walkToTree(final String path) {
        InputStream stream = HelpController.class.getClassLoader().getResourceAsStream(path);
        Scanner scanner = new Scanner(stream);
        scanner.useDelimiter("\\n");
        List<String> children = new ArrayList<>();

        String root = URLDecoder.decode(HelpController.class.getClassLoader().getResource(path).getFile());
        int pos = root.lastIndexOf('/');
        root = root.substring(pos + 1);
        DefaultMutableTreeNode out = new DefaultMutableTreeNode(root);

        while (scanner.hasNext()) {
            children.add(scanner.next());
        }

        for (String child : children) {
            if (child.endsWith(".md")) {
                int dot = child.lastIndexOf('.');
                String name = child.substring(0, dot);
                if (!children.contains(name)) {
                    out.add(new DefaultMutableTreeNode(name));
                }
            } else {
                out.add(walkToTree(path + "/" + child));
            }
        }
        return out;
    }
}
