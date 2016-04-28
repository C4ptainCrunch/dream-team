package controllers.accounts;

import constants.Errors;
import constants.GUI;
import constants.Network;
import constants.Warnings;
import models.NetworkRequest;
import org.apache.commons.validator.routines.EmailValidator;
import views.accounts.EditUserView;
import views.accounts.LoginWindowView;
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
    private final String GET_PATH = "user/get/";

    public EditUserController(EditUserView view) {
        this.view = view;
    }

    public void launchEditPanel(String originalUsername) {
        this.view.hideLogginView();
        this.view.showEditPanel(getUserData(originalUsername));
    }

    public ArrayList<String> getUserData(String originalUsername) {
        Form postForm = new Form("username",originalUsername);

        NetworkRequest request = new NetworkRequest(Network.HOST.COMPLETE_HOSTNAME,
                GET_PATH+originalUsername, MediaType.TEXT_PLAIN_TYPE);

        request.post(postForm);
        String response = request.getResponseAsString();
        String[] split = response.split("/");
        ArrayList<String> data = new ArrayList<>();
        data.add(split[0]);
        data.add(split[1]);
        data.add(split[2]);
        data.add(split[3]);
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
     * @param originalUsername The original username from the user
     */

    public void validateFields(ArrayList<JTextField> fields, String originalUsername, String originalEmail) {
        boolean firstNameCheck = checkField(fields.get(0), GUI.SignUp.NAMES_REGEX, Warnings.FIRSTNAME_WARNING);
        boolean lastNameCheck = checkField(fields.get(1), GUI.SignUp.NAMES_REGEX, Warnings.LASTNAME_WARNING);
        boolean userNameCheck = checkField(fields.get(2), GUI.SignUp.USERNAME_REGEX, Warnings.USERNAME_WARNING);

        EmailValidator emailValidator = EmailValidator.getInstance();
        Boolean emailCheck = emailValidator.isValid(fields.get(3).getText());
        if(!emailCheck) {
            this.view.initWarning(Warnings.EMAIL_WARNING);
        }

        if(firstNameCheck && lastNameCheck && userNameCheck && emailCheck) {
            editProfile(fields, originalUsername, originalEmail);
        }
    }

    public void cancelEdit() {
        this.view.dispose();
        java.awt.EventQueue.invokeLater(LoginWindowView::new);
    }

    private void editProfile(ArrayList<JTextField> fields, String originalUsername, String originalEmail) {
        Form postForm = new Form();

        for(int i = 0 ; i<fields.size(); i++) {
            postForm.param(Network.Signup.FIELDS_NAMES.get(i), fields.get(i).getText());
        }
        postForm.param("originalUsername", originalUsername);
        postForm.param("originalEmail", originalEmail);

        NetworkRequest request = new NetworkRequest(Network.HOST.COMPLETE_HOSTNAME,
                                                    BASE_PATH+fields.get(2).getText(), MediaType.TEXT_PLAIN_TYPE);
        request.post(postForm);

        String response = request.getResponseAsString();
        if(response.equals(Network.Signup.SIGN_UP_OK)){
            this.view.dispose();
            if(originalEmail.equals(fields.get(3).getText())) {
                this.view.showLogginView();
            } else {
                new TokenActivationView();
            }
        }else {
            JOptionPane.showMessageDialog(this.view, Errors.SIGNUP_FAILED, Errors.ERROR, JOptionPane.ERROR_MESSAGE);
        }

    }


}
