package views.accounts;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import constants.GUI;
import controllers.accounts.EditUserController;
import models.databaseModels.User;

public class EditUserView extends JFrame{

    private EditUserController controller;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private String originalEmail;
    private User user;
    private JPanel panel;

    public EditUserView(User user) {
        this.user = user;
        this.originalEmail = user.getEmail();
        this.controller = new EditUserController(this);
        this.render();
    }

    public final void render() {
        this.setSize(new Dimension(300,200));
        this.panel = new JPanel();
        this.add(this.panel);

        this.initFields();
        this.initButtons();
        this.setVisible(true);
    }

    private void initFields() {
        JPanel fieldsPanel = new JPanel();

        this.firstNameField = new JTextField(this.user.getFirstName());
        fieldsPanel.add(this.firstNameField);

        this.lastNameField = new JTextField(this.user.getLastName());
        fieldsPanel.add(this.lastNameField);

        this.emailField = new JTextField(this.user.getEmail());
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
