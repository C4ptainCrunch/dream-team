package gui.editor.drag;

import gui.editor.views.CanvasView;

import javax.swing.*;
import java.awt.datatransfer.Transferable;

/**
 * Created by aurelien on 15/04/16.
 */
public class CanvasTransfertHandler extends TikzTransferHandler {

    // Create a Transferable Object from the source that will be retrieved by
    // the destination.

    @Override
    protected Transferable createTransferable(JComponent c) {
        return new CanvasTransferableTikz(((CanvasView) c).getSelectedComponent());
    }
}
