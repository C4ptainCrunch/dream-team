package gui.editor.drag.handler;

import constants.GUI;
import gui.editor.drag.transferable.CanvasTransferableTikz;
import gui.editor.views.CanvasView;

import javax.swing.*;
import java.awt.datatransfer.Transferable;

/**
 * Created by aurelien on 15/04/16.
 */
public class CanvasTransferHandler extends TikzTransferHandler {

    // Create a Transferable Object from the source that will be retrieved by
    // the destination.

    @Override
    protected Transferable createTransferable(JComponent c) {
        return new CanvasTransferableTikz(((CanvasView) c).getSelectedComponent(), GUI.Drag.DropOptions.MOVE);
    }
}
