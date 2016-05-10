package misc.drag.handler;

import java.awt.datatransfer.Transferable;

import javax.swing.*;

import misc.drag.transferable.CanvasTransferableTikz;
import views.editor.CanvasView;
import constants.GUI;

public class CanvasTransferHandler extends TikzTransferHandler {

    // Create a Transferable Object from the source that will be retrieved by
    // the destination.

    @Override
    protected Transferable createTransferable(JComponent c) {
        return new CanvasTransferableTikz(((CanvasView) c).getSelectedComponent(), GUI.Drag.DropOptions.MOVE);
    }
}
