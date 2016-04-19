package misc;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import models.tikz.TikzComponent;

/**
 * Implementation of the Transferable interface that can be used
 * to provide Tikz components for a transfer operation.
 */
public class TransferableTikz implements Transferable {

    public static DataFlavor data; // Generic type for identifying data.
    private TikzComponent component; // The "real" object that will be passed
                                        // through d&d.

    /**
     * Constructs a TransferableTikz with a given TikzComponent
     * @param comp the tikz component
     */
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

    /**
     * Returns an array of DataFlavor objects indicating the flavors the data can be provided in.
     * @return an array of string data flavors
     */
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[] { data, DataFlavor.stringFlavor };
    }

    /**
     * Returns whether or not the specified data flavor is supported for this object.
     * @param df the requested flavor for the data
     * @return true if the data flavor is supported
     */
    public boolean isDataFlavorSupported(DataFlavor df) {
        return df.equals(data) || df.equals(DataFlavor.stringFlavor);
    }

    /**
     * Returns an object which represents the data to be transferred. The class of the object returned is defined by the representation class of the flavor.
     * @param df the requested flavor for the data
     * @return The tikz component
     * @throws UnsupportedFlavorException - if the requested data flavor is not supported.
     * @throws IOException - if the data is no longer available in the requested flavor.
     */
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
