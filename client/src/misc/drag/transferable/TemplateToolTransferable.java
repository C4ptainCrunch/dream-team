package misc.drag.transferable;

import constants.GUI;
import misc.drag.transfertdata.TransferTikz;
import models.tikz.TikzGraph;

/**
 * Created by aurelien on 20/04/16.
 */
public class TemplateToolTransferable extends TransferableTikz {

    public TemplateToolTransferable(TikzGraph graph, GUI.Drag.DropOptions opt){
        try{
            initializeData();
            transfert_data = new TransferTikz(graph, opt);
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
