package views.editor.toolbox.templateview;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import misc.drag.handler.TemplateToolTransferHandler;
import models.Template;
import models.tikz.TikzGraph;

/**
 * A subclass of JList defined for stocking Template Object.
 */

public class TemplateList extends JList<Template> {

    private DefaultListModel<Template> template_list_model;

    /**
     * Default Constructor.
     *
     * @param model
     *            A DefaultListModel that will contain all the Template objects.
     */

    public TemplateList(DefaultListModel<Template> model) {
        super(model);
        template_list_model = model;
        initTemplatesList();
        enableDrag();
    }

    private void initTemplatesList() {
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.setLayoutOrientation(JList.VERTICAL);
        this.setCellRenderer(new TemplateCellRenderer());
    }

    private void enableDrag() {
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

    /**
     * Add a Template to the DefaultListModel.
     *
     * @param template
     *            The Template to add.
     */

    public void addTemplateToList(Template template) {
        template_list_model.addElement(template);
    }

    /**
     * Get the TikzGraph from the selected Template.
     *
     * @return A TikzGraph.
     */

    public TikzGraph getSelectedTemplateGraphClone() {
        Template template = template_list_model.get(this.getSelectedIndex());
        TikzGraph g = template.getGraph();
        return g.getClone();
    }

    /**
     * Reset the JList's selection.
     */

    public void deselectTemplate() {
        this.clearSelection();
    }
}
