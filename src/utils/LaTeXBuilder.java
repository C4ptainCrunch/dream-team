package utils;

import models.tikz.TikzGraph;

public final class LaTeXBuilder {
    private final static String begin = "\\documentclass{article}\n\\usepackage{tikz}\n\\begin{document}\n\\begin{tikzpicture}\n";
    private final static String end = "\\end{tikzpicture}\n\\end{document}\n";

    private LaTeXBuilder() {
    }

    public static String toLaTeX(TikzGraph graph) {
        return begin + graph.toString() + end;
    }
}
