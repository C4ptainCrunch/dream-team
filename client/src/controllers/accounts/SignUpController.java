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
 * Created by mrmmtb on 21.04.16.
 */
public class SignUpController {

    private SignUpView view;

    public SignUpController(SignUpView v) {
        this.view = v;
    }

    public void launchSignUpPanel() {
        this.view.hideLogginView();
        this.view.initSignUpPanel();
    }

    public void validateFields(ArrayList<JTextField> fields, JPasswordField passwordField) {
        Boolean firstNameCheck = Pattern.matches(GUI.SignUp.NAMES_REGEX,fields.get(0).getText());
        if(!firstNameCheck) {
            this.view.initWarning(Warnings.FIRSTNAME_WARNING);
        }

        Boolean lastNameCheck = Pattern.matches(GUI.SignUp.NAMES_REGEX,fields.get(1).getText());
        if(!lastNameCheck) {
            this.view.initWarning(Warnings.LASTNAME_WARNING);
        }

        Boolean userNameCheck = Pattern.matches(GUI.SignUp.USERNAME_REGEX,fields.get(2).getText());
        if(!userNameCheck) {
            this.view.initWarning(Warnings.USERNAME_WARNING);
        }

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
