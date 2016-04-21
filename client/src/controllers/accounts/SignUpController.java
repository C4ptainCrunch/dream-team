package controllers.accounts;

import views.accounts.SignUpView;

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

    public void validateFields() {
        System.out.println("VALIDATION NEEDED HERE!");
    }

    public void cancelSignUp() {

    }

}
