package misc.drag.handler;

import java.awt.datatransfer.Transferable;

import javax.swing.*;

import misc.drag.transferable.TemplateToolTransferable;
import views.editor.toolbox.templateview.TemplateList;
import constants.GUI;

public class TemplateToolTransferHandler extends TikzTransferHandler {

    protected Transferable createTransferable(JComponent c) {
        return new TemplateToolTransferable(((TemplateList) c).getSelectedTemplateGraphClone(), GUI.Drag.DropOptions.ADD);
    }

    protected void exportDone(JComponent c, Transferable t, int action) {
        ((TemplateList) c).deselectTemplate();
    }
}
