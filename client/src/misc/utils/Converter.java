package misc.utils;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jhellinckx on 19/04/16.
 */

public class Converter {
    public static Point tikz2swing(Point tikzPosition, int panelWidth, int panelHeight){
        int swingX = (int)(panelWidth / 2 + tikzPosition.getX());
        int swingY = (int)(panelHeight / 2 - tikzPosition.getY());
        return new Point(swingX, swingY);
    }

    public static Point tikz2swing(Point tikzPosition, JComponent panel){
        return Converter.tikz2swing(tikzPosition, panel.getWidth(), panel.getHeight());
    }

    public static Point swing2tikz(Point swingPosition, int panelWidth, int panelHeight){
        int tikzX = (int)(swingPosition.getX() - panelWidth / 2);
        int tikzY = (int)(panelHeight / 2 - swingPosition.getY());
        return new Point(tikzX, tikzY);
    }

    public static Point swing2tikz(Point swingPosition, JComponent panel){
        return Converter.swing2tikz(swingPosition, panel.getWidth(), panel.getHeight());
    }
}
