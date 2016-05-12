package misc.utils;

import org.junit.Test;

import javax.swing.*;

import java.awt.*;
import java.awt.geom.Point2D;

import static org.junit.Assert.*;

/**
 * Created by jhellinckx on 12/05/16.
 */
public class ConverterTest extends ClientTest{
    private double precision = 0.001;
    @Test
    public void testTikz2swing() throws Exception {
        try {
            double testedWidthCentimeters = 10;
            double testedHeightCentimeters = 10;
            int testedWidth = (int) Converter.centimetersToPixels(testedWidthCentimeters);
            int testedHeight = (int) Converter.centimetersToPixels(testedHeightCentimeters);
            float testedPointxcm = 2.5f;
            float testedPointycm = 2.5f;
            float testedPointx = (float) Converter.centimetersToPixels(testedPointxcm);
            float testedPointy = (float) Converter.centimetersToPixels(testedPointycm);
            Point2D.Float testedPoint = new Point2D.Float(testedPointxcm, testedPointycm);
            Point2D.Float expectedPoint = new Point2D.Float((int) (testedWidth / 2 + testedPointx), (int) (testedHeight / 2 - testedPointy));
            assertEquals(expectedPoint, Converter.tikz2swing(testedPoint, testedWidth, testedHeight));
        }catch(HeadlessException e){}
    }

    @Test
    public void testSwing2tikz() throws Exception {
        try {
            float testedWidth = 200;
            float testedHeight = 200;
            float testedPointx = 50;
            float testedPointy = 50;
            Point2D.Float testedPoint = new Point2D.Float(testedPointx, testedPointy);
            Point2D.Float expectedPointPixels = new Point2D.Float((testedPointx - testedWidth / 2), (testedHeight / 2 - testedPointy));

            Point2D.Float actualPointCm = Converter.swing2tikz(testedPoint, (int) testedWidth, (int) testedHeight);
            assertEquals(Converter.pixelsToCentimeters(expectedPointPixels.getX()), actualPointCm.getX(), precision);
            assertEquals(Converter.pixelsToCentimeters(expectedPointPixels.getY()), actualPointCm.getY(), precision);
        }catch(HeadlessException e){}
    }

}