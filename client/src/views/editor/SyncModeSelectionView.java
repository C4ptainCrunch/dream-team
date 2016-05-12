package views.editor;

import constants.Errors;
import controllers.editor.SyncModeSelectionController;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class SyncModeSelectionView extends JFrame {

    private SyncModeSelectionController controller;
    private String flag;
    private JList<String> optionsList;
    private Vector<String> optionNames;


    public SyncModeSelectionView(String flag) {
        this.controller = new SyncModeSelectionController(this);
        this.flag = flag;
        this.render();
    }

    private void render() {
        this.setTitle("TikzCreator : choose a sync method");
        this.setPreferredSize(new Dimension(400, 250));
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        createSelectionList();
        createButton();

        this.pack();
        this.setVisible(true);

    }

    private void createSelectionList() {

        //TODO : Constants
        this.optionNames = new Vector<>();
        this.optionNames.add("Ours"); this.optionNames.add("Theirs"); this.optionNames.add("Ours Merge");
        this.optionNames.add("Theirs Merge"); this.optionNames.add("Fusion");

        if(flag.equals("NOFUSION")) {
            this.optionNames.remove(4);
        }

        JPanel diagramPanel = new JPanel();
        JList options = new JList(this.optionNames);

        options.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        options.setLayoutOrientation(JList.VERTICAL);
        options.setVisibleRowCount(-1);
        options.setSelectedIndex(0);

        JScrollPane listScroller = new JScrollPane(options);
        listScroller.setPreferredSize(new Dimension(250, 130));

        diagramPanel.add(listScroller);
        this.add(diagramPanel, BorderLayout.NORTH);
    }

    private void createButton() {

    }
}
