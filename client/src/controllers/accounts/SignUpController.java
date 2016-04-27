package controllers.accounts;

import constants.GUI;
import constants.Warnings;
import views.accounts.SignUpView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.regex.Pattern;
import org.apache.commons.validator.routines.EmailValidator;
import views.accounts.TokenActivationView;

/**
 * The controller for the SignUpView.
 */
public class SignUpController {

    private SignUpView view;

    public SignUpController(SignUpView v) {
        this.view = v;
    }

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
            new TokenActivationView();
            this.view.dispose();
        }
    }

    public void cancelSignUp() {
        this.view.dispose();
    }

    private void accountCreation(ArrayList<JTextField> fields, JPasswordField passwordField) {
        // SERVER: CREATE ACCOUNT HERE
    }

}
