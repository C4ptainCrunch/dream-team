package controllers.accounts;

import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import misc.utils.RequestBuilder;

import models.databaseModels.User;
import org.apache.commons.validator.routines.EmailValidator;

import utils.Log;
import views.accounts.EditUserView;
import views.accounts.TokenActivationView;
import views.management.ManagementView;
import constants.Errors;
import constants.GUI;
import constants.Warnings;

/**
 * The controller related to the EditUserView.
 */

public class EditUserController {

    private final EditUserView view;
    private final static Logger logger = Log.getLogger(EditUserController.class);

    public EditUserController(final EditUserView view) {
        this.view = view;
    }


    private boolean checkFields() {
        boolean valid = true;
        if(!Pattern.matches(GUI.SignUp.NAMES_REGEX, this.view.getFirstName())) {
            this.showWarning(Warnings.FIRSTNAME_WARNING);
            valid = false;
        }

        if(!Pattern.matches(GUI.SignUp.NAMES_REGEX, this.view.getLastName())) {
            this.showWarning(Warnings.LASTNAME_WARNING);
            valid = false;
        }

        if(!EmailValidator.getInstance().isValid(this.view.getEmail())) {
            this.showWarning(Warnings.EMAIL_WARNING);
            valid = false;
        }

        return valid;
    }

    public void showWarning(final String text) {
        JOptionPane.showMessageDialog(this.view, text, Errors.ERROR, JOptionPane.ERROR_MESSAGE);
    }

    public void submit() {
        if(this.checkFields()) {
            User u = this.view.getUser();
            u.setEmail(this.view.getEmail());
            u.setFirstName(this.view.getFirstName());
            u.setLastName(this.view.getLastName());

            Entity e = Entity.entity(u, MediaType.APPLICATION_XML);
            Response r = RequestBuilder.post("/user/edit", e).invoke();
            boolean emailChanged = !this.view.getOriginalEmail().equals(u.getEmail());
            if(emailChanged) {
                logger.info("Email changed");
            }
            if(emailChanged && r.getStatus() == 200) {
                java.awt.EventQueue.invokeLater(TokenActivationView::new);
            } else {
                java.awt.EventQueue.invokeLater(ManagementView::new);
            }
            this.view.dispose();
        }
    }


}
