package parser;

import constants.Models;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by acaccia on 22/04/16.
 */
public class TikzColors {
    private final static HashMap<String, Color> string2color = new HashMap<String, Color>() {{
        put("black", new Color(0x00, 0x00, 0x00));
        put("blue", new Color(0x00, 0x00, 0xFF));
        put("brown", new Color(0xA5, 0x2A, 0x2A));
        put("cyan", new Color(0x00, 0xFF, 0xFF));
        put("darkgray", new Color(0xA9, 0xA9, 0xA9));
        put("gray", new Color(0x80, 0x80, 0x80));
        put("green", new Color(0x00, 0x80, 0x00));
        put("lightgray", new Color(0xD3, 0xD3, 0xD3));
        put("lime", new Color(0x00, 0xFF, 0x00));
        put("magenta", new Color(0xFF, 0x00, 0xFF));
        put("olive", new Color(0x80, 0x80, 0x00));
        put("orange", new Color(0xFF, 0xA5, 0x00));
        put("pink", new Color(0xFF, 0xC0, 0xCB));
        put("purple", new Color(0x80, 0x00, 0x80));
        put("red", new Color(0xFF, 0x00, 0x00));
        put("teal", new Color(0x00, 0x80, 0x80));
        put("violet", new Color(0xEE, 0x82, 0xEE));
        put("white", new Color(0xFF, 0xFF, 0xFF));
        put("yellow", new Color(0xFF, 0xFF, 0x00));
    }};

    private final static HashMap<Color, String> color2string = new HashMap<Color, String>() {{
        for (Entry<String, Color> entry: string2color.entrySet()){
            put(entry.getValue(), entry.getKey());
        }
    }};

    /**
     * Cast a String to an java.awt.Color object (using a data member HashMap).
     * @param name  the color name.
     * @return an awt.Color object.
     */

    public static Color StringToColor(String name){
        return string2color.getOrDefault(name.toLowerCase(), Models.DEFAULT.COLOR);
    }

    /**
     * Cast a java.awt.Color object to a String (using a data member HashMap).
     * @param color the awt.Color object
     * @return the corresponding String.
     */

    public static String ColorToString(Color color){
        return color2string.getOrDefault(color, color2string.get(Models.DEFAULT.COLOR));
    }
}
