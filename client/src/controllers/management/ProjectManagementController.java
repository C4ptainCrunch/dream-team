package controllers.management;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.swing.*;

import models.project.Diagram;
import models.project.RecentProjects;
import utils.Log;
import views.editor.EditorView;
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

    public void editProject(Diagram diagram) throws IOException {
        RecentProjects.addProject(diagram);

        java.awt.EventQueue.invokeLater(() -> new EditorView(diagram));
        view.dispose(); // Exit previous windows
    }

    /**
     * Updates the project description with the selected project
     * Should be called when the dropdown is updated.
     * @param comboBox
     */
    public void dropdownSelected(JComboBox comboBox) {
        Diagram selectedDiagram = (Diagram) comboBox.getSelectedItem();
        if(selectedDiagram == null){
            return;
        }
        logger.info(selectedDiagram.toString());

        try {
            String text = String.format(ProjectManagement.BLANK_INFO_PANEL, selectedDiagram.getName(), "Local",
                    selectedDiagram.getLastChange().toString());
            this.view.setInfoText(text);
        } catch (IOException | ClassNotFoundException e) {
            logger.severe("Error while reading the diff file: " + e.toString());
        }
    }

    /**
     * Creates a new project and opens it in the editor.
     */
    public void createProject() {
        try {
            editProject(new Diagram());
        } catch (IOException e) {}
    }


    public void openProject() {
        Diagram diagram = view.getSelectedProject();
        if (diagram != null) {
            try {
                editProject(diagram);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(view, Errors.OPEN_ERROR, Errors.ERROR, JOptionPane.ERROR_MESSAGE);
                logger.severe("Failed to open the diagram: " + e.getMessage());
            }
        }
    }

    public void renameProject() {
        Diagram diagram = view.getSelectedProject();
        if (diagram == null) {
            return;
        }

        FileChooseView choose = new FileChooseView("Rename diagram", JFileChooser.DIRECTORIES_ONLY);
        File path = choose.ask();
        if (path != null) {
            try {
                diagram.rename(path);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(view, Errors.RENAME_ERROR, Errors.ERROR, JOptionPane.ERROR_MESSAGE);
                logger.severe("Failed to rename the diagram: " + e.getMessage());
            }
        }
    }
}
