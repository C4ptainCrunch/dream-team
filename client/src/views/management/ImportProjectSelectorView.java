package views.management;
import controllers.management.ImportProjectSelectorController;
import models.project.Project;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Vector;

public class ImportProjectSelectorView extends JDialog {

    private ImportProjectSelectorController contoller;
    private Vector diagramNames;
    private Project currentProject;
    private JList diagramList;


    public ImportProjectSelectorView(Path pathToProject) throws IOException {
        this.contoller = new ImportProjectSelectorController(this);
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

        createDiagramsList();

        this.pack();
        this.setVisible(true);
    }

    private void createDiagramsList() {
        JPanel diagramPanel = new JPanel();

        this.diagramList = new JList(this.diagramNames);
        this.diagramList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.diagramList.setLayoutOrientation(JList.VERTICAL);
        this.diagramList.setVisibleRowCount(-1);
        this.diagramList.setSelectedIndex(0);

        JScrollPane listScroller = new JScrollPane(diagramList);
        listScroller.setPreferredSize(new Dimension(250, 130));

        diagramPanel.add(listScroller);
        this.add(diagramPanel, BorderLayout.NORTH);
    }
}