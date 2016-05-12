package misc.drag.transferable;

import misc.drag.transfereddata.TransferTikz;
import models.tikz.TikzComponent;
import constants.GUI;

public class CanvasTransferableTikz extends TransferableTikz {

    public CanvasTransferableTikz(final TikzComponent comp, final GUI.Drag.DropOptions opt) {
        try {
            initializeData();
            transfert_data = new TransferTikz(comp, opt);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
