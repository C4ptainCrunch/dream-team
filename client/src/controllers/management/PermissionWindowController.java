package controllers.management;

import constants.SyncModeSelection;
import misc.utils.RequestBuilder;
import models.databaseModels.Project;
import views.management.PermissionWindowView;
import views.management.UserPermissionWindowView;

import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;
import java.util.Vector;

public class PermissionWindowController {

    private final PermissionWindowView view;
    private boolean defaultReadPerm;
    private boolean defaultWritePerm;
    private Vector<String> serverUsers;
    private Project currentProject;
    private RequestBuilder requestBuilder;

    public PermissionWindowController(PermissionWindowView view, Project project) {
        this.view = view;
        this.currentProject = project;
        this.requestBuilder = new RequestBuilder();
    }

    public boolean getDefaultReadPerm() {
        Response r = RequestBuilder.get("/project/info/"+this.currentProject.getUid()).invoke();
        Project proj = r.readEntity(Project.class);
        return proj.isRead_default();
    }

    public boolean getDefaultWritePerm() {
        Response r = RequestBuilder.get("/project/info/"+this.currentProject.getUid()).invoke();
        Project proj = r.readEntity(Project.class);
        return proj.isWrite_default();
    }

    public Vector getServerUsers() {
        // GET USER LIST FROM SERVER
        this.serverUsers = new Vector<>();
        this.serverUsers.add("BOB");
        this.serverUsers.add("ALICE");
        return serverUsers;
    }

    public void setDefaultPermissions(boolean defaultReadPerm, boolean defaultWritePerm) {
        Form postForm = new Form();
        postForm.param("readPerm", Boolean.toString(defaultReadPerm));
        postForm.param("writePerm", Boolean.toString(defaultWritePerm));
        Response r = RequestBuilder.post("/project/set_permissions/"+this.currentProject.getUid(), postForm).invoke();
    }


    public void setUserPermissions(Project currentProject, String user) {
        this.view.dispose();
        new UserPermissionWindowView(this.view, currentProject, user);
    }
}
