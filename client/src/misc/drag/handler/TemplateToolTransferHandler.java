package misc.drag.handler;

import java.awt.datatransfer.Transferable;

import javax.swing.*;

import misc.drag.transferable.TemplateToolTransferable;
import views.editor.toolbox.templateview.TemplateList;
import constants.GUI;

public class TemplateToolTransferHandler extends TikzTransferHandler {

    protected Transferable createTransferable(final JComponent c) {
        return new TemplateToolTransferable(((TemplateList) c).getSelectedTemplateGraphClone(), GUI.Drag.DropOptions.ADD);
    }

    protected void exportDone(final JComponent c, final Transferable t, final int action) {
        ((TemplateList) c).deselectTemplate();
    }
}
