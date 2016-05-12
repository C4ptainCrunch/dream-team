package controllers.management;


import models.databaseModels.Project;
import views.management.PermissionWindowView;

import javax.swing.*;
import java.util.List;
import java.util.Vector;

public class PermissionWindowController {

    private final PermissionWindowView view;
    private boolean defaultReadPerm;
    private boolean defaultWritePerm;
    private Vector<String> serverUsers;

    public PermissionWindowController(PermissionWindowView view) {
        this.view = view;
    }

    public boolean getDefaultReadPerm() {
        return true;
    }

    public boolean getDefaultWritePerm() {
        return true;
    }

    public Vector getServerUsers() {
        this.serverUsers = new Vector<>();
        this.serverUsers.add("BOB");
        this.serverUsers.add("ALICE");
        return serverUsers;
    }

    public void setDefaultPermissions(Project currentProject, boolean defaultReadPerm, boolean defaultWritePerm) {

    }

    public void setUserPermissions(Project currentProject, String user) {

    }
}
