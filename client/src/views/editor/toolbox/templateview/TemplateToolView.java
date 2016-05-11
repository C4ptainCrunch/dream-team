package views.editor.toolbox.templateview;

import java.awt.*;
import java.io.File;

import javax.swing.*;

import models.Template;
import controllers.editor.toolbox.TemplateToolController;

/**
 * A View that will display all the Templates available.
 *
 * These templates can be drag and drop from this view to the CanvasView.
 */

public class TemplateToolView extends JPanel {

    private TemplateList templates;
    private JScrollPane scroll_zone;
    private TemplateToolController controller;

    /**
     * @name Default Constructor
     */

    public TemplateToolView() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        controller = new TemplateToolController(this);
        templates = controller.getTemplates();
        initScrollZone();
    }

    private void initScrollZone() {
        scroll_zone = new JScrollPane(templates);
        this.add(scroll_zone);
    }

    public void addTemplateFromFile(File file){
        controller.addTemplateFromFile(file);
    }
}

// source: https://docs.oracle.com/javase/8/docs/api/javax/swing/JList.html

class TemplateCellRenderer extends JLabel implements ListCellRenderer<Object> {

    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        String s = ((Template) value).getTemplateName();
        setText(s);
        if (isSelected) {
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
