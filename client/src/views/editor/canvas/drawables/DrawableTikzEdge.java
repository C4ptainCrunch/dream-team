package views.editor.canvas.drawables;


import models.tikz.TikzComponent;
import models.tikz.TikzEdge;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

public class DrawableTikzEdge extends DrawableTikzComponent {

    public DrawableTikzEdge(TikzComponent component){
        super(component);
    }

    @Override
    public TikzEdge getComponent(){
        return (TikzEdge)super.getComponent();
    }

    @Override
    public void draw(Graphics2D g){
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Stroke old_stroke = g.getStroke();
        Color old_color = g.getColor();

        TikzEdge component = getComponent();

        g.setColor(component.getColor());
        for(Shape shape : getShapes()){
            g.fill(shape);
        }

        for(Shape stroke : getStrokes()){
            g.fill(stroke);
        }


        g.setColor(old_color);
        g.setStroke(old_stroke);
    }
}
