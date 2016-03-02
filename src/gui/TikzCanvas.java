
package gui;

import models.TikzGraph;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class TikzCanvas extends JPanel implements Observer {

    private TikzGraph graph = new TikzGraph();
    private CanvasControler controler;

    public TikzCanvas(CanvasControler controler){
        this.controler = controler;
    }

    public void drawGraph(Graphics g){

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        drawGraph(g);
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}