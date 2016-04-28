package views.accounts;

import constants.GUI;
import controllers.accounts.LoginWindowController;
import constants.GUI.LoginWindow;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * LoginWindowView is the main window for the login process.
 */

public class LoginWindowView extends JFrame {

    private LoginWindowController controller = new LoginWindowController(this);
    private JPanel loginWindowPanel;
    private GridBagConstraints constraints;
    private JTextField usernameText;
    private JTextField passwordText;

    public LoginWindowView() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.render();
    }

    /**
     * Render the view.
     */

    public final void render() {
        this.setTitle("TikzCreator : Login or Sign Up");

        this.loginWindowPanel = new JPanel();
        this.loginWindowPanel.setLayout(new GridBagLayout());
        this.constraints = new GridBagConstraints();
        this.constraints.fill = GridBagConstraints.HORIZONTAL;

        createLoginInfoPanel();
        this.loginWindowPanel.setBorder(new LineBorder(Color.GRAY));
        createButtonsPanel();

        this.add(this.loginWindowPanel);

        this.setResizable(false);
        this.pack();
        this.setVisible(true);
    }

    private void createLoginInfoPanel() {

        createUsernamePanel();
        createPasswordPanel();

    }

    private void createUsernamePanel() {
        JLabel usernameLabel = new JLabel("Username: ");
        this.constraints.gridx = 0;
        this.constraints.gridy = 0;
        this.constraints.gridwidth = 1;
        this.loginWindowPanel.add(usernameLabel, this.constraints);

        this.usernameText = new JTextField(20);
        this.constraints.gridx = 1;
        this.constraints.gridy = 0;
        this.constraints.gridwidth = 2;
        this.loginWindowPanel.add(this.usernameText, this.constraints);
    }

    private void createPasswordPanel() {
        JLabel passwordLabel = new JLabel("Password: ");
        this.constraints.gridx = 0;
        this.constraints.gridy = 1;
        this.constraints.gridwidth = 1;
        this.loginWindowPanel.add(passwordLabel, this.constraints);

        this.passwordText = new JPasswordField(20);
        this.constraints.gridx = 1;
        this.constraints.gridy = 1;
        this.constraints.gridwidth = 2;
        this.loginWindowPanel.add(this.passwordText, this.constraints);
    }

    private void createButtonsPanel() {
        JPanel buttons = new JPanel();

        JButton login = new JButton(LoginWindow.LOGIN_BUTTON);
        login.addActionListener(e -> controller.login(this.usernameText.getText(),
                                                      this.passwordText.getText()));

        JButton signUp = new JButton(LoginWindow.SIGNUP_BUTTON);
        signUp.addActionListener(e -> controller.signUp());

        JButton tokenActivation = new JButton(LoginWindow.TOKEN_BUTTON);
        tokenActivation.addActionListener(e -> controller.tokenActivation());

        JButton edit = new JButton(LoginWindow.EDIT_BUTTON);
        edit.addActionListener(e -> controller.editProfile(this.usernameText.getText(),
                                                            this.passwordText.getText()));

        buttons.add(login);
        buttons.add(signUp);
        buttons.add(tokenActivation);
        buttons.add(edit);

        this.loginWindowPanel.add(buttons);
    }
}
