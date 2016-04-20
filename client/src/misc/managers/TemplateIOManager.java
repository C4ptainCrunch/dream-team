package misc.managers;

import constants.GUI;
import models.Template;
import models.tikz.TikzComponent;
import models.tikz.TikzGraph;
import models.tikz.TikzNode;
import views.management.FileChooseView;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by aurelien on 20/04/16.
 */
public class TemplateIOManager {

    public static File exportGraphAsTemplate(Collection<TikzComponent> components) throws IOException{
        FileChooseView file_view = new FileChooseView("Template filename", JFileChooser.FILES_AND_DIRECTORIES);
        TikzGraph g = new TikzGraph();
        for (TikzComponent comp: components){
            if (comp.isNode()){
                g.add((TikzNode) comp);
            }
        }
        File file = file_view.ask();
        Template template = new Template(g);
        template.saveTemplate(file);
        return file;
    }

    public static Template loadTemplate(File template_file) throws IOException{
        Template template = new Template();
        template.loadTemplate(template_file);
        return template;
    }

    public static List<Template> loadAllTemplatesFromDir() throws IOException{
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
