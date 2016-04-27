package misc.managers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.*;

import models.Template;
import models.tikz.TikzComponent;
import models.tikz.TikzEdge;
import models.tikz.TikzGraph;
import models.tikz.TikzNode;
import views.management.FileChooseView;
import constants.GUI;

/**
 * This class manages all the template IO operations (i.e. save a Template
 * object into a file and load a Template object from a file.
 *
 * This class is a static class.
 */

public class TemplateIOManager {

    private static void moveTemplateGraphToOrigin(TikzGraph g) {
        List<TikzNode> comps = g.getNodes();
        TikzNode first_node = comps.get(0);
        int origin_delta_x = -((int) first_node.getPosition().getX());
        int origin_delta_y = -((int) first_node.getPosition().getY());
        g.translation(origin_delta_x, origin_delta_y);

    }

    /**
     * Cast a TikzGraph (represented as a Collection of TikzComponents) into a
     * Template object and save it into a file.
     *
     * @param components
     *            The collection representing the TikzGraph
     * @return The file which the graph will be saved in.
     * @throws IOException
     *             thrown if an error occurred during the saving process.
     */

    public static File exportGraphAsTemplate(Collection<TikzComponent> components) throws IOException {
        TikzGraph g = new TikzGraph();
        for (TikzComponent comp : components) {
            if (comp != null) {
                if (comp.isNode()) {
                    g.add((TikzNode) comp);
                } else if (comp.isEdge()){
                    g.add((TikzEdge) comp);
                }
            }
        }
        moveTemplateGraphToOrigin(g.getClone());
        String name = JOptionPane.showInputDialog(new JFrame(), "Enter template name:");
        Template template = new Template(g);
        return template.saveTemplate(name);
    }

    /**
     * Load a Template from a file.
     *
     * @param template_file
     *            The file which load the Template from.
     * @return The Template loaded
     * @throws IOException
     *             thrown if the loading process failed.
     */

    public static Template loadTemplate(File template_file) throws IOException {
        Template template = new Template();
        template.loadTemplate(template_file);
        return template;
    }

    /**
     * Load all the Templates from all the files in a specific directory
     * (defined in as a constant).
     *
     * @return A List of loaded Templates.
     * @throws IOException
     *             thrown if one of the loading process failed.
     */

    public static List<Template> loadAllTemplatesFromDir() throws IOException {
        ArrayList<Template> templates = new ArrayList<>();
        File dir = new File(GUI.Template.DIR);
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                templates.add(loadTemplate(file));
            }
        }
        return templates;
    }

}
