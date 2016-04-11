package gui.editor.toolbox.views;


import javax.swing.*;

public class ToolBoxView extends JPanel {

    private ToolView creator;


    public ToolBoxView(){
        creator = new ToolView();
        this.add(creator);
    }
}
