package gui.editor.drag;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import models.TikzComponent;

/**
 * Created by aurelien on 13/04/16.
 */
public class TransferableTikz implements Transferable {

    public static DataFlavor data; // Generic type for identifying data.
    private TikzComponent component; // The "real" object that will be passed
                                        // through d&d.

    public TransferableTikz(TikzComponent comp) {
        String mimeType = DataFlavor.javaJVMLocalObjectMimeType + ";class=" + TikzComponent.class.getName(); // Identifier
                                                                                                                // for
                                                                                                                // the
                                                                                                                // data
                                                                                                                // passed.
        try {
            data = new DataFlavor(mimeType);
            component = comp.getClone();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[] { data, DataFlavor.stringFlavor };
    }

    public boolean isDataFlavorSupported(DataFlavor df) {
        return df.equals(data)) || df.equals(DataFlavor.stringFlavor;
    }

    public Object getTransferData(DataFlavor df) throws UnsupportedFlavorException, IOException {
        if (df == null) {
            throw new IOException();
        }
        if (df.equals(data)) { // this checked if the identifier of the source
                                // is an particular object (cf. mimeType above).
            return component;
        }
        if (df.equals(DataFlavor.stringFlavor)) {
            return component.toString();
        } else {
            throw new UnsupportedFlavorException(df);
        }
    }

}
