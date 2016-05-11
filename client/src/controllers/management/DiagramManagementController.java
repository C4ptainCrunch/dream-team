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
        if (selectedValue == null && newName.equals("") ) {
            this.view.showAlert("Please either choose an existing project" +
                                " or insert a name in the text dialog to create" +
                                " a new diagram."); // TO DO: move to constants when Jerome's branch works.
        }
        else{
            if(selectedValue == "Create new diagram") {
                // Create new diagram an open editor
                Diagram newDiagram = new Diagram(newName, this.currentProject);
                new EditorView(newDiagram);
            }
            else {
                // Open editor with selected diagram
                Diagram selectedDiagram = this.currentProject.getDiagram(selectedValue);
                new EditorView(selectedDiagram);
            }
            this.view.dispose();
        }

    }
}
