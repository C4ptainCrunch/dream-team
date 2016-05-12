package controllers.accounts;

import constants.Errors;
import misc.utils.RequestBuilder;
import utils.Log;
import views.accounts.LoginWindowView;
import views.accounts.SignUpView;
import views.accounts.TokenActivationView;
import views.management.ManagementView;

import javax.swing.*;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

/**
 * The controller related to the LoginWindowView.
 */

public class LoginWindowController {

    private final LoginWindowView view;
    private final String BASE_PATH = "user/login/";
    private final static Logger logger = Log.getLogger(LoginWindowController.class);

    public LoginWindowController(final LoginWindowView view) {
        this.view = view;
    }

    public static boolean shouldSkipAuth() {
        Response r = RequestBuilder.get("/authentication/checktoken").invoke();
        Boolean shouldSkip;
        if (r.getStatus() == 200) {
            logger.info("Token was good, skipping login");
            shouldSkip = true;
        } else {
            logger.fine("Old token was invalid, " + Integer.toString(r.getStatus()));
            shouldSkip = false;
        }
        return shouldSkip;

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
     * @param username The user's username
     * @param password The user's password
     */
    public void login(final String username, final String password) {

        String token = getToken(username, password);

        if (!token.trim().equals("")) {
            this.view.dispose();
            RequestBuilder.setToken(token);
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
