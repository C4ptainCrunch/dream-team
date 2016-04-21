package misc.drag.handler;

import java.awt.datatransfer.Transferable;

import javax.swing.*;

import misc.drag.transferable.PreviewTransferableTikz;
import views.editor.toolbox.PreviewView;
import constants.GUI;

/**
 * Created by aurelien on 15/04/16.
 */
public class PreviewTransferHandler extends TikzTransferHandler {

    protected Transferable createTransferable(JComponent c) {
        return new PreviewTransferableTikz(((PreviewView) c).getComponentAsGraph(), GUI.Drag.DropOptions.ADD);
    }

    protected void exportDone(JComponent c, Transferable t, int action) {
        ((PreviewView) c).reset();
    }
}
