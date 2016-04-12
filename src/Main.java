import gui.projectManagement.views.ProjectManagementView;
import models.TikzCircle;
import models.TikzNode;
import models.TikzRectangle;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(ProjectManagementView::new);
    }
}
