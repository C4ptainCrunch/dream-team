package controllers.accounts;

import java.util.logging.Logger;

import javax.swing.*;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import misc.logic.CloudLogic;
import misc.utils.RequestBuilder;
import models.databaseModels.User;
import utils.Log;
import views.accounts.LoginWindowView;
import views.accounts.SignUpView;
import views.accounts.TokenActivationView;
import views.management.ManagementView;
import constants.Errors;

/**
 * The controller related to the LoginWindowView.
 */

public class LoginWindowController {

    private final static Logger logger = Log.getLogger(LoginWindowController.class);
    private final LoginWindowView view;
    private final String BASE_PATH = "user/login/";

    public LoginWindowController(final LoginWindowView view) {
        this.view = view;
    }

    public static boolean shouldSkipAuth() {
        Response r = RequestBuilder.get("/authentication/checktoken").invoke();
        Boolean shouldSkip;
        if (r.getStatus() == 200) {
            logger.info("Token was good, skipping login");
            storeUsername();
            shouldSkip = true;
        } else {
            logger.fine("Old token was invalid, " + Integer.toString(r.getStatus()));
            shouldSkip = false;
        }
        return shouldSkip;

    }

    private static void storeUsername() {
        Response r = RequestBuilder.get("/user/get").invoke();
        String username = r.readEntity(User.class).getUsername();
        CloudLogic.setUsername(username);
        logger.info("Our username is " + username);
    }

    private String getToken(final String username, final String password) {
        Form postForm = new Form();
        postForm.param("username", username);
        postForm.param("password", password);

        Response r = RequestBuilder.post("/authentication", postForm).invoke();
        return r.readEntity(String.class);
    }

    /**
     * Launch the login process.
     *
     * @param username
     *            The user's username
     * @param password
     *            The user's password
     */
    public void login(final String username, final String password) {

        String token = getToken(username, password);

        if (!token.trim().equals("")) {
            this.view.dispose();
            RequestBuilder.setToken(token);
            storeUsername();
            new ManagementView();
        } else {
            JOptionPane.showMessageDialog(this.view, Errors.LOGIN_FAILED, Errors.ERROR, JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Launch the sign up process.
     */
    public void signUp() {
        this.view.dispose();
        java.awt.EventQueue.invokeLater(() -> new SignUpView(this.view));
    }

    /**
     * Launch the token activation process.
     */
    public void tokenActivation() {
        this.view.dispose();
        java.awt.EventQueue.invokeLater(TokenActivationView::new);
    }

}
