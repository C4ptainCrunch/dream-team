package views.accounts;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import constants.GUI;
import controllers.accounts.EditUserController;
import models.users.User;

public class EditUserView extends JFrame{

    private EditUserController controller;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private String originalEmail;
    private User user;

    public EditUserView(User user) {
        this.user = user;
        this.originalEmail = user.getEmail();
        this.controller = new EditUserController(this);
        this.render();
    }

    public final void render() {
        this.setSize(new Dimension(300,200));
        this.initFields();
        this.setVisible(true);
    }

    private void initFields() {
        JPanel fieldsPanel = new JPanel();
        System.out.println(this.user.getFirstName());
        System.out.println(this.user.getLastName());
        System.out.println(this.user.getEmail());
        this.firstNameField = new JTextField(this.user.getFirstName());
        fieldsPanel.add(this.firstNameField);

        this.lastNameField = new JTextField(this.user.getLastName());
        fieldsPanel.add(this.lastNameField);

        this.emailField = new JTextField(this.user.getEmail());
        fieldsPanel.add(this.emailField);

        this.add(fieldsPanel);
    }


}
