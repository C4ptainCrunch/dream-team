package views.editor.canvas.drawables;

import constants.Models;
import misc.utils.Converter;
import models.tikz.TikzComponent;
import models.tikz.TikzNode;
import models.tikz.TikzShape;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

public class DrawableTikzNode extends DrawableTikzComponent {
    public DrawableTikzNode(TikzComponent component){
        super(component);
    }

    @Override
    public TikzNode getComponent(){
        return (TikzNode)super.getComponent();
    }

    @Override
    public void draw(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Stroke old_stroke = g.getStroke();
        Color old_color = g.getColor();

        TikzNode component = getComponent();



        g.setStroke(new BasicStroke(component.getStroke()));
        if(component.isShape()){
            g.setColor(((TikzShape)component).getBackgroundColor());
        }

        for(Shape shape : getShapes()){
            g.fill(shape);
        }
        g.setColor(component.getColor());
        for(Shape shape : getShapes()){
            g.draw(shape);
        }

        //TODO use label color ! 
        g.setColor(old_color);
        String label = component.getLabel();
        if (!label.equals(Models.DEFAULT.LABEL)) {
            Rectangle2D bounds = getBounds();
            FontMetrics metrics = g.getFontMetrics();
            int x = ((int)bounds.getWidth() - metrics.stringWidth(label))/2 + (int)bounds.getX();
            int y = (((int)bounds.getHeight() + metrics.getHeight()))/2 + (int)bounds.getY();

            g.drawString(label, x, y);
        }



        g.setColor(old_color);
        g.setStroke(old_stroke);
    }
}
