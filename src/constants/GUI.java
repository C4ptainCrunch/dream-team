package constants;

/**
 * Created by nikita on 3/27/16.
 */
public final class GUI {
    public static final class Text {
        public static final String FILE_MENU = "File";
        public static final String SAVE = "Save";
        public static final String PDF = "Build PDF";
        public static final String EXIT = "Exit";
        public static final String VIEW_MENU = "View";
        public static final String GRID_VISIBILTY = "Show/Hide Grid";
        public static final String HELP_MENU = "Help";
        public static final String HELP = "See help panel";
        public static final String DIFF = "Show History";
        public static final String APP_NAME = "CreaTikZ";
    }

    public static final class ProjectManagementText {
        public static final String CREATE_PANEL = "Chose location to create your project";
        public static final String IMPORT_PANEL = "Chose location to import your project";
        public static final String DROPDOWN_HEADER = "Chose existing project from this list (if it exists) " +
                                                     "and press 'Import' or 'Rename'.";
        public static final String BLANK_INFO_PANEL = "INFORMATION ABOUT SELECTED PROJECT:\nProject Name: %s" +
                                                      "\nUser: %s\nLast revision: %s\n";
        public static final String DIFF_TEXT = "Diff History";
    }

    public static final class Config {
        public static final double ARROW_LENGTH = 30;
        public static final double ARROW_ANGLE = Math.PI/6;
    }
}
