package controllers.accounts;

import java.util.regex.Pattern;

import javax.swing.*;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import misc.utils.RequestBuilder;
import models.users.User;

import org.apache.commons.validator.routines.EmailValidator;

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

    public EditUserController(EditUserView view) {
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

    public void showWarning(String text) {
        JOptionPane.showMessageDialog(this.view, text, Errors.ERROR, JOptionPane.ERROR_MESSAGE);
    }

    public void submit() {
        if(this.checkFields()) {
            User u = this.view.getUser();
            Entity e = Entity.entity(u, MediaType.APPLICATION_XML);
            Response r = RequestBuilder.post("/user/edit", e).invoke();
            if(r.readEntity(String.class).equals("sdfsdfs")) {
                java.awt.EventQueue.invokeLater(TokenActivationView::new);
            } else {
                java.awt.EventQueue.invokeLater(ManagementView::new);
            }
        }
    }


}
