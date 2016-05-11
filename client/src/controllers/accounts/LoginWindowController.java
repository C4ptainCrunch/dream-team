package controllers.accounts;

import javax.swing.*;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

import models.NetworkRequest;
import views.accounts.EditUserView;
import views.accounts.LoginWindowView;
import views.accounts.SignUpView;
import views.accounts.TokenActivationView;
import views.management.ProjectManagementView;
import constants.Errors;
import constants.Network;

/**
 * The controller related to the LoginWindowView.
 */

public class LoginWindowController {

    private final LoginWindowView view;
    private final String BASE_PATH = "user/login/";

    public LoginWindowController(LoginWindowView view) {
        this.view = view;
    }

    /**
     * Checks the validity of the Username/Password pair
     * @param username The user's username
     * @param password The user's password
     * @return The server's response
     */
    private String checkUsernamePasswordPair(String username, String password) {
        Form postForm = new Form();
        postForm.param("username", username);
        postForm.param("password", password);

        NetworkRequest request = new NetworkRequest(Network.HOST.COMPLETE_HOSTNAME,BASE_PATH+username, MediaType.TEXT_PLAIN_TYPE);
        request.post(postForm);

        String response = request.getResponseAsString();
        return response;
    }

    /**
     * Launch the login process.
     * @param username The user's username
     * @param password The user's password
     */
    public void login(String username, String password) {

        String response = checkUsernamePasswordPair(username,password);

        if(response.equals(Network.Login.LOGIN_OK)){
            this.view.dispose();
            new ProjectManagementView();
        }else if(response.equals(Network.Login.ACCOUNT_NOT_ACTIVATED)){
            JOptionPane.showMessageDialog(this.view, Errors.ACTIVE_ACCOUNT_FIRST, Errors.ERROR, JOptionPane.ERROR_MESSAGE);
        }else {
            JOptionPane.showMessageDialog(this.view, Errors.LOGIN_FAILED, Errors.ERROR, JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Launch the edit profile process.
     * @param username The user's username
     * @param password The user's password
     */
    public void editProfile(String username, String password){

        String response = checkUsernamePasswordPair(username,password);

        if(response.equals(Network.Login.LOGIN_OK)){
            this.view.dispose();
            new EditUserView(this.view, username);
        }else if(response.equals(Network.Login.ACCOUNT_NOT_ACTIVATED)){
            JOptionPane.showMessageDialog(this.view, Errors.ACTIVE_ACCOUNT_FIRST, Errors.ERROR, JOptionPane.ERROR_MESSAGE);
        }else {
            JOptionPane.showMessageDialog(this.view, Errors.LOGIN_FAILED, Errors.ERROR, JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Launch the sign up process.
     */

    public void signUp() {
        new SignUpView(this.view);
    }

    /**
     * Launch the token activation process.
     */

    public void tokenActivation() {
        new TokenActivationView();
    }

}
