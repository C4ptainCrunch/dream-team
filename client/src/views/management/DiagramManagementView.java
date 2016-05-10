package views.management;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class DiagramManagementView extends JFrame {

    private Object[] diagramNames;
    private JList<String> diagramList;

    public DiagramManagementView(Set<String> diagramNames){
        this.diagramNames = diagramNames.toArray();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.render();
    }

    public final void render(){
        this.setTitle("TikzCreator : choose a diagram");
        this.setPreferredSize(new Dimension(900, 200));
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        createDiagramsList();

        this.pack();
        this.setVisible(true);

    }

    private void createDiagramsList(){
        JPanel diagramPanel = new JPanel();

        diagramList = new JList(this.diagramNames); //data has type Object[]
        diagramList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        diagramList.setLayoutOrientation(JList.VERTICAL);
        diagramList.setVisibleRowCount(-1);

        JScrollPane listScroller = new JScrollPane(diagramList);
        listScroller.setPreferredSize(new Dimension(250, 80));

        diagramPanel.add(listScroller);
        this.add(diagramPanel, BorderLayout.NORTH);

    }
}
