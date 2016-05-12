package controllers.management;

import misc.logic.CloudLogic;
import models.project.Project;
import utils.Dirs;
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

public class CloudManagementController {

    private final CloudManagementView view;

    public CloudManagementController(CloudManagementView cloudManagementView) {
        this.view = cloudManagementView;
    }

    public void dropdownSelected(models.databaseModels.Project selectedProject) {

    }

    public void openSharedProject() {
        models.databaseModels.Project dbProject = this.view.getSelectedProject();

        Project project = null;
        Path cloudDir = Dirs.getDataDir().resolve("cloud");
        Path path = cloudDir.resolve(dbProject.getUid() + ".crea");
        // TODO create dirs
        try {
            try {
                project = new Project(path);
                project.getName();
            } catch (Exception e) {
                Path localZip = CloudLogic.getLocalCopy(dbProject);
                System.out.println(localZip);
                System.out.println(path);
                Files.move(localZip, path);
                project = new Project(path);
            }
            Project finalProject = project;
            java.awt.EventQueue.invokeLater(() -> {
                try {
                    new DiagramManagementView(finalProject);
                    this.view.dispose();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
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
            Path localZip = CloudLogic.getLocalCopy(project);

            ImportProjectSelectorView view = new ImportProjectSelectorView(localZip);
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
