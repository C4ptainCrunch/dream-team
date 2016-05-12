package controllers.editor;

import views.editor.SyncModeSelectionView;


public class SyncModeSelectionController {

    private SyncModeSelectionView view;

    public SyncModeSelectionController(SyncModeSelectionView view) {
        this.view = view;
    }

    public void activateSelection() {
        String selection = this.view.getSelection();
        // TODO: Do something with this selection
    }
}
