package controllers.management;

import java.util.logging.Logger;

import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import misc.utils.RequestBuilder;
import models.databaseModels.Permissions;
import models.databaseModels.Project;
import utils.Log;
import views.management.UserPermissionWindowView;

public class UserPermissionWindowController {

    private final UserPermissionWindowView view;
    private final Project currentProject;
    private final String user;
    private Logger logger = Log.getLogger(UserPermissionWindowView.class);

    public UserPermissionWindowController(UserPermissionWindowView view, Project currentProject, String user) {
        this.view = view;
        this.currentProject = currentProject;
        this.user = user;
    }

    public Permissions getUserPerms() {
        Form postForm = new Form();
        postForm.param("userName", this.user);
        Response r = RequestBuilder.post("/project/get_permission_for_user/" + this.currentProject.getUid(), postForm).invoke();
        if (r.getStatus() == 200) {
            logger.info("Permission get");
        } else {
            logger.severe("Permission get failed " + r.getStatus());
            logger.severe(r.readEntity(String.class));
        }
        Permissions perms = r.readEntity(Permissions.class);
        return perms;
    }

    public void setPermissions(boolean readSelected, boolean writeSelected) {
        Form postForm = new Form();
        postForm.param("readPerm", Boolean.toString(readSelected));
        postForm.param("writePerm", Boolean.toString(writeSelected));
        postForm.param("userName", this.user);
        Response r = RequestBuilder.post("/project/set_permission_for_user/" + this.currentProject.getUid(), postForm).invoke();
        this.view.dispose();
        this.setParentViewVisible();
    }

    private void setParentViewVisible() {
        this.view.setParentViewVisible();
    }
}
