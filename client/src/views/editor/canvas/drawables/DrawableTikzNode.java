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


        if (component.getLabel() != Models.DEFAULT.LABEL) {
            g.drawString(component.getLabel(), 0, 0);
        }

        for(Shape shape : getShapes()){
            g.fill(shape);
        }
        g.setColor(component.getColor());
        for(Shape shape : getShapes()){
            g.draw(shape);
        }

        g.setColor(old_color);
        g.setStroke(old_stroke);
    }
}
