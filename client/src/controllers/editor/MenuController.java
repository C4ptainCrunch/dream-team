package controllers.editor;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

import javax.swing.*;

import constants.ProjectConflicts;
import misc.logic.CloudLogic;
import models.project.Diagram;
import models.project.Project;
import utils.Log;
import utils.PdfCompilationError;
import utils.PdfRenderer;
import views.editor.EditorView;
import views.editor.MenuView;
import views.editor.SyncModeSelectionView;
import views.help.HelpView;
import views.management.*;
import constants.Errors;

/**
 * Implementation of the Controller (from the MVC architectural pattern) for the
 * Menu. The Menu is the menu bar of the GUI.
 */
public class MenuController implements Observer {
    private final static Logger logger = Log.getLogger(MenuController.class);
    private final MenuView view;
    private final Diagram diagram;

    /**
     * Constructs a new Controller for the Menu, with a given Diagram and
     * EditorView
     *
     * @param view
     *            The MenuView which is associated with this controller
     * @param diagram
     *            The Diagram
     */
    public MenuController(MenuView view, Diagram diagram) {
        this.view = view;

        this.diagram = diagram;
        this.diagram.getGraph().addObserver(this);
    }

    /**
     * Called when Observables linked to this Observer call notify(),
     *
     * @param o
     *            The Observable
     * @param arg
     *            The arguments given by the Observable
     */
    public void update(Observable o, Object arg) {
        // this was left intentionally blank
    }

    /**
     * Saves the Tikz text into a file
     */
    public void save() {
        try {
            if(this.diagram.isTemporary()){
                String diagramName = this.view.getDiagramName();
                this.diagram.rename(diagramName);
                this.diagram.save();

                FileChooseView choose = new FileChooseView("Save diagram", JFileChooser.FILES_AND_DIRECTORIES);
                choose.setFileRestriction("CreaTikz files","crea");
                choose.setSelectedFile(new File(diagramName + ".crea"));
                File newDir = choose.ask();
                if(newDir != null){
                    Project p = this.diagram.getProject();
                    p.move(newDir);
                    p.rename(newDir.toPath().getFileName().toString());
                    FileChooseView.setRecent(newDir);
                }
            }
            this.diagram.save();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(view, Errors.SAVE_ERROR, Errors.ERROR, JOptionPane.ERROR_MESSAGE);
            logger.severe("Diagram saved failed : " + e.toString());
            e.printStackTrace();
        }
    }

    /**
     * Compiles and renders the Tikz into a PDF file
     */
    public void compileAndOpen() {
        // TODO : should we move this to the model ?
        try {
            PdfRenderer.compileAndOpen(new File(this.diagram.getProject().getDirectory() + "/" + this.diagram.getName() + ".pdf"), this.diagram.getGraph());
        } catch (PdfCompilationError e) {
            showMessageDialog(null, "Error during compilation");
        }
    }

    /**
     * Opens the History window whichs shows the modifications done on the
     * working diagram
     */
    public void openHistory() {
        HistoryView histView = new HistoryView(this.diagram);
    }

    /**
     * Opens the Help window
     */
    public void showHelp() {
        java.awt.EventQueue.invokeLater(HelpView::new);
    }

    /**
     * Saves the diagram and closes the editor window.
     * If the diagram has no name/path, it asks the user if
     * he wants to save the file to the disk.
     * If the user cancels, no windows are closed and this
     * method does nothing.
     *
     * @param parentView
     *            The view in which the menu view associated with this
     *            controller is contained
     */
    public void saveAndQuit(EditorView parentView) {
        if(this.askToSave()){
            parentView.dispose();
        }
    }

    /**
     * Sets the Color blind mode on or off
     * @param stateChange State change
     */
    public void setColorBlindMode(int stateChange) {
        boolean set_mode = (stateChange == ItemEvent.SELECTED ? true : false);
        view.setBlindMode(set_mode);
    }

    /**
     * Return to the ManagementView to open a new project
     */
    public void openNew() {
        if(this.askToSave()){
            this.view.disposeParent();
            EventQueue.invokeLater(ManagementView::new);
        }
    }

    /**
     * Prompts the user to ask if he wants to save his diagram or not
     * @return The user's choice
     */
    private boolean askToSave() {
        boolean shouldQuit = true;
        if(diagram.isTemporary()){
            int r = JOptionPane.showConfirmDialog(this.view, "Would you like to save your diagram ?");
            if(r == JOptionPane.NO_OPTION){
            } else if (r == JOptionPane.CANCEL_OPTION){
                shouldQuit = false;
            } else if (r == JOptionPane.YES_OPTION) {
                save();
            }
        } else {
            save();
        }
        return shouldQuit;
    }

    /**
     * Open the DiagramManagementView to choose another diagram in the current project
     */
    public void openDiagram() {
        this.view.disposeParent();
        EventQueue.invokeLater(() -> {
            try {
                new DiagramManagementView(this.view.getDiagram().getProject());
            } catch (IOException e) {
                new ManagementView();
            }
        });
    }

    /**
     * Either notifies the user that the sync was successful, or launches a SyncModeSelectionView
     * to ask the user which sync mode he desires.
     */
    public void syncProject(){
        try {
            Project p = this.view.getDiagram().getProject();
            boolean hasConflicts = CloudLogic.AskForMerge(p);
            String policy = null;
            if (hasConflicts) {
                SyncModeSelectionView sv = new SyncModeSelectionView(p);
                policy = sv.getMode();
            } else {
                policy = ProjectConflicts.PUSH;
            }

            logger.info("Starting merge with policy: " + policy);

            if (CloudLogic.Merge(p, policy)) {
                try {
                    Path tmp = CloudLogic.getLocalCopy(p.getUid());
                    Files.move(tmp, p.getPath(), StandardCopyOption.REPLACE_EXISTING);

                    this.view.disposeParent();
                    this.view.syncOKPopup();
                    java.awt.EventQueue.invokeLater(() -> {
                        try {
                            String oldDiagramName = this.view.getDiagram().getName();
                            Project newProject = new Project(p.getPath());
                            new EditorView(newProject.getDiagram(oldDiagramName));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    return;
                } catch (IOException e) {
                    logger.warning("Merge with policy failed");
                    e.printStackTrace();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        logger.warning("Merge failed");

    }
}
