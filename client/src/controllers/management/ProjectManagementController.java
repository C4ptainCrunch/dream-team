package controllers.management;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Logger;

import javax.swing.*;

import models.project.Diagram;
import models.project.Project;
import utils.Log;
import utils.RecentProjects;
import views.editor.EditorView;
import views.management.DiagramManagementView;
import views.management.FileChooseView;
import views.management.ProjectManagementView;
import constants.Errors;
import constants.GUI.ProjectManagement;

public class ProjectManagementController {
    private final static Logger logger = Log.getLogger(ProjectManagementController.class);
    private final ProjectManagementView view;

    public ProjectManagementController(ProjectManagementView view) {
        this.view = view;
    }

    public void editDiagram(Diagram diagram) throws IOException {
        RecentProjects.addProject(diagram.getProject());

        java.awt.EventQueue.invokeLater(() -> new EditorView(diagram));
        view.dispose(); // Exit previous windows
    }

    /**
     * Updates the project description with the selected project
     * Should be called when the dropdown is updated.
     * @param comboBox
     */
    public void dropdownSelected(JComboBox comboBox) {
        Project selectedProject = (Project) comboBox.getSelectedItem();
        if(selectedProject == null){
            return;
        }
        logger.info(selectedProject.toString());

        String text = String.format(ProjectManagement.BLANK_INFO_PANEL, selectedProject.getName(), "Local",
                    selectedProject.getLastChange().toString());
        this.view.setInfoText(text);

    }

    /**
     * Creates a new project and opens it in the editor.
     */
    public void createProject() {
        try {
            editDiagram(new Project().getDiagram("unsaved"));
        } catch (IOException e) {}
    }


    public void openRecentDiagram() {
        Diagram diagram = view.getSelectedProject();
        if (diagram != null) {
            try {
                editDiagram(diagram);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(view, Errors.OPEN_ERROR, Errors.ERROR, JOptionPane.ERROR_MESSAGE);
                logger.severe("Failed to open the diagram: " + e.getMessage());
            }
        }
    }

    public void openProjects(){

        FileChooseView choose = new FileChooseView("Select project", JFileChooser.FILES_ONLY);
        choose.setFileRestriction("Archive files","zip");
        File projectFile = choose.ask();
        try {
            Project currentProject = new Project(projectFile.toPath());
            Set<String> diagramNames = currentProject.getDiagramNames();
            new DiagramManagementView(currentProject, diagramNames);
            this.view.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void moveProject() {
        Diagram diagram = view.getSelectedProject();
        if (diagram == null) {
            return;
        }

        FileChooseView choose = new FileChooseView("Move project", JFileChooser.DIRECTORIES_ONLY);
        File path = choose.ask();
        if (path != null) {
            try {
                diagram.getProject().move(path);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(view, Errors.RENAME_ERROR, Errors.ERROR, JOptionPane.ERROR_MESSAGE);
                logger.severe("Failed to rename the diagram: " + e.getMessage());
            }
        }
    }
}
