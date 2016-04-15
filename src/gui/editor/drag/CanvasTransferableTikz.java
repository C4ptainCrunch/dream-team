package gui.editor.drag;

import models.TikzComponent;

import java.awt.datatransfer.DataFlavor;

/**
 * Created by aurelien on 15/04/16.
 */
public class CanvasTransferableTikz extends TransferableTikz {

    public CanvasTransferableTikz(TikzComponent comp) {
        String mimeType = DataFlavor.javaJVMLocalObjectMimeType + ";class=" + TikzComponent.class.getName(); // Identifier
        try {
            data = new DataFlavor(mimeType);
            component = comp;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
