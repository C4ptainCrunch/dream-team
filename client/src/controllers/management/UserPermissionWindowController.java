package controllers.management;


import models.databaseModels.Project;
import views.management.UserPermissionWindowView;

public class UserPermissionWindowController {

    private final UserPermissionWindowView view;

    public UserPermissionWindowController(UserPermissionWindowView view) {
        this.view = view;
    }

    public boolean getUserReadPerm() {
        return true;
    }

    public boolean getUserWritePerm() {
        return true;
    }

    public void setPermissions(Project currentProject, String user,
                               boolean readSelected, boolean writeSelected) {
        this.view.dispose();
        this.setParentViewVisible();
    }

    private void setParentViewVisible() {
        this.view.setParentViewVisible();
    }
}
