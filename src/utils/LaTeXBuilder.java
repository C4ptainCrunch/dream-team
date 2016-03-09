package utils;

import models.TikzGraph;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Created by acaccia on 09/03/16.
 */
public class LaTeXBuilder {
    private final static String begin = "\\documentclass{article}\n\\usepackage{tikz}\n\\begin{document}\n\\begin{tikzpicture}\n";
    private final static String end = 	"\\end{tikzpicture}\n\\end{document}\n";

    public static String fromGraph (TikzGraph graph) {
        return begin + graph.toString() + end;
    }

    public static void graph2File (String filename, TikzGraph graph) throws FileNotFoundException, UnsupportedEncodingException{
        PrintWriter writer = new PrintWriter(filename, "utf-8");
        writer.print(fromGraph(graph));
    }
}
