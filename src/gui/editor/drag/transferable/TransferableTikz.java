package gui.editor.drag.transferable;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import constants.GUI;
import gui.editor.drag.transfertdata.TransferTikz;
import models.tikz.TikzComponent;

/**
 * Created by aurelien on 13/04/16.
 */
public abstract class TransferableTikz implements Transferable {

    public static DataFlavor data; // Generic type for identifying data.
    protected TransferTikz transfert_data; // The "real" object that will be passed
                                        // through d&d.

    protected TransferableTikz(){

    }

    protected void initializeDataAndComponent(TikzComponent comp, GUI.Drag.DropOptions opt){
        String mimeType = DataFlavor.javaJVMLocalObjectMimeType + ";class=" + TransferTikz.class.getName(); // Identifier
        // for
        // the
        // data
        // passed.
        try {
            data = new DataFlavor(mimeType);
            transfert_data = new TransferTikz(comp, opt);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected TransferableTikz(TikzComponent comp, GUI.Drag.DropOptions opt) {
        initializeDataAndComponent(comp, opt);
    }

    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[] { data, DataFlavor.stringFlavor };
    }

    public boolean isDataFlavorSupported(DataFlavor df) {
        return df.equals(data) || df.equals(DataFlavor.stringFlavor);
    }

    public Object getTransferData(DataFlavor df) throws UnsupportedFlavorException, IOException {
        if (df == null) {
            throw new IOException();
        }
        if (df.equals(data)) { // this checked if the identifier of the source
                                // is an particular object (cf. mimeType above).
            return transfert_data;
        }
        if (df.equals(DataFlavor.stringFlavor)) {
            return transfert_data.toString();
        } else {
            throw new UnsupportedFlavorException(df);
        }
    }
}
