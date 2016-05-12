package views.editor;

import constants.SyncModelSelection;
import controllers.editor.SyncModeSelectionController;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * Window that allows the user to select one of several possible sync modes for his diagram
 */
public class SyncModeSelectionView extends JFrame {

    private SyncModeSelectionController controller;
    private String flag;
    private JList<String> optionsList;
    private Vector<String> optionNames;
    private JPanel syncPanel;
    private Object selection;

    /**
     * Constructs the SyncModeSelectionView
     * @param flag Flag to set if a fusion is possible or not
     */
    public SyncModeSelectionView(String flag) {
        this.controller = new SyncModeSelectionController(this);
        this.flag = flag;
        this.render();
    }

    private void render() {
        this.setTitle(SyncModelSelection.Title.TITLE);
        this.setPreferredSize(new Dimension(300, 160));
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        this.syncPanel = new JPanel();
        this.syncPanel.setLayout(new BoxLayout(this.syncPanel, BoxLayout.Y_AXIS));

        createSelectionList();
        createButton();

        this.add(this.syncPanel);
        this.pack();
        this.setVisible(true);
    }

    private void createSelectionList() {

        //TODO : Constants
        this.optionNames = new Vector<>();
        this.optionNames.add(SyncModelSelection.Option.OPTION_OURS); this.optionNames.add(SyncModelSelection.Option.OPTION_THEIRS); this.optionNames.add(SyncModelSelection.Option.OPTION_OURS_MERGE);
        this.optionNames.add(SyncModelSelection.Option.OPTION_THEIRS_MERGE); this.optionNames.add(SyncModelSelection.Option.OPTION_FUSION);

        if(flag.equals(SyncModelSelection.Option.OPTION_NO_FUSION)) {
            this.optionNames.remove(4);
        }

        JPanel diagramPanel = new JPanel();
        this.optionsList = new JList(this.optionNames);

        this.optionsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.optionsList.setLayoutOrientation(JList.VERTICAL);
        this.optionsList.setVisibleRowCount(-1);
        this.optionsList.setSelectedIndex(0);

        JScrollPane listScroller = new JScrollPane(this.optionsList);
        listScroller.setPreferredSize(new Dimension(250, 130));

        diagramPanel.add(listScroller);
        this.syncPanel.add(diagramPanel);
    }

    private void createButton() {
        JButton syncButton = new JButton(SyncModelSelection.Button.BUTTON_SYNC);
        syncButton.addActionListener(actionEvent -> {
            this.controller.activateSelection();
        });
        this.syncPanel.add(syncButton);
    }

    /**
     * Get the user's selection
     * @return The user's selection
     */
    public String getSelection() {
        return this.optionsList.getSelectedValue();
    }
}
