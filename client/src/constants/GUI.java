package constants;

import java.awt.*;
import java.util.List;

public final class GUI {
    public static final class MenuBar {
        public static String FILE_MENU;
        public static String SAVE;
        public static String PDF;
        public static String EXIT;
        public static String VIEW_MENU;
        public static String GRID_VISIBILTY;
        public static String HELP_MENU;
        public static String HELP;
        public static String DIFF;
        public static String APP_NAME;
        public static String OPTIONS_MENU;
        public static String COLOR_BLIND;
        public static String EDIT_MENU;
        public static String UNDO;
        public static String REDO;
        public static String OPEN;
        public static String OPEN_DIAGRAM;
        public static String SYNC;
    }

    public static final class ProjectManagement {

        public static String CREATE_BUTTON;
        public static String OPEN_PROJECT_BUTTON;
        public static String OPEN_RECENT_BUTTON;
        public static String RENAME_BUTTON;
        public static String CREATE_PANEL;
        public static String IMPORT_PANEL;
        public static String DROPDOWN_HEADER;
        public static String BLANK_INFO_PANEL;
        public static String DIFF_TEXT;
    }

    public static final class LoginWindow {
        public static String LOGIN_BUTTON;
        public static String SIGNUP_BUTTON;
        public static String TOKEN_BUTTON;
        public static String EDIT_BUTTON;

    }

    public static final class TokenWindow {
        public static String WIN_LABEL;
        public static String OK_BUTTON;
        public static String USER_LABEL;
        public static String TOKEN_LABEL;
        public static String TOKEN_VALID;
        public static String TOKEN_WRONG;
    }

    public static final class SignUp {
        public static List<String> FIELD_LABELS;

        public static List<Integer> FIELD_SIZES;

        public static String PASSWORD_LABEL;
        public static String OK_BUTTON;
        public static String CANCEL_BUTTON;

        public static String NAMES_REGEX;
        public static String USERNAME_REGEX;

    }

    public static final class Drawing {
        public static double ARROW_LENGTH;
        public static double ARROW_ANGLE;
    }

    public static final class Tabs {
        public static String SHAPE_TAB;
        public static String TEMPLATE_TAB;
    }

    public static final class Drag {
        public enum DropOptions {
            MOVE, ADD;
        }
    }

    public static final class Selection {
        public static Color BKG_COLOR;
    }

    public static final class Template {
        public static String DIR;
    }

    public static final class TextArea {
        public static String DEFAULT_THEME;
        public static String DEFAULT_COLOR_BLINDNESS_THEME;
    }
}
