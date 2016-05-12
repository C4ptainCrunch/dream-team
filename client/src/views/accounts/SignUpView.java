package views.accounts;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import misc.utils.JTextFieldSizeLimiter;
import constants.GUI.SignUp;
import controllers.accounts.SignUpController;


/**
 * View designed for sign up process.
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

    /**
     * Default constructor.
     * @param loginView The parent view
     */

    public SignUpView(final LoginWindowView loginView) {
        this.controller = new SignUpController(this);
        this.loginView = loginView;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.render();
    }

    /**
     * Render the view.
     */

    public final void render() {
        this.setTitle("TikzCreator : Login or Sign Up");
        this.setPreferredSize(new Dimension(300,200));
        initServiceCondition();
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
    }

    private void initServiceCondition() {
        JOptionPane optionPane = new JOptionPane(
                "<html>Do you accept the service conditions?<br>" +
                        "The conditions are : <br>" +
                        "As long as you retain this notice you can do whatever you want with this stuff. <br>" +
                        "If we meet some day, and you think this stuff is worth it, you can buy us a beer in return." +
                        "</html>",
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
            System.exit(0);
        }
    }

    /**
     * Show a warning dialog
     * @param warningText The warning text displayed in the dialog
     */

    public void initWarning(final String warningText) {
        JOptionPane.showMessageDialog(this, warningText, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Display the sign up panel.
     */

    public void showSignUpPanel() {
        JPanel signUpPanel = new JPanel();
        signUpPanel.setLayout(new BoxLayout(signUpPanel, BoxLayout.Y_AXIS));
        initInformationPanel(signUpPanel);
        initButtonsPanel(signUpPanel);
        this.add(signUpPanel);
    }

    private void initInformationPanel(final JPanel signUpPanel) {
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

    private void initInfoPanelOptions(final JPanel informationPanel) {

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

    private void initButtonsPanel(final JPanel signupPanel) {
        JPanel buttons = new JPanel();
        buttons.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton OKButton = new JButton(SignUp.OK_BUTTON);
        OKButton.addActionListener(e -> controller.validateFields(this.fields, this.passwordField));
        this.getRootPane().setDefaultButton(OKButton);

        JButton cancelButton = new JButton(SignUp.CANCEL_BUTTON);
        cancelButton.addActionListener(e -> controller.cancelSignUp());

        buttons.add(OKButton);
        buttons.add(cancelButton);

        signupPanel.add(buttons);
    }

    public void showLogginView(){
        this.loginView.setVisible(true);
    }

    public void hideLogginView(){
        this.loginView.setVisible(false);
    }

}
