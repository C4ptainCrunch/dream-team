package gui.editor.drag.transfertdata;

import constants.GUI;
import models.tikz.TikzComponent;

/**
 * A TransferTikz is an object that will be transferred through drag and drop.
 */

public class TransferTikz {

    private TikzComponent component;
    private GUI.Drag.DropOptions option;

    /**
     * Default constructor.
     */

    public TransferTikz(){
        component = null;
        option = null;
    }

    /**
     * Constructor taking two parameters.
     *
     * As one can see, there are no setters methods defined in this class.
     * As so, the only way to initialize private data members is to call this constructor.
     *
     * @param comp The component that will be transferred through drag an drop.
     * @param opt An option defining which drop process will be done.
     */

    public TransferTikz(TikzComponent comp, GUI.Drag.DropOptions opt){
        component = comp;
        option = opt;
    }

    /**
     * Getter for the component.
     *
     * @return A TikzComponent passed through drag and drop
     */

    public TikzComponent getComponent(){
        return component;
    }

    /**
     * Getter for the drop option.
     *
     * @return The drop option defining the drop process.
     */
    public GUI.Drag.DropOptions getOption(){
        return option;
    }

}
