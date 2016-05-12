package misc.drag.transferable;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import misc.drag.transfereddata.TransferTikz;

/**
 * Implementation of the Transferable interface that can be used to provide Tikz
 * components for a transfer operation.
 */
public abstract class TransferableTikz implements Transferable {

    public DataFlavor data; // Generic type for identifying data.
    protected TransferTikz transfert_data; // The "real" object that will be
                                           // passed
                                           // through d&d.

    protected void initializeData() throws ClassNotFoundException {
        String mimeType = DataFlavor.javaJVMLocalObjectMimeType + ";class=" + TransferTikz.class.getName(); // Identifier
        // for
        // the
        // data
        // passed.

        data = new DataFlavor(mimeType);
    }

    /**
     * Returns an array of DataFlavor objects indicating the flavors the data
     * can be provided in.
     *
     * @return an array of string data flavors
     */
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[] { data, DataFlavor.stringFlavor };
    }

    /**
     * Returns whether or not the specified data flavor is supported for this
     * object.
     *
     * @param df
     *            the requested flavor for the data
     * @return true if the data flavor is supported
     */
    public boolean isDataFlavorSupported(final DataFlavor df) {
        return df.equals(data) || df.equals(DataFlavor.stringFlavor);
    }

    /**
     * Returns an object which represents the data to be transferred. The class
     * of the object returned is defined by the representation class of the
     * flavor.
     *
     * @param df
     *            the requested flavor for the data
     * @return The tikz component
     * @throws UnsupportedFlavorException
     *             - if the requested data flavor is not supported.
     * @throws IOException
     *             - if the data is no longer available in the requested flavor.
     */
    public Object getTransferData(final DataFlavor df) throws UnsupportedFlavorException, IOException {
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
