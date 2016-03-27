package utils;

import models.TikzGraph;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class LaTeXBuilder {
    private final static String begin = "\\documentclass{article}\n\\usepackage{tikz}\n\\begin{document}\n\\begin{tikzpicture}\n";
    private final static String end = 	"\\end{tikzpicture}\n\\end{document}\n";

    public static String toLaTeX (TikzGraph graph) {
        return begin + graph.toString() + end;
    }
}
