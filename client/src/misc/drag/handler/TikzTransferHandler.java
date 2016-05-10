package misc.drag.handler;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.*;

import misc.drag.transfereddata.TransferTikz;
import views.editor.CanvasView;

/**
 * Class used to handle the transfer of a TransferableTikz to and from Swing
 * components.
 */
public abstract class TikzTransferHandler extends TransferHandler {

    /**
     * This method is called repeatedly during a drag and drop operation to
     * allow the developer to configure properties of, and to return the
     * acceptability of transfers; with a return value of true indicating that
     * the transfer represented by the given TransferSupport (which contains all
     * of the details of the transfer) is acceptable at the current time, and a
     * value of false rejecting the transfer.
     *
     * @param info
     *            The object containing the details of the drag and drop
     *            transfer to be checked
     * @return true if the import can happen, false otherwise
     */
    public boolean canImport(TransferHandler.TransferSupport info) {
        return info.isDataFlavorSupported(DataFlavor.stringFlavor);
    }

    /**
     * Imports the data after a drop is detected
     *
     * @param support
     *            The object which contains the details of the drag and drop
     *            transfer
     * @return true if the import has been successful
     */
    public boolean importData(TransferHandler.TransferSupport support) {
        if (!canImport(support)) {
            return false;
        }

        Transferable data = support.getTransferable(); // The object passed
                                                       // through the d&d is an
                                                       // instance of
                                                       // Transferable.
        TransferTikz transfer_data = new TransferTikz();
        String mimeType = DataFlavor.javaJVMLocalObjectMimeType + ";class=" + TransferTikz.class.getName(); // Identifies
                                                                                                            // the
                                                                                                            // type
                                                                                                            // of
                                                                                                            // data
                                                                                                            // passed.
        try {
            DataFlavor import_data;
            import_data = new DataFlavor(mimeType);
            transfer_data = (TransferTikz) data.getTransferData(import_data);
        } catch (ClassNotFoundException | IOException | UnsupportedFlavorException e) {
            e.printStackTrace();
        }

        CanvasView view = (CanvasView) support.getComponent();
        view.dragEvent(transfer_data, support.getDropLocation().getDropPoint());

        return true;
    }

    /**
     * Action to do after the exportation (this defines what to do with the
     * source of the drad and drop)
     *
     * @param c
     *            The component that was the source of the data
     * @param t
     *            The data that was transferred or possibly null if the action
     *            is NONE
     * @param action
     *            the actual action that was performed
     */
    protected void exportDone(JComponent c, Transferable t, int action) {
        // this was left intentionally blank
    }

    /**
     * Create a Transferable Object from the source that will be retrieved by
     * the destination.
     *
     * @param c
     *            The component holding the data to be transferred, provided to
     *            enable sharing of TransferHandlers
     * @return the representation of the data to be transferred, or null if the
     *         property associated with c is null
     */

    protected abstract Transferable createTransferable(JComponent c);

    /**
     * Defines the type of Action (MOVE, COPY, COPY_OR_MOVE, etc..).
     *
     * @param c
     *            the component holding the data to be transferred; provided to
     *            enable sharing of TransferHandlers
     * @return MOVE
     */
    public int getSourceActions(JComponent c) {
        return MOVE;
    }
}
