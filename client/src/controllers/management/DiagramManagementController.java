package controllers.management;
import models.project.Diagram;
import models.project.Project;
import views.editor.EditorView;
import views.management.DiagramManagementView;

public class DiagramManagementController {

    private DiagramManagementView view;
    private Project currentProject;

    public DiagramManagementController(DiagramManagementView view, Project currentProject) {
        this.view = view;
        this.currentProject = currentProject;
    }

    public void openDiagram(String selectedValue, String newName) {
        if(selectedValue == "Create new diagram") {
            if (newName.equals("") ){
                this.view.showAlert("Please set a name for your diagram");
                        // TO DO: move to constants when Jerome's branch works.
            } else {
                // Create new diagram an open editor
                Diagram newDiagram = new Diagram(newName, this.currentProject);
                new EditorView(newDiagram);
                this.view.dispose();
            }
        }
        else {
            // Open editor with selected diagram
            Diagram selectedDiagram = this.currentProject.getDiagram(selectedValue);
            new EditorView(selectedDiagram);
            this.view.dispose();
        }

    }
}
