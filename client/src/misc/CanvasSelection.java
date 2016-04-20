package misc;

import javax.swing.JPanel;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by aurelien on 19/04/16.
 */
public class CanvasSelection extends JPanel {

    private static final Color BKG_COLOR = new Color(0,50,120,50);

    private Rectangle selection;

    public CanvasSelection(Point pos){
        this.setSize(new Dimension(0,0));
        this.setBackground(BKG_COLOR);
        this.setLocation(pos);
        this.selection = null;
    }

    public void resize(Point bottom_right){
        int delta_x = (int) bottom_right.getX() - this.getX();
        int delta_y = (int) bottom_right.getY() - this.getY();
        this.setSize(new Dimension(delta_x, delta_y));
    }

    public boolean contains(Point point){
        return selection.contains(point);
    }

    public List<Point> getShapePoints(){
        ArrayList<Point> points = new ArrayList<>();
        for (int i = 0; i < selection.getWidth(); i++){
            for (int j = 0; j < selection.getHeight(); j++){
                points.add(new Point((int)(i+selection.getX()), (int) (j+selection.getY())));
            }
        }
        return points;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        selection = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
}
