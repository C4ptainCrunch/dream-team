package views.accounts;

import controllers.accounts.EditUserController;
import models.databaseModels.User;

import javax.swing.*;
import java.awt.*;

public class EditUserView extends JFrame{

    private EditUserController controller;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private String originalEmail;
    private User user;
    private JPanel panel;

    public EditUserView(final User user) {
        this.user = user;
        this.originalEmail = user.getEmail();
        this.controller = new EditUserController(this);
        this.render();
    }

    public final void render() {
        this.setSize(new Dimension(300,200));

        this.panel = new JPanel();
        this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.Y_AXIS));

        this.initFields();
        this.initButtons();
        this.add(this.panel);
        this.setVisible(true);
    }

    private void initFields() {
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridLayout(0,2));

        JLabel firstNameLabel = new JLabel("First Name: ");
        this.firstNameField = new JTextField(this.user.getFirstName());
        fieldsPanel.add(firstNameLabel);
        fieldsPanel.add(this.firstNameField);

        JLabel lastNameLabel = new JLabel("Last Name: ");
        this.lastNameField = new JTextField(this.user.getLastName());
        fieldsPanel.add(lastNameLabel);
        fieldsPanel.add(this.lastNameField);

        JLabel emailLabel = new JLabel("Email: ");
        this.emailField = new JTextField(this.user.getEmail());
        fieldsPanel.add(emailLabel);
        fieldsPanel.add(this.emailField);

        this.panel.add(fieldsPanel);
    }

    private void initButtons() {
        JButton ok = new JButton("Submit");
        this.getRootPane().setDefaultButton(ok);
        ok.addActionListener(actionEvent -> controller.submit());
        this.panel.add(ok);
    }


    public String getFirstName() {
        return this.firstNameField.getText();
    }

    public String getLastName() {
        return this.lastNameField.getText();
    }

    public String getEmail() {
        return this.emailField.getText();
    }

    public User getUser() {
        return user;
    }

    public String getOriginalEmail() {
        return originalEmail;
    }
}
