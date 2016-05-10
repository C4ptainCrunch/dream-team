package constants;

import utils.Dirs;

import java.awt.*;
import java.util.*;

/**
 * Created by nikita on 3/27/16.
 */
public final class GUI {
    public static final class MenuBar {
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
        public static final String OPTIONS_MENU = "Options";
        public static final String COLOR_BLIND = "ColorBlind mode";
    }

    public static final class ProjectManagement {
        public static final String CREATE_BUTTON = "Create";
        public static final String OPEN_BUTTON = "Open";
        public static final String RENAME_BUTTON = "Rename";
        public static final String CREATE_PANEL = "Choose location to create your project";
        public static final String IMPORT_PANEL = "Choose location to import your project";
        public static final String DROPDOWN_HEADER = "Choose existing project from this list" + " and press 'Open' or 'Rename'.";
        public static final String BLANK_INFO_PANEL = "INFORMATION ABOUT SELECTED PROJECT:\nDiagram Name: %s"
                + "\nUser: %s\nLast revision: %s\n";
        public static final String DIFF_TEXT = "Diff History";
    }

    public static final class LoginWindow {
        public static final String LOGIN_BUTTON = "Login";
        public static final String SIGNUP_BUTTON = "Sign Up";
        public static final String TOKEN_BUTTON = "Token Activation";
    }

    public static final class TokenWindow {
        public static final String WIN_LABEL = "An email has been sent to you. Please copy here the" +
                                           " token enclosed within the email and press OK.";
        public static final String OK_BUTTON = "OK";
        public static final String USER_LABEL = "Username: ";
        public static final String TOKEN_LABEL = "Token: ";
        public static final String TOKEN_VALID = "Congratulations! Your account is now validated.\\nYou may log in now.";
        public static final String TOKEN_WRONG = "That token is wrong. Please make sure you have copied it well.";
    }

    public static final class SignUp {
        public static final ArrayList<String> FIELD_LABELS = new ArrayList<String>(){{
            add("First Name: ");
            add("Last Name: ");
            add("Username: ");
            add("Email: ");
        }};

        public static final ArrayList<Integer> FIELD_SIZES = new ArrayList<Integer>(){{
            add(16);
            add(32);
            add(16);
            add(32);
        }};

        public static final String PASSWORD_LABEL = "Password: ";
        public static final String OK_BUTTON = "OK";
        public static final String CANCEL_BUTTON = "Cancel";

        public static final String NAMES_REGEX = "^[\\p{L} .'-]+$";
        public static final String USERNAME_REGEX = "[A-Za-z0-9]+";

    }

    public static final class Config {
        public static final double ARROW_LENGTH = 30;
        public static final double ARROW_ANGLE = Math.PI / 6;
    }

    public static final class Tabs {
        public static final String SHAPE_TAB = "<html>S<br>H<br>A<br>P<br>E<br>S</html>";
        public static final String TEMPLATE_TAB = "<html>T<br>E<br>M<br>P<br>L<br>A<br>T<br>E<br>S</html>";
    }

    public static final class Drag {
        public enum DropOptions {
            MOVE, ADD;
        }
    }

    public static final class Selection {
        public static final Color BKG_COLOR = new Color(0, 50, 120, 50);
    }

    public static final class Template {
        public static final String DIR = Dirs.getDataDir().resolve("templates").toString();
    }

    public static final class TextArea {
        public static final String DEFAULT_THEME = "syntax_themes/default.xml";
        public static final String DEFAULT_COLOR_BLINDNESS_THEME = "syntax_themes/default_color_blindness.xml";
    }
}
