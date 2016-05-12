package misc.drag.handler;

import java.awt.datatransfer.Transferable;

import javax.swing.*;

import misc.drag.transferable.PreviewTransferableTikz;
import views.editor.toolbox.PreviewView;
import constants.GUI;

public class PreviewTransferHandler extends TikzTransferHandler {

    protected Transferable createTransferable(final JComponent c) {
        return new PreviewTransferableTikz(((PreviewView) c).getComponentAsGraph(), GUI.Drag.DropOptions.ADD);
    }

    protected void exportDone(final JComponent c, final Transferable t, final int action) {
        ((PreviewView) c).reset();
    }
}
