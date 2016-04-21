package misc.drag.transferable;

import misc.drag.transfertdata.TransferTikz;
import models.tikz.TikzComponent;
import constants.GUI;

/**
 * Created by aurelien on 15/04/16.
 */
public class CanvasTransferableTikz extends TransferableTikz {

    public CanvasTransferableTikz(TikzComponent comp, GUI.Drag.DropOptions opt) {
        try {
            initializeData();
            transfert_data = new TransferTikz(comp, opt);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
