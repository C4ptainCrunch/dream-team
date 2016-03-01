package gui;


import javax.swing.*;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;


public class TikzCanvas extends JPanel{
    private static final int column_count= 100;
    private static final int row_count = 100;
    private static final int additionnal_column_count = 10;         // Ensure that the are cells everywhere on the screen.
    private List<Rectangle> cells;
    private boolean grid_visible;

    public TikzCanvas(){
        cells = new ArrayList<>();
        grid_visible = true;
    }

    private void initGridParameters(){
        int width = getWidth();
        int cellWidth = width/column_count;             // width is used 2 times to draw the cells in a square shape.
        int cellHeight = width/row_count;

        if(cells.isEmpty()){
            for (int row = 0; row < row_count; row++){
                for (int col = 0; col < column_count+additionnal_column_count; col++){
                    Rectangle cell = new Rectangle((col*cellWidth),(row*cellHeight),
                            cellWidth, cellHeight);
                    cells.add(cell);
                }
            }
        }
    }

    private void drawGrid(Graphics g){
        Graphics2D g2d = (Graphics2D) g.create();
        initGridParameters();

        g2d.setColor(Color.LIGHT_GRAY);
        for (Rectangle cell: cells){
            g2d.draw(cell);
        }
        g2d.dispose();
    }

    public void setGridVisible(boolean visible){
        grid_visible = visible;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if (grid_visible) {
            drawGrid(g);
        }
    }
}
