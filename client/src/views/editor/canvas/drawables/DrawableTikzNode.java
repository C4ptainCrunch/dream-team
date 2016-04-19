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
    public void draw(Graphics2D g, JComponent panel) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Stroke old_stroke = g.getStroke();
        Color old_color = g.getColor();

        TikzNode component = getComponent();



        g.setStroke(new BasicStroke(component.getStroke()));
        if(component.isShape()){
            g.setColor(((TikzShape)component).getBackgroundColor());
        }

        Area area = new Area(getArea());
        /* First center it before applying the converter. */
        Rectangle bounds = area.getBounds();
        area.transform(new AffineTransform(1, 0, 0, 1, - bounds.getWidth()/2, - bounds.getHeight()/2 ));
        /* Convert the tikz point of the model to a swing point and translate the Area with the resulting point. */
        Point swingPosition = Converter.tikz2swing(component.getPosition(), panel);
        AffineTransform toSwingTransform = new AffineTransform(1, 0, 0, 1, swingPosition.getX(), swingPosition.getY());
        area.transform(toSwingTransform);
        setDrawing(area);
        if (component.getLabel() != Models.DEFAULT.LABEL) {
            g.drawString(component.getLabel(), 0, 0);
        }

        g.fill(area);
        g.setColor(component.getColor());


        g.draw(area);

        g.setColor(old_color);
        g.setStroke(old_stroke);
    }
}
