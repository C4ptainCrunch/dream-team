package controllers.editor.toolbox;

import constants.Errors;
import constants.GUI;
import misc.managers.TemplateIOManager;
import models.Template;
import views.editor.toolbox.templateview.TemplateList;
import views.editor.toolbox.templateview.TemplateToolView;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by aurelien on 21/04/16.
 */
public class TemplateToolController {

    private TemplateToolView view;
    private TemplateList templates;

    public TemplateToolController(TemplateToolView v){
        this.view = v;
        templates = new TemplateList(new DefaultListModel<>());
        addTemplatesToList();

    }

    /**
     * Add All the templates in a constant dir to the JList.
     */

    public void addTemplatesToList() {
        try {
            for (Template temp : TemplateIOManager.loadAllTemplatesFromDir()) {
                templates.addTemplateToList(temp);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(view, Errors.LOAD_TEMPLATES_ERROR, Errors.ERROR, JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Add a Template Object to the TemplateList from a File.
     *
     * @param file
     *            The file where the Template is saved.
     */

    public void addTemplateFromFile(File file) {
        try {
            Path p = Paths.get(GUI.Template.DIR);
            p = p.resolve(file.getName());
            templates.addTemplateToList(TemplateIOManager.loadTemplate(p.toFile()));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(view, Errors.LOAD_TEMPLATES_ERROR, Errors.ERROR, JOptionPane.ERROR_MESSAGE);
        }
    }

    public TemplateList getTemplates(){
        return templates;
    }
}
