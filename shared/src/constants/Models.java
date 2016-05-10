package constants;

import java.awt.*;
import java.util.ResourceBundle;

public final class Models {
    public static final class DEFAULT {
        public static Color COLOR = Color.black;
        public static int STROKE = 1;
        public static int X = 0;
        public static int Y = 0;
        public static String LABEL = "";
        public static int LENGTH = 100;
        public static int SIDES = 3;
        public static Color BACKGROUND_COLOR = Color.white;
        public static int EDGE_X_LENGTH = 100;
    }

    public static class Project {
        public static String SAVE_FILE = "save.tikz";
        public static String DIFF_FILE = "diff";
    }

    public static class Graph {
        public static String LATEX_PRELUDE = "\\documentclass{article}\n\\usepackage{tikz}\n\\usetikzlibrary{shapes.geometric}\n\\begin{document}\n\\begin{tikzpicture}\n";
        public static String LATEX_POSTLUDE = "\\end{tikzpicture}\n\\end{document}\n";
    }

}
