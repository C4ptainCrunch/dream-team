package controllers.accounts;

import constants.Network;
import models.NetworkRequest;
import views.accounts.LoginWindowView;
import views.accounts.SignUpView;
import views.accounts.TokenActivationView;
import views.management.ProjectManagementView;

import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * The controller related to the LoginWindowView.
 */

public class LoginWindowController {

    private final LoginWindowView view;

    public LoginWindowController(LoginWindowView view) {
        this.view = view;
    }

    /**
     * Launch the login process.
     * @param username The user's username
     * @param password The user's password
     */

    public void login(String username, String password) {
        Form postForm = new Form();
        postForm.param("username", username);
        postForm.param("password", password);

        NetworkRequest request = new NetworkRequest(Network.HOST.COMPLETE_HOSTNAME,"user/login/"+username, MediaType.TEXT_PLAIN_TYPE);
        request.post(postForm);

        Response response = request.getResponse();

        if(response.readEntity(String.class).equals("OK")){
            this.view.dispose();
            new ProjectManagementView();
        } else {
            System.out.println(response.readEntity(String.class));
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
