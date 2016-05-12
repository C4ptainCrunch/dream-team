package views.accounts;

import java.awt.*;

import javax.swing.*;

import constants.GUI.LoginWindow;
import controllers.accounts.LoginWindowController;

/**
 * LoginWindowView is the main window for the login process.
 */

public class LoginWindowView extends JFrame {

    private LoginWindowController controller = new LoginWindowController(this);
    private JPanel loginWindowPanel;
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
        this.loginWindowPanel.setLayout(new BoxLayout(this.loginWindowPanel, BoxLayout.Y_AXIS));

        createFirstButtonsPanel();
        createLoginInfoPanel();
        createLoginButtonPanel();

        this.add(this.loginWindowPanel);

        this.setResizable(false);
        this.pack();
        this.setVisible(true);
    }

    private void createLoginInfoPanel() {

        createUsernamePanel();
        createPasswordPanel();
        this.setFocusTraversalPolicy(new FocusTraversalPolicy() {
            @Override
            public Component getComponentAfter(final Container container, final Component component) {
                return passwordText;
            }

            @Override
            public Component getComponentBefore(final Container container, final Component component) {
                return usernameText;
            }

            @Override
            public Component getFirstComponent(final Container container) {
                return usernameText;
            }

            @Override
            public Component getLastComponent(final Container container) {
                return passwordText;
            }

            @Override
            public Component getDefaultComponent(final Container container) {
                return usernameText;
            }
        });
    }

    private void createUsernamePanel() {
        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.X_AXIS));

        JLabel usernameLabel = new JLabel("Username: ");
        this.usernameText = new JTextField(20);
        usernamePanel.add(usernameLabel);
        usernamePanel.add(this.usernameText);

        this.loginWindowPanel.add(usernamePanel);
    }

    private void createPasswordPanel() {
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));

        JLabel passwordLabel = new JLabel("Password: ");
        this.passwordText = new JPasswordField(20);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(this.passwordText);

        this.loginWindowPanel.add(passwordPanel);
    }

    private void createFirstButtonsPanel() {
        JPanel buttons = new JPanel();

        JButton signUp = new JButton(LoginWindow.SIGNUP_BUTTON);
        signUp.addActionListener(e -> controller.signUp());
        buttons.add(signUp);

        JButton tokenActivation = new JButton(LoginWindow.TOKEN_BUTTON);
        tokenActivation.addActionListener(e -> controller.tokenActivation());
        buttons.add(tokenActivation);

        this.loginWindowPanel.add(buttons);
    }

    private void createLoginButtonPanel() {

        JPanel buttons = new JPanel();

        JButton login = new JButton(LoginWindow.LOGIN_BUTTON);
        login.addActionListener(e -> controller.login(this.usernameText.getText(), this.passwordText.getText()));
        this.getRootPane().setDefaultButton(login);
        buttons.add(login);
        this.loginWindowPanel.add(buttons);

    }
}
