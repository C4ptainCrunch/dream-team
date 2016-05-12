package controllers.management;
import models.project.Diagram;
import models.project.Project;
import views.editor.EditorView;
import views.management.DiagramManagementView;

/**
 * Controller for the DiagramManagementView
 */
public class DiagramManagementController {

    private DiagramManagementView view;
    private Project currentProject;

    /** Creates a controller for the DiagramManagementView
     * @param view The corresponding view, created elsewhere
     * @param currentProject The project in which the diagram is
     */
    public DiagramManagementController(final DiagramManagementView view, final Project currentProject) {
        this.view = view;
        this.currentProject = currentProject;
    }

    /** Opens a diagram in the editor, either an old one or a new one.
     * @param selectedValue Selected diagram on a list (either an old one or the option to create a new one)
     * @param newName Name given in a text box for a new diagram
     */
    public void openDiagram(final String selectedValue, final String newName) {
        if(selectedValue.equals("Create new diagram")) {
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
