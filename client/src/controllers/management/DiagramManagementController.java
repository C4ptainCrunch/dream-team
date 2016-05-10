package controllers.management;


import models.project.Project;
import views.management.DiagramManagementView;

import java.util.Set;

public class DiagramManagementController {

    public DiagramManagementController(Project currentProject, Set<String> diagramNames) {
        java.awt.EventQueue.invokeLater(() -> new DiagramManagementView(diagramNames));

    }
}
