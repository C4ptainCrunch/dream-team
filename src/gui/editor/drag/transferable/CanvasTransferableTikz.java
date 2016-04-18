package gui.editor.drag.transferable;

import constants.GUI;
import models.tikz.TikzComponent;

/**
 * Created by aurelien on 15/04/16.
 */
public class CanvasTransferableTikz extends TransferableTikz {

    public CanvasTransferableTikz(TikzComponent comp, GUI.Drag.DropOptions opt) {
        super(comp, opt);
    }
}
