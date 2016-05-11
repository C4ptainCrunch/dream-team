package controllers.management;


import models.project.Diagram;
import models.project.Project;
import utils.RecentProjects;
import views.editor.EditorView;
import views.management.DiagramManagementView;
import views.management.FileChooseView;
import views.management.ManagementView;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class ManagementController {
    private final ManagementView view;

    public ManagementController(ManagementView view) {
        this.view = view;
    }


    /**
     * Opens a FileChooser to select an existing project in the file system
     */
    public void openProject(){

        FileChooseView choose = new FileChooseView("Select project", JFileChooser.FILES_ONLY);
        choose.setFileRestriction("CreaTikz files","crea");
        File projectFile = choose.ask();
        if(projectFile != null) {
            try {
                Project currentProject = new Project(projectFile.toPath());
                new DiagramManagementView(currentProject);
                this.view.dispose();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Creates a new project and opens it in the editor.
     */
    public void createProject() {
        try {
            editDiagram(new Project().getDiagram("unsaved"));
        } catch (IOException e) {}
    }


    /**
     * Launches edition of a given diagram
     * @param diagram The diagram to be edited
     * @throws IOException
     */
    public void editDiagram(Diagram diagram) throws IOException {
        RecentProjects.addProject(diagram.getProject());

        java.awt.EventQueue.invokeLater(() -> new EditorView(diagram));
        this.view.dispose(); // Exit previous windows
    }
}
