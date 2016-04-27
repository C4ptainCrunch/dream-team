package controllers.accounts;

import constants.Errors;
import constants.GUI;
import constants.Network;
import constants.Warnings;
import models.NetworkRequest;
import org.apache.commons.validator.routines.EmailValidator;
import views.accounts.EditUserView;
import views.accounts.TokenActivationView;

import javax.swing.*;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by bambalaam on 27/04/16.
 */
public class EditUserController {

    private final EditUserView view;
    private final String BASE_PATH = "user/edit/";

    public EditUserController(EditUserView view) {
        this.view = view;
    }

    public void launchEditPanel() {
        this.view.hideLogginView();
        this.view.showEditPanel(getUserData());
    }

    public ArrayList<String> getUserData() {
        ArrayList<String> data = new ArrayList<>();
        return data;
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

    public void validateFields(ArrayList<JTextField> fields, JPasswordField passwordField, String originalUsername) {
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
            editProfile(fields, passwordField, originalUsername);
        }
    }

    public void cancelEdit() {
        this.view.dispose();
    }

    private void editProfile(ArrayList<JTextField> fields, JPasswordField passwordField, String originalUsername) {
        Form postForm = new Form();

        for(int i = 0 ; i<fields.size(); i++) {
            postForm.param(Network.Signup.FIELDS_NAMES.get(i), fields.get(i).getText());
        }
        postForm.param("password", new String(passwordField.getPassword()));
        postForm.param("originalUsername", originalUsername);



        NetworkRequest request = new NetworkRequest(Network.HOST.COMPLETE_HOSTNAME,
                                                    BASE_PATH+fields.get(2), MediaType.TEXT_PLAIN_TYPE);
        request.post(postForm);

        String response = request.getResponseAsString();
        if(response.equals(Network.Signup.SIGN_UP_OK)){
            this.view.dispose();
        }else {
            JOptionPane.showMessageDialog(this.view, Errors.SIGNUP_FAILED, Errors.ERROR, JOptionPane.ERROR_MESSAGE);
        }

    }


}
