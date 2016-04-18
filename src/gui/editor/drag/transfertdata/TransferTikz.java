package gui.editor.drag.transfertdata;

import constants.GUI;
import models.tikz.TikzComponent;

/**
 * Created by aurelien on 18/04/16.
 */
public class TransferTikz {

    private TikzComponent component;
    private GUI.Drag.DropOptions option;

    public TransferTikz(){
        component = null;
        option = null;
    }

    public TransferTikz(TikzComponent comp, GUI.Drag.DropOptions opt){
        component = comp;
        option = opt;
    }

    public TikzComponent getComponent(){
        return component;
    }

    public GUI.Drag.DropOptions getOption(){
        return option;
    }

}
