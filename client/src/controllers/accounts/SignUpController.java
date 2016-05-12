package controllers.accounts;

import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

import models.NetworkRequest;

import org.apache.commons.validator.routines.EmailValidator;

import views.accounts.LoginWindowView;
import views.accounts.SignUpView;
import views.accounts.TokenActivationView;
import constants.Errors;
import constants.GUI;
import constants.Network;
import constants.Warnings;

/**
 * The controller for the SignUpView.
 */
public class SignUpController {

    private SignUpView view;
    private final String BASE_PATH = "user/signup/";

    public SignUpController(SignUpView v) {
        this.view = v;
    }

    /**
     * Launches the creation of the sign up panel
     */
    public void launchSignUpPanel() {
        this.view.hideLogginView();
        this.view.showSignUpPanel();
    }

    private boolean checkField(JTextField field, String regex, String warning){
        boolean valid = Pattern.matches(regex, field.getText());
        if (!valid){
            this.view.initWarning(warning);
        }
        return valid;
    }

    /**
     * Check if the data entered by the user are correct (i.e. allow him to enter the program).
     * @param fields The text fields
     * @param passwordField The password field
     */
    public void validateFields(ArrayList<JTextField> fields, JPasswordField passwordField) {
        boolean firstNameCheck = checkField(fields.get(0), GUI.SignUp.NAMES_REGEX, Warnings.FIRSTNAME_WARNING);
        boolean lastNameCheck = checkField(fields.get(1), GUI.SignUp.NAMES_REGEX, Warnings.LASTNAME_WARNING);
        boolean userNameCheck = checkField(fields.get(2), GUI.SignUp.USERNAME_REGEX, Warnings.USERNAME_WARNING);

        EmailValidator emailValidator = EmailValidator.getInstance();
        Boolean emailCheck = emailValidator.isValid(fields.get(3).getText());
        if(!emailCheck) {
            this.view.initWarning(Warnings.EMAIL_WARNING);
        }

        // PASSWORD VALIDATION HERE: Need to discuss password rules.
        if(firstNameCheck && lastNameCheck && userNameCheck && emailCheck) {
            accountCreation(fields, passwordField);
        }
    }

    /**
     * Action launched when the Cancel button is pressed
     */
    public void cancelSignUp() {
        this.view.dispose();
        java.awt.EventQueue.invokeLater(LoginWindowView::new);
    }

    private void accountCreation(ArrayList<JTextField> fields, JPasswordField passwordField) {
        Form postForm = new Form();

        for(int i = 0 ; i<fields.size(); i++) {
            postForm.param(Network.Signup.FIELDS_NAMES.get(i), fields.get(i).getText());
        }
        postForm.param("password", new String(passwordField.getPassword()));


        NetworkRequest request = new NetworkRequest(Network.HOST.COMPLETE_HOSTNAME,BASE_PATH+fields.get(2), MediaType.TEXT_PLAIN_TYPE);
        request.post(postForm);


        if(request.getResponse().getStatus() == 200){
            new TokenActivationView();
            this.view.dispose();
        }else {
            JOptionPane.showMessageDialog(this.view, Errors.SIGNUP_FAILED, Errors.ERROR, JOptionPane.ERROR_MESSAGE);
        }

    }

}
