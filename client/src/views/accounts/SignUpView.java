package views.accounts;

import constants.GUI;
import controllers.accounts.SignUpController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import constants.GUI.SignUp;
import misc.utils.JTextFieldSizeLimiter;


/**
 * Created by mrmmtb on 21.04.16.
 */
public class SignUpView extends JFrame {

    SignUpController controller;
    LoginWindowView loginView;
    JTextField firstNameField = new JTextField();
    JTextField lastNameField = new JTextField();
    JTextField usernameField = new JTextField();
    JTextField emailField = new JTextField();
    JPasswordField passwordField;
    ArrayList<JTextField> fields;

    public SignUpView(LoginWindowView loginView) {
        this.controller = new SignUpController(this);
        this.loginView = loginView;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.render();
    }

    public final void render() {
        this.setTitle("TikzCreator : Login or Sign Up");
        this.setPreferredSize(new Dimension(300,130));
        initServiceCondition();
        this.setResizable(false);
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

    public void initWarning(String warningText) {
        JOptionPane.showMessageDialog(this, warningText, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    public void initSignUpPanel() {
        JPanel signUpPanel = new JPanel();
        signUpPanel.setLayout(new BoxLayout(signUpPanel, BoxLayout.Y_AXIS));
        initInformationPanel(signUpPanel);
        initButtonsPanel(signUpPanel);
        this.add(signUpPanel);
    }

    private void initInformationPanel(JPanel signUpPanel) {
        JPanel informationPanel = new JPanel();
        informationPanel.setLayout(new BoxLayout(informationPanel, BoxLayout.Y_AXIS));

        this.fields = new ArrayList<>();
        this.fields.add(this.firstNameField);
        this.fields.add(this.lastNameField);
        this.fields.add(this.usernameField);
        this.fields.add(this.emailField);

        initInfoPanelOptions(informationPanel);
        signUpPanel.add(informationPanel);
    }

    private void initInfoPanelOptions(JPanel informationPanel) {

        for(int i=0; i< this.fields.size(); i++){
            JPanel newPanel = new JPanel();
            newPanel.setLayout(new GridLayout(1,2));
            newPanel.setMaximumSize( new Dimension(500,100));
            newPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel thisLabel = new JLabel(SignUp.FIELD_LABELS.get(i));
            JTextField thisField = this.fields.get(i);
            thisField.setDocument(new JTextFieldSizeLimiter(SignUp.FIELD_SIZES.get(i)));

            newPanel.add(thisLabel);
            newPanel.add(thisField);
            informationPanel.add(newPanel);
        }

        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new GridLayout(1,2));
        passwordPanel.setMaximumSize( new Dimension(500,100));
        passwordPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel passwordLabel = new JLabel(SignUp.PASSWORD_LABEL);
        this.passwordField = new JPasswordField();

        passwordPanel.add(passwordLabel);
        passwordPanel.add(this.passwordField);
        informationPanel.add(passwordPanel);
    }

    private void initButtonsPanel(JPanel signupPanel) {
        JPanel buttons = new JPanel();
        buttons.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton OKButton = new JButton(SignUp.OK_BUTTON);
        OKButton.addActionListener(e -> controller.validateFields(this.fields, this.passwordField));

        JButton cancelButton = new JButton(SignUp.CANCEL_BUTTON);
        cancelButton.addActionListener(e -> controller.cancelSignUp());

        buttons.add(OKButton);
        buttons.add(cancelButton);

        signupPanel.add(buttons);
    }

    public void initTokenInputPane() {
        this.dispose();
        JOptionPane optionPane = new JOptionPane("VALIDATION DONE",
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION);
        JDialog dialog = optionPane.createDialog(this,"Service conditions");
        dialog.pack();
        dialog.setVisible(true);
    }

    public void showLogginView(){
        this.loginView.setVisible(true);
    }

    public void hideLogginView(){
        this.loginView.setVisible(false);
    }

}
