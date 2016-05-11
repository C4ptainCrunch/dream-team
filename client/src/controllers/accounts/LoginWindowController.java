package controllers.accounts;

import javax.swing.*;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import misc.utils.RequestBuilder;
import utils.Log;
import views.accounts.EditUserView;
import views.accounts.LoginWindowView;
import views.accounts.SignUpView;
import views.accounts.TokenActivationView;
import views.management.ProjectManagementView;
import constants.Errors;
import constants.Network;

import java.util.logging.Logger;

/**
 * The controller related to the LoginWindowView.
 */

public class LoginWindowController {

    private final LoginWindowView view;
    private final String BASE_PATH = "user/login/";
    private final static Logger logger = Log.getLogger(LoginWindowController.class);

    public LoginWindowController(LoginWindowView view) {
        this.view = view;
    }

    public static boolean shouldSkipAuth() {
        Response r = RequestBuilder.get("/authentication/checktoken").invoke();

        if(r.getStatus() == 200) {
            logger.info("Token was good, skipping login");
            return true;
        } else {
            logger.fine("Old token was invalid, " + Integer.toString(r.getStatus()));
            return false;
        }

    }

    private String getToken(String username, String password) {
        Form postForm = new Form();
        postForm.param("username", username);
        postForm.param("password", password);

        Response r = RequestBuilder.post("/authentication", postForm).invoke();
        return r.readEntity(String.class);
    }

    /**
     * Launch the login process.
     * @param username The user's username
     * @param password The user's password
     */
    public void login(String username, String password) {

        String token = getToken(username,password);

        if(!token.trim().equals("")){
            this.view.dispose();
            RequestBuilder.setToken(token);
            new ProjectManagementView();
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

        String response = getToken(username,password);

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
