package controllers.accounts;

import views.accounts.LoginWindowView;
import views.accounts.SignUpView;
import views.accounts.TokenActivationView;
import views.management.ProjectManagementView;

/**
 * Created by bambalaam on 21/04/16.
 */
public class LoginWindowController {

    private final LoginWindowView view;

    public LoginWindowController(LoginWindowView view) {
        this.view = view;
    }

    public void login() {
        view.dispose();
        new ProjectManagementView();
        // SERVER: FIND BY USER AND PASSWORD
        // IF USER NOT NULL -> IS ACTIVATED?  ELSE ERROR
        // ELSE ERROR
    }

    public void signUp() {
        new SignUpView(this.view);
    }

    public void tokenActivation() {
        new TokenActivationView();
    }
}
