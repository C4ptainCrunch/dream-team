package views.editor.toolbox.templateview;

import constants.Errors;
import misc.drag.handler.TemplateToolTransferHandler;
import misc.managers.TemplateIOManager;
import models.Template;
import models.tikz.TikzGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * Created by mrmmtb on 19.04.16.
 */
public class TemplateToolView extends JPanel {

    private TemplateList templates;
    private JScrollPane scroll_zone;

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
