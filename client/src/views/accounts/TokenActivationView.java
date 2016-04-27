package views.accounts;

import constants.GUI;
import controllers.accounts.TokenActivationController;

import javax.swing.*;
import java.awt.*;

/**
 * Created by bambalaam on 26/04/16.
 */
public class TokenActivationView extends JFrame {

    private TokenActivationController controller;

    public TokenActivationView() {
        this.controller = new TokenActivationController(this);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setMaximumSize( new Dimension(500,100));
        this.render();
    }

    private void render() {
        this.setTitle("TikzCreator : Token Activation");
        initConfirmationPanel();
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
    }

    private void initConfirmationPanel(){
        JPanel tokenConfirmationPanel = new JPanel();
        tokenConfirmationPanel.setLayout(new BoxLayout(tokenConfirmationPanel, BoxLayout.Y_AXIS));

        JLabel presentationText = new JLabel(GUI.TokenWindow.WIN_LABEL);

        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new GridLayout(1,2));
        JLabel usernameLabel = new JLabel(GUI.TokenWindow.USER_LABEL);
        JTextField usernameInput = new JTextField();
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameInput);

        JPanel tokenPanel = new JPanel();
        tokenPanel.setLayout(new GridLayout(1,2));
        JLabel tokenLabel = new JLabel(GUI.TokenWindow.TOKEN_LABEL);
        JTextField tokenInput = new JTextField();
        tokenPanel.add(tokenLabel);
        tokenPanel.add(tokenInput);

        JButton okButton = new JButton(GUI.TokenWindow.OK_BUTTON);
        okButton.addActionListener(e -> controller.validateToken(tokenInput.getText(),
                                                                 usernameInput.getText()));

        tokenConfirmationPanel.add(presentationText);
        tokenConfirmationPanel.add(usernamePanel);
        tokenConfirmationPanel.add(tokenPanel);
        tokenConfirmationPanel.add(okButton);

        this.add(tokenConfirmationPanel);
    }

    public void correctTokenDialog() {
        JOptionPane.showMessageDialog(this,
                "Congratulations! Your account is now validated.\nYou may log in now.",
                "Congratulations",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void wrongTokenWarning(){
        JOptionPane.showMessageDialog(this,
                "That token is wrong. Please make sure you have copied it well.",
                "Warning",
                JOptionPane.WARNING_MESSAGE);
    }
}
