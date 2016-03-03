package gui.drawers;

import gui.drawables.Drawable;
import java.util.Vector;

public class UnknownDrawer implements Drawer{
    public Vector<Drawable> toDrawable(){
        return new Vector<>();
    }
}
