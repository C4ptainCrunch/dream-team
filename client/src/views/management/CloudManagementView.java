package views.management;

import javax.swing.*;
import java.awt.*;

public class CloudManagementView extends JPanel {

    private ManagementView parentView;

    public CloudManagementView(ManagementView parentView) {
        this.parentView = parentView;
        this.render();
    }

    public void render() {
        this.setPreferredSize(new Dimension(600, 300));
        this.setSize(900, 200);

        JLabel label = new JLabel("TOTOTOT");
        this.add(label);
    }

    public void dispose() {
        this.parentView.dispose();
    }

}
