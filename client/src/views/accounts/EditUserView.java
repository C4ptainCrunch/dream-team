package views.accounts;

import constants.GUI;
import controllers.accounts.EditUserController;
import misc.utils.JTextFieldSizeLimiter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


/**
 * Created by bambalaam on 27/04/16.
 */
public class EditUserView extends JFrame{

    private EditUserController controller;
    LoginWindowView loginView;
    JTextField firstNameField;
    JTextField lastNameField;
    JTextField usernameField;
    JTextField emailField;
    ArrayList<JTextField> fields;
    String originalUsername;
    String originalEmail;

    public EditUserView(LoginWindowView loginView, String username) {
        this.controller = new EditUserController(this);
        this.loginView = loginView;
        this.originalUsername = username;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.render();
    }

    /**
     * Render the view.
     */

    public final void render() {
        this.setTitle("TikzCreator : Edit Profile");
        this.setPreferredSize(new Dimension(300,130));
        controller.launchEditPanel(this.originalUsername);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
    }

    /**
     * Display the sign up panel.
     */

    public void showEditPanel(ArrayList<String> data) {
        this.originalEmail = data.get(3);
        JPanel editPanel = new JPanel();
        editPanel.setLayout(new BoxLayout(editPanel, BoxLayout.Y_AXIS));
        initInformationPanel(editPanel, data);
        initButtonsPanel(editPanel);
        this.add(editPanel);
    }

    private void initInformationPanel(JPanel signUpPanel, ArrayList<String> data) {
        JPanel informationPanel = new JPanel();
        informationPanel.setLayout(new BoxLayout(informationPanel, BoxLayout.Y_AXIS));

        this.fields = new ArrayList<>();
        this.firstNameField = new JTextField();
        this.lastNameField = new JTextField();
        this.usernameField = new JTextField();
        this.emailField = new JTextField();
        this.fields.add(this.firstNameField);
        this.fields.add(this.lastNameField);
        this.fields.add(this.usernameField);
        this.fields.add(this.emailField);

        initInfoPanelOptions(informationPanel, data);
        signUpPanel.add(informationPanel);
    }

    private void initInfoPanelOptions(JPanel informationPanel, ArrayList<String> data) {

        for(int i=0; i< this.fields.size(); i++){
            JPanel newPanel = new JPanel();
            newPanel.setLayout(new GridLayout(1,2));
            newPanel.setMaximumSize( new Dimension(500,100));
            newPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel thisLabel = new JLabel(GUI.SignUp.FIELD_LABELS.get(i));
            this.fields.get(i).setText(data.get(i));
            //this.fields.get(i).setDocument(new JTextFieldSizeLimiter(GUI.SignUp.FIELD_SIZES.get(i)));

            newPanel.add(thisLabel);
            newPanel.add(this.fields.get(i));
            informationPanel.add(newPanel);
        }

    }

    private void initButtonsPanel(JPanel signupPanel) {
        JPanel buttons = new JPanel();
        buttons.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton OKButton = new JButton(GUI.SignUp.OK_BUTTON);
        OKButton.addActionListener(e -> controller.validateFields(this.fields,this.originalUsername,
                                                                  this.originalEmail));

        JButton cancelButton = new JButton(GUI.SignUp.CANCEL_BUTTON);
        cancelButton.addActionListener(e -> controller.cancelEdit());

        buttons.add(OKButton);
        buttons.add(cancelButton);

        signupPanel.add(buttons);
    }

    /**
     * Show a warning dialog
     * @param warningText The warning text displayed in the dialog
     */

    public void initWarning(String warningText) {
        JOptionPane.showMessageDialog(this, warningText, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    public void showLogginView(){
        this.loginView.setVisible(true);
    }

    public void hideLogginView(){
        this.loginView.setVisible(false);
    }
}
