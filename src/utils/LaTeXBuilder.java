package utils;

import models.TikzGraph;

public class LaTeXBuilder {
    private LaTeXBuilder() {
    }

    private final static String begin = "\\documentclass{article}\n\\usepackage{tikz}\n\\begin{document}\n\\begin{tikzpicture}\n";
    private final static String end = "\\end{tikzpicture}\n\\end{document}\n";

    public static String toLaTeX(TikzGraph graph) {
        return begin + graph.toString() + end;
    }
}
