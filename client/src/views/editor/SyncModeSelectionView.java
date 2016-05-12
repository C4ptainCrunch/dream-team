package views.editor;

import java.awt.*;
import java.util.Vector;

import javax.swing.*;

import models.project.Project;
import constants.SyncModeSelection;
import controllers.editor.SyncModeSelectionController;

/**
 * Window that allows the user to select one of several possible sync modes for
 * his diagram
 */
public class SyncModeSelectionView extends JDialog {

    private String selection = null;
    private SyncModeSelectionController controller;
    private Project project;
    private JList<String> optionsList;
    private Vector<String> optionNames;
    private JPanel syncPanel;

    public SyncModeSelectionView(Project project) {
        this.controller = new SyncModeSelectionController(this);
        this.project = project;
        this.setModal(true);
        this.render();
    }

    private void render() {
        this.setTitle(SyncModeSelection.Title.TITLE);
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
        this.optionNames = new Vector<>();
        this.optionNames.add(SyncModeSelection.Option.OPTION_OURS);
        this.optionNames.add(SyncModeSelection.Option.OPTION_THEIRS);

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
        JButton syncButton = new JButton(SyncModeSelection.Button.BUTTON_SYNC);
        syncButton.addActionListener(actionEvent -> {
            this.controller.choiceDone();
        });
        this.syncPanel.add(syncButton);
    }

    /**
     * Get the user's selection
     *
     * @return The user's selection
     */
    public String getSelection() {
        return this.optionsList.getSelectedValue();
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }

    public String getMode() {
        return this.selection;
    }

}
