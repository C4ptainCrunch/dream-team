package constants;

import java.awt.*;

public final class Models {
    public static final class DEFAULT {
        public static final Color COLOR = Color.black;
        public static final int STROKE = 1;
        public static final int X = 0;
        public static final int Y = 0;
        public static final String LABEL = "";
        public static final float WIDTH = 1;
        public static final float LENGTH = 2.5f;
        public static final int SIDES = 1;
        public static final Color BACKGROUND_COLOR = Color.white;
        public static final float EDGE_X_LENGTH = 3;
    }

    public class Project {
        public static final String SAVE_FILE = "save.tikz";
        public static final String DIFF_FILE = "diffs";
    }

    public class Graph {
        public static final String latexPrelude = "\\documentclass{article}\n\\usepackage{tikz}\n\\usetikzlibrary{shapes.geometric}\n\\begin{document}\n\\begin{tikzpicture}\n";
        public static final String latexPostlude = "\\end{tikzpicture}\n\\end{document}\n";
    }
}
