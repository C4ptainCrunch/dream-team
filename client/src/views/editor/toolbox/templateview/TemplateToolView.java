package views.editor.toolbox.templateview;

import constants.Errors;
import constants.GUI;
import misc.managers.TemplateIOManager;
import models.Template;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A View that will display all the Templates available.
 *
 * These templates can be drag and drop from this view to the CanvasView.
 */

// TODO: Create a controller for this view.

public class TemplateToolView extends JPanel {

    private TemplateList templates;
    private JScrollPane scroll_zone;

    /**
     * @name Default Constructor
     */

    public TemplateToolView(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        templates = new TemplateList(new DefaultListModel<>());
        initScrollZone();
        addTemplatesToList();
    }

    private void initScrollZone(){
        scroll_zone = new JScrollPane(templates);
        this.add(scroll_zone);
    }

    private void addTemplatesToList(){
        try {
            for (Template temp : TemplateIOManager.loadAllTemplatesFromDir()) {
                templates.addTemplateToList(temp);
            }
        } catch (IOException e){
            JOptionPane.showMessageDialog(this, Errors.LOAD_TEMPLATES_ERROR, Errors.ERROR, JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Add a Template Object to the TemplateList from a File.
      * @param file The file where the Template is saved.
     */

    public void addTemplateFromFile(File file){
        try{
            Path p = Paths.get(GUI.Template.DIR);
            p = p.resolve(file.getName());
            templates.addTemplateToList(TemplateIOManager.loadTemplate(p.toFile()));
        } catch (IOException e){
            JOptionPane.showMessageDialog(this, Errors.LOAD_TEMPLATES_ERROR, Errors.ERROR, JOptionPane.ERROR_MESSAGE);
        }

    }
}

// source: https://docs.oracle.com/javase/8/docs/api/javax/swing/JList.html

class TemplateCellRenderer extends JLabel implements ListCellRenderer<Object>{

    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus){
        String s = ((Template) value).getTemplateName();
        setText(s);
        if (isSelected){
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        setEnabled(list.isEnabled());
        setFont(list.getFont());
        setOpaque(true);
        return this;
    }
}
