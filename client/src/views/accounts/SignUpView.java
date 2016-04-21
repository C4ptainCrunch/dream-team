package views.accounts;

import controllers.accounts.SignUpController;

import javax.swing.*;
import java.awt.*;

/**
 * Created by mrmmtb on 21.04.16.
 */
public class SignUpView extends JFrame {

    SignUpController controller;
    LoginWindowView loginView;
    JTextField firstNameField;
    JTextField lastNameField;
    JTextField usernameField;
    JTextField emailField;
    JPasswordField passwordField;

    public SignUpView(LoginWindowView loginView) {
        this.controller = new SignUpController(this);
        this.loginView = loginView;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.render();
    }

    public final void render() {
        this.setTitle("TikzCreator : Login or Sign Up");
        this.setPreferredSize(new Dimension(500,500));
        initServiceCondition();
        this.pack();
        this.setVisible(true);
    }

    private void initServiceCondition() {

        JOptionPane optionPane = new JOptionPane("Do you accept the service conditions?",
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION);
        JDialog dialog = optionPane.createDialog(this,"Service conditions");
        dialog.pack();
        dialog.setVisible(true);
        if((Integer) optionPane.getValue() == JOptionPane.YES_OPTION) {
            controller.launchSignUpPanel();
        }else{
            JOptionPane.showMessageDialog(this,
                    "You can't create an account without accepting service conditions",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
            this.dispose();
        }
    }

    public void initSignUpPanel() {
        JPanel signUpPanel = new JPanel();

        signUpPanel.setLayout(new BoxLayout(signUpPanel, BoxLayout.Y_AXIS));
        initInformationsPanel(signUpPanel);
        this.add(signUpPanel);
    }

    private void initInformationsPanel(JPanel signUpPanel) {
        JPanel informationsPanel = new JPanel();
        informationsPanel.setLayout(new BoxLayout(informationsPanel, BoxLayout.Y_AXIS));

        initInfoPanelOptions(informationsPanel);
        signUpPanel.add(informationsPanel);
    }

    private void initInfoPanelOptions(JPanel informationsPanel) {

        JPanel firstNamePanel = new JPanel();
        firstNamePanel.setLayout(new BoxLayout(firstNamePanel, BoxLayout.X_AXIS));
        JLabel firstNameLabel = new JLabel("First Name: ");
        this.firstNameField = new JTextField();

        firstNamePanel.add(firstNameLabel);
        firstNamePanel.add(this.firstNameField);

        informationsPanel.add(firstNamePanel);


    }

    public void showLogginView(){
        this.loginView.setVisible(true);
    }

    public void hideLogginView(){
        this.loginView.setVisible(false);
    }
}
