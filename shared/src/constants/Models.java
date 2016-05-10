package constants;

import java.awt.*;

/**
 * Created by jhellinckx on 29/02/16.
 */
public final class Models {
    public static final class DEFAULT {
        public static final Color COLOR = Color.black;
        public static final int STROKE = 1;
        public static final int X = 0;
        public static final int Y = 0;
        public static final String LABEL = "";
        public static final int WIDTH = 1;
        public static final int LENGTH = 100;
        public static final int SIDES = 3;
        public static final Color BACKGROUND_COLOR = Color.white;
        public static final int EDGE_X_LENGTH = 100;
    }

    public class Project {
        public static final String SAVE_FILE = "save.tikz";
        public static final String DIFF_FILE = "diffs";
    }

    public class Graph {
        public static final String latexPrelude = "\\documentclass{article}\n\\usepackage{tikz}\n\\usetikzlibrary{shapes.geometric}\n\\begin{document}\n\\begin{tikzpicture}[x=0.0625em,y=0.0625em]\n";
        public static final String latexPostlude = "\\end{tikzpicture}\n\\end{document}\n";
    }
}
