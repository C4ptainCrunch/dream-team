package views.management;

import javax.swing.*;
import java.awt.*;

public class ManagementView extends JFrame {

    public ManagementView() {
        this.setTitle("Trololo");
        this.setSize(new Dimension(600, 600));

        getContentPane().setLayout(
                new BoxLayout(getContentPane(), BoxLayout.X_AXIS)
        );

        this.render();
        this.setVisible(true);
    }

    public void render() {

        ProjectManagementView pmv = new ProjectManagementView(this);
        this.add(pmv);
        CloudManagementView cmv = new CloudManagementView(this);
        this.add(cmv);

    }
}
