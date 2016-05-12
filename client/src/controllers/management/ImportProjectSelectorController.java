package controllers.management;
import models.project.Project;
import views.management.ImportProjectSelectorView;

import java.io.IOException;
import java.util.Set;

public class ImportProjectSelectorController {

    private ImportProjectSelectorView view;

    public ImportProjectSelectorController(final ImportProjectSelectorView view) {
        this.view = view;
    }

    public void ok() {
        Set<String> selection = this.view.getSelection();
        Project currentProject = this.view.getProject();
        try {
            for(String diagram : currentProject.getDiagramNames()) {
                if( ! selection.contains(diagram)) {
                    currentProject.removeDiagram(diagram);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void all() {
        this.view.dispose();
    }
}
