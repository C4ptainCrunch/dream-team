package gui.editor.drag;

import gui.editor.views.CanvasView;
import gui.editor.toolbox.views.PreviewView;
import models.TikzComponent;
import models.TikzVoid;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * Created by aurelien on 13/04/16.
 */
public class TikzTransferHandler extends TransferHandler {


    // Check if the data imported are valid (according to the TransferHandler doc).
    public boolean canImport(TransferHandler.TransferSupport info) {
        if (!info.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            return false;
        }
        return true;
    }

    // Import the data after a drop is detected.
    public boolean importData(TransferHandler.TransferSupport support){
        if(!canImport(support))
            return false;

        Transferable data = support.getTransferable();  // The object passed through the d&d is an instance of Transferable.
        TikzComponent component = new TikzVoid();
        String mimeType = DataFlavor.javaJVMLocalObjectMimeType + ";class=" + TikzComponent.class.getName(); // Identifies the type of data passed.
        try{
            DataFlavor import_data;
            import_data = new DataFlavor(mimeType);
            component = (TikzComponent) data.getTransferData(import_data);
        } catch (ClassNotFoundException|IOException|UnsupportedFlavorException e){
            e.printStackTrace();
        }

        CanvasView view = (CanvasView) support.getComponent();
        view.dragEvent(component, support.getDropLocation().getDropPoint());

        return true;
    }

    // Action to do after the exportation (this defines what to do with the source of the d&d).
    protected void exportDone(JComponent c, Transferable t, int action){}

    // Create a Transferable Object from the source that will be retrieved by the destination.
    protected Transferable createTransferable(JComponent c) {
        return new TransferableTikz(((PreviewView)c).getComponent());
    }

    // Defines the type of Action (MOVE, COPY, COPY_OR_MOVE, etc..).
    public int getSourceActions(JComponent c) {
        return MOVE;
    }
}
