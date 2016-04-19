package views.editor.canvas.drawers;

import models.tikz.TikzComponent;
import views.editor.canvas.drawables.DrawableTikzNode;

/**
 * Created by jhellinckx on 18/04/16.
 */
public class NodeDrawer extends ComponentDrawer {
    @Override
    public DrawableTikzNode toDrawable(TikzComponent component){
        DrawableTikzNode drawable = new DrawableTikzNode(component);
        return drawable;
    }
}
