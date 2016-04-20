package misc.managers;

import constants.Errors;
import models.Template;
import models.tikz.TikzComponent;
import models.tikz.TikzGraph;
import models.tikz.TikzNode;
import views.management.FileChooseView;

import javax.swing.*;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by aurelien on 20/04/16.
 */
public class TemplateIOManager {

    public static void exportGraphAsTemplate(Collection<TikzComponent> components) throws IOException{
        FileChooseView file_view = new FileChooseView("Template filename", JFileChooser.FILES_AND_DIRECTORIES);
        TikzGraph g = new TikzGraph();
        for (TikzComponent comp: components){
            if (comp instanceof TikzNode){      //TODO: Refactor this;
                g.add((TikzNode) comp);
            }
        }
        Template template = new Template(g);
        template.saveTemplate(file_view.ask());
    }
}
