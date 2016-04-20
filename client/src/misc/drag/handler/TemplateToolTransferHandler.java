package misc.drag.handler;

import constants.GUI;
import misc.drag.transferable.TemplateToolTransferable;
import views.editor.toolbox.templateview.TemplateList;

import javax.swing.*;
import java.awt.datatransfer.Transferable;

/**
 * Created by aurelien on 20/04/16.
 */
public class TemplateToolTransferHandler extends TikzTransferHandler {

    protected Transferable createTransferable(JComponent c) {
        return new TemplateToolTransferable(((TemplateList) c).getSelectedTemplateGraphClone(), GUI.Drag.DropOptions.ADD);
    }

    protected void exportDone(JComponent c, Transferable t, int action) {
        ((TemplateList)c).deselectTemplate();
    }
}
