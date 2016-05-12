package misc.drag.transfereddata;

import models.tikz.TikzComponent;
import models.tikz.TikzGraph;
import constants.GUI;

/**
 * A TransferTikz is an object that will be transferred through drag and drop.
 */

public class TransferTikz {

    private TikzComponent component;
    private GUI.Drag.DropOptions option;
    private TikzGraph graph;

    /**
     * Default constructor.
     */

    public TransferTikz() {
        component = null;
        option = null;
    }

    /**
     * Constructor taking two parameters.
     *
     * As one can see, there are no setter methods defined in this class. As so,
     * the only way to initialize private data members is by calling this
     * constructor.
     *
     * @param comp
     *            The component that will be transferred through drag an drop.
     * @param opt
     *            An option defining which drop process will be done.
     */

    public TransferTikz(final TikzComponent comp, final GUI.Drag.DropOptions opt) {
        this.component = comp;
        this.option = opt;
        this.graph = null;
    }

    public TransferTikz(final TikzGraph graph, final GUI.Drag.DropOptions opt) {
        this.component = null;
        this.graph = graph;
        this.option = opt;
    }

    /**
     * Getter for the component.
     *
     * @return A TikzComponent passed through drag and drop
     */

    public TikzComponent getComponent() {
        return component;
    }

    public TikzGraph getGraph() {
        return graph;
    }

    /**
     * Getter for the drop option.
     *
     * @return The drop option defining the drop process.
     */
    public GUI.Drag.DropOptions getOption() {
        return option;
    }

}
