package misc.drag.transferable;

import misc.drag.transfereddata.TransferTikz;
import models.tikz.TikzGraph;
import constants.GUI;

public class PreviewTransferableTikz extends TransferableTikz {

    public PreviewTransferableTikz(final TikzGraph graph, final GUI.Drag.DropOptions opt) {
        try {
            initializeData();
            transfert_data = new TransferTikz(graph, opt);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
