package gui.editor.drag.handler;

import constants.GUI;
import gui.editor.drag.transferable.PreviewTransferableTikz;
import gui.editor.toolbox.views.PreviewView;

import javax.swing.*;
import java.awt.datatransfer.Transferable;

/**
 * Created by aurelien on 15/04/16.
 */
public class PreviewTransferHandler extends TikzTransferHandler {

    protected Transferable createTransferable(JComponent c) {
        return new PreviewTransferableTikz(((PreviewView) c).getComponentAsGraph(), GUI.Drag.DropOptions.ADD);
    }

    protected void exportDone(JComponent c, Transferable t, int action) {
        ((PreviewView)c).reset();
    }
}
