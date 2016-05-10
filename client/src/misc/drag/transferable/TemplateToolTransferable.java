package misc.drag.transferable;

import misc.drag.transfereddata.TransferTikz;
import models.tikz.TikzGraph;
import constants.GUI;

public class TemplateToolTransferable extends TransferableTikz {

    public TemplateToolTransferable(TikzGraph graph, GUI.Drag.DropOptions opt) {
        try {
            initializeData();
            transfert_data = new TransferTikz(graph, opt);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
