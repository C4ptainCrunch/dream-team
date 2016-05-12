package controllers.management;

import misc.logic.CloudLogic;
import models.project.Project;
import utils.RecentProjects;
import views.management.CloudManagementView;
import views.management.FileChooseView;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CloudManagementController {

    private final CloudManagementView view;

    public CloudManagementController(CloudManagementView cloudManagementView) {
        this.view = cloudManagementView;
    }

    public void dropdownSelected(models.databaseModels.Project selectedProject) {

    }

    public void openSharedProject() {

    }

    public void setPermissionsToSelectedProject() {

    }

    public void getLocalCopy() {
        models.databaseModels.Project project = view.getSelectedProject();
        if (project == null) {
            return;
        }
        try {
            Path localZip = CloudLogic.getLocalCopy(project);

            FileChooseView choose = new FileChooseView("Save local copy", JFileChooser.FILES_AND_DIRECTORIES);
            choose.setFileRestriction("CreaTikz files","crea");
            choose.setSelectedFile(new File(project.getName()));
            File destination = choose.ask();
            if(destination != null) {
                Files.move(localZip, destination.toPath());
                RecentProjects.addProject(new Project(destination.toPath()));
                this.view.getParentView().refresh();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
