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

        JLabel presentationText = new JLabel(GUI.TokenWindow.LABEL);
        JTextField tokenInput = new JTextField();
        JButton okButton = new JButton(GUI.TokenWindow.OK_BUTTON);
        okButton.addActionListener(e -> controller.validateToken(tokenInput.getText()));

        tokenConfirmationPanel.add(presentationText);
        tokenConfirmationPanel.add(tokenInput);
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
