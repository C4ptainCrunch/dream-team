package views.editor.toolbox.templateview;

import misc.drag.handler.TemplateToolTransferHandler;
import models.Template;
import models.tikz.TikzGraph;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by aurelien on 20/04/16.
 */
public class TemplateList extends JList<Template> {

    private DefaultListModel<Template> template_list_model;

    public TemplateList(DefaultListModel<Template> model){
        super(model);
        template_list_model = model;
        initTemplatesList();
        enableDrag();
    }

    private void initTemplatesList(){
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.setLayoutOrientation(JList.VERTICAL);
        this.setCellRenderer(new TemplateCellRenderer());
    }

    private void enableDrag(){
        this.setTransferHandler(new TemplateToolTransferHandler());
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                if (SwingUtilities.isLeftMouseButton(mouseEvent)) {
                    TemplateList view = (TemplateList) mouseEvent.getSource();
                    TransferHandler handler = view.getTransferHandler();
                    handler.exportAsDrag(view, mouseEvent, TransferHandler.MOVE);
                }
            }
        });
    }

    public void addTemplateToList(Template template){
        template_list_model.addElement(template);
    }

    public TikzGraph getSelectedTemplateGraph(){
        Template template = template_list_model.get(this.getSelectedIndex());
        return template.getTemplateGraph();
    }

    public void deselectTemplate(){
        this.clearSelection();
    }
}