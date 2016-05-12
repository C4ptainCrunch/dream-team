package controllers.management;
import views.management.ImportProjectSelectorView;

public class ImportProjectSelectorController {

    private ImportProjectSelectorView view;

    public ImportProjectSelectorController(ImportProjectSelectorView view) {
        this.view = view;
    }

    public void ok() {
    }

    public void all() {
        this.view.dispose();
    }
}
