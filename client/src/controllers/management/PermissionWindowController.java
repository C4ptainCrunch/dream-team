package controllers.management;

import constants.SyncModeSelection;
import misc.utils.RequestBuilder;
import models.databaseModels.Project;
import models.databaseModels.User;
import views.management.PermissionWindowView;
import views.management.UserPermissionWindowView;

import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Vector;

public class PermissionWindowController {

    private final PermissionWindowView view;
    private Vector<String> serverUsers;
    private Project currentProject;

    public PermissionWindowController(PermissionWindowView view, Project project) {
        this.view = view;
        this.currentProject = project;
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
        Response r = RequestBuilder.get("/user/list/").invoke();
        List<User> userList = r.readEntity(new GenericType<List<User>>(){});
        this.serverUsers = new Vector<>();
        userList.forEach(u ->this.serverUsers.add(u.getUsername()));
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
