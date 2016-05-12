package controllers.management;


import misc.utils.RequestBuilder;
import models.databaseModels.User;
import models.project.Diagram;
import models.project.Project;
import utils.Log;
import utils.RecentProjects;
import views.accounts.EditUserView;
import views.accounts.LoginWindowView;
import views.editor.EditorView;
import views.management.DiagramManagementView;
import views.management.FileChooseView;
import views.management.ManagementView;

import javax.swing.*;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class ManagementController {
    private final ManagementView view;
    private static final Logger logger = Log.getLogger(ManagementController.class);

    public ManagementController(final ManagementView view) {
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
        } catch (IOException e) {
            logger.fine("Failed to create new unsaved diagram");
        }
    }


    /**
     * Launches edition of a given diagram
     * @param diagram The diagram to be edited
     * @throws IOException
     */
    public void editDiagram(final Diagram diagram) throws IOException {
        RecentProjects.addProject(diagram.getProject());
        java.awt.EventQueue.invokeLater(() -> new EditorView(diagram));
        this.view.dispose(); // Exit previous windows
    }

    public void logout() {
        RequestBuilder.setToken("");
        this.view.dispose();
        java.awt.EventQueue.invokeLater(LoginWindowView::new);
    }

    public void editProfile(){
        this.view.dispose();
        java.awt.EventQueue.invokeLater(() -> {
            Response r = RequestBuilder.get("/user/get").invoke();
            User user = r.readEntity(User.class);
            new EditUserView(user);
        });
    }
}
