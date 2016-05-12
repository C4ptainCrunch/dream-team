package views.management;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.*;

import models.project.Project;
import controllers.management.ImportProjectSelectorController;

public class ImportProjectSelectorView extends JDialog {

    private ImportProjectSelectorController controller;
    private Vector diagramNames;
    private Project currentProject;
    private JList diagramList;
    private JButton okButton;

    public ImportProjectSelectorView(Path pathToProject) throws IOException {
        this.controller = new ImportProjectSelectorController(this);
        this.currentProject = new Project(pathToProject);
        this.diagramNames = new Vector<>(currentProject.getDiagramNames());
        this.setSize(new Dimension(400, 300));
        this.render();
    }

    private void render() {
        this.setTitle("TikzCreator : choose which diagrams to import");
        this.setPreferredSize(new Dimension(400, 250));
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.setModal(true);

        createDiagramsList();
        createNewDiagramDialog();

        this.pack();
        this.setVisible(true);
    }

    private void createDiagramsList() {
        JPanel diagramPanel = new JPanel();

        this.diagramList = new JList(this.diagramNames);
        this.diagramList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        this.diagramList.setLayoutOrientation(JList.VERTICAL);
        this.diagramList.setVisibleRowCount(-1);
        this.diagramList.setSelectedIndex(0);

        JScrollPane listScroller = new JScrollPane(diagramList);
        listScroller.setPreferredSize(new Dimension(250, 130));

        diagramPanel.add(listScroller);
        this.add(diagramPanel, BorderLayout.NORTH);
    }

    private void createNewDiagramDialog() {
        JPanel buttonPanel = new JPanel();

        this.okButton = new JButton("Selection");
        this.getRootPane().setDefaultButton(this.okButton);
        okButton.addActionListener(e -> controller.ok());

        JButton all = new JButton("All");
        okButton.addActionListener(e -> controller.all());

        buttonPanel.add(this.okButton);
        buttonPanel.add(all);
        this.add(buttonPanel, BorderLayout.CENTER);
    }

    public Set<String> getSelection() {
        List<String> selection = this.diagramList.getSelectedValuesList();
        return new HashSet<>(selection);
    }

    public Project getProject() {
        return this.currentProject;
    }
}
