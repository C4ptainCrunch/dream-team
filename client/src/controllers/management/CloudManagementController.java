package controllers.management;

import misc.logic.CloudLogic;
import models.project.Project;
import utils.Dirs;
import utils.Log;
import utils.RecentProjects;
import views.editor.SyncModeSelectionView;
import views.management.CloudManagementView;
import views.management.DiagramManagementView;
import views.management.FileChooseView;
import views.management.ImportProjectSelectorView;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class CloudManagementController {

    private final CloudManagementView view;
    private Logger logger = Log.getLogger(CloudManagementController.class);

    public CloudManagementController(CloudManagementView cloudManagementView) {
        this.view = cloudManagementView;
    }

    public void dropdownSelected(models.databaseModels.Project selectedProject) {
        String infoText = "INFORMATION ABOUT SELECTED PROJECT:\nCreator: " + selectedProject.getUsername() + "\nLast revision: "+
                          selectedProject.getLast_modification()+"\nWrite Permission: " + (selectedProject.isCurrentUserWritePerm() ? "Yes": "No");
        this.view.setInfoText(infoText);
    }

    public void openSharedProject() {
        try {
            models.databaseModels.Project dbProject = this.view.getSelectedProject();

            Path projectPath = CloudLogic.getLocalOrDistantCopy(dbProject);

            Path cloudDir = Dirs.getDataDir().resolve("cloud");
            Path path = cloudDir.resolve(dbProject.getUid() + ".crea");

            if (!projectPath.equals(path)) {
                Files.move(projectPath, path);
            }

            Project project = new Project(path);
            java.awt.EventQueue.invokeLater(() -> {
                try {
                    new DiagramManagementView(project);
                    this.view.dispose();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            logger.severe("Load failed");
            e.printStackTrace();
        }

    }

    public void setPermissionsToSelectedProject() {

    }

    public void getLocalCopy() {
        models.databaseModels.Project project = view.getSelectedProject();
        if (project == null) {
            return;
        }
        try {
            Path localZip = CloudLogic.getLocalOrDistantCopy(project);

            FileChooseView choose = new FileChooseView("Save local copy", JFileChooser.FILES_AND_DIRECTORIES);
            choose.setFileRestriction("CreaTikz files","crea");
            choose.setSelectedFile(new File(project.getName()));
            File destination = choose.ask();

            if(destination != null) {
                Files.move(localZip, destination.toPath());
                new ImportProjectSelectorView(destination.toPath());
                RecentProjects.addProject(new Project(destination.toPath()));
                this.view.getParentView().refresh();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
