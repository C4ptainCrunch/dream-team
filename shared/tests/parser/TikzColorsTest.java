package parser;

import static org.junit.Assert.assertEquals;

import java.awt.*;

import org.junit.Test;

/**
 * Created by jhellinckx on 11/05/16.
 */
public class TikzColorsTest {

    @Test
    public void testNearestColors() throws Exception {
        Color expectedColor = new Color(0x80, 0x80, 0x00);
        Color closeToExpected = new Color(0x80, 0x80, 0x03);
        assertEquals(expectedColor, TikzColors.nearestTikzColor(closeToExpected));
    }

    @Test
    public void testString2Color() throws Exception {
        Color expectedColor = new Color(0x00, 0x80, 0x80);
        String colorString = "teal";
        assertEquals(expectedColor, TikzColors.StringToColor(colorString));
    }

    @Test
    public void testColor2String() throws Exception {
        Color color = new Color(0x00, 0x80, 0x80);
        String expectedString = "teal";
        assertEquals(expectedString, TikzColors.ColorToString(color));
    }

}
