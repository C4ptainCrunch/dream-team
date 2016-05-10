package constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import static constants.Errors.*;
import static constants.GUI.*;

/**
 * Created by jhellinckx on 10/05/16.
 */
public class ClientPropertiesLoader extends PropertiesLoader {
    private static final String ERRORS_PROPERTIES_FILENAME = "errors.properties";
    private static final String GUI_PROPERTIES_FILENAME = "gui.properties";
    private static final String WARNING_PROPERTIES_FILENAME = "warning.properties";

    public static void loadAll(){
        PropertiesLoader.loadAll();
        loadErrorsProperties();
        loadGUIProperties();
        loadWarningProperties();
    }

    public static void loadErrorsProperties(){
        PropertiesLoader.load(ERRORS_PROPERTIES_FILENAME, new ErrorsPropertiesReader());
    }

    public static void loadGUIProperties(){
        PropertiesLoader.load(GUI_PROPERTIES_FILENAME, new GUIPropertiesReader());
    }

    public static void loadWarningProperties(){
        PropertiesLoader.load(WARNING_PROPERTIES_FILENAME, new WarningPropertiesReader());
    }
}

class ErrorsPropertiesReader implements PropertiesReader{

    @Override
    public void read(Properties properties){
        ERROR = properties.getProperty("ERROR");
        CREATE_ERROR = properties.getProperty("CREATE_ERROR");
        SAVE_ERROR = properties.getProperty("SAVE_ERROR");
        FILL_VIEW_ERROR = properties.getProperty("FILL_VIEW_ERROR");
        OPEN_ERROR = properties.getProperty("OPEN_ERROR");
        RENAME_ERROR = properties.getProperty("RENAME_ERROR");
        CREATE_TEMPLATE_DIR_ERROR = properties.getProperty("CREATE_TEMPLATE_DIR_ERROR");
        SAVE_TEMPLATE_ERROR = properties.getProperty("SAVE_TEMPLATE_ERROR");
        LOAD_TEMPLATES_ERROR = properties.getProperty("LOAD_TEMPLATES_ERROR");
        LOAD_TEXT_AREA_ERROR = properties.getProperty("LOAD_TEXT_AREA_ERROR");
        ACTIVE_ACCOUNT_FIRST = properties.getProperty("ACTIVE_ACCOUNT_FIRST");
        LOGIN_FAILED = properties.getProperty("LOGIN_FAILED");
        SIGNUP_FAILED = properties.getProperty("SIGNUP_FAILED");
    }
}

class GUIPropertiesReader implements PropertiesReader{

    @Override
    public void read(Properties properties) {
        MenuBar.FILE_MENU = properties.getProperty("FILE_MENU");
        MenuBar.SAVE = properties.getProperty("SAVE");
        MenuBar.PDF = properties.getProperty("PDF");
        MenuBar.EXIT = properties.getProperty("EXIT");
        MenuBar.VIEW_MENU = properties.getProperty("VIEW_MENU");
        MenuBar.GRID_VISIBILTY = properties.getProperty("GRID_VISIBILTY");
        MenuBar.HELP_MENU = properties.getProperty("HELP_MENU");
        MenuBar.HELP = properties.getProperty("HELP");
        MenuBar.DIFF = properties.getProperty("DIFF");
        MenuBar.APP_NAME = properties.getProperty("APP_NAME");
        MenuBar.OPTIONS_MENU = properties.getProperty("OPTIONS_MENU");
        MenuBar.COLOR_BLIND = properties.getProperty("COLOR_BLIND");
        MenuBar.EDIT_MENU = properties.getProperty("EDIT_MENU");
        MenuBar.UNDO = properties.getProperty("UNDO");
        MenuBar.REDO = properties.getProperty("REDO");

        ProjectManagement.CREATE_BUTTON = properties.getProperty("CREATE_BUTTON");
        ProjectManagement.OPEN_BUTTON = properties.getProperty("OPEN_BUTTON");
        ProjectManagement.RENAME_BUTTON = properties.getProperty("RENAME_BUTTON");
        ProjectManagement.CREATE_PANEL = properties.getProperty("CREATE_PANEL");
        ProjectManagement.IMPORT_PANEL = properties.getProperty("IMPORT_PANEL");
        ProjectManagement.DROPDOWN_HEADER = properties.getProperty("DROPDOWN_HEADER");
        ProjectManagement.BLANK_INFO_PANEL = properties.getProperty("BLANK_INFO_PANEL");
        ProjectManagement.DIFF_TEXT = properties.getProperty("DIFF_TEXT");

        LoginWindow.LOGIN_BUTTON = properties.getProperty("LOGIN_BUTTON");
        LoginWindow.SIGNUP_BUTTON = properties.getProperty("SIGNUP_BUTTON");
        LoginWindow.TOKEN_BUTTON = properties.getProperty("TOKEN_BUTTON");
        LoginWindow.EDIT_BUTTON = properties.getProperty("EDIT_BUTTON");

        TokenWindow.WIN_LABEL = properties.getProperty("WIN_LABEL");
        TokenWindow.OK_BUTTON = properties.getProperty("OK_BUTTON");
        TokenWindow.USER_LABEL = properties.getProperty("USER_LABEL");
        TokenWindow.TOKEN_LABEL = properties.getProperty("TOKEN_LABEL");
        TokenWindow.TOKEN_VALID = properties.getProperty("TOKEN_VALID");
        TokenWindow.TOKEN_WRONG = properties.getProperty("TOKEN_WRONG");
        
        SignUp.FIELD_LABELS = new ArrayList<>(Arrays.asList(properties.getProperty("FIELD_LABELS").split("#")));

        String[] sizesStrings = properties.getProperty("FIELD_SIZES").split("#");
        SignUp.FIELD_SIZES = new ArrayList<>();
        for(int i = 0; i < sizesStrings.length; ++i){ SignUp.FIELD_SIZES.add(Integer.valueOf(sizesStrings[i])) }

        SignUp.PASSWORD_LABEL = properties.getProperty("PASSWORD_LABEL");
        SignUp.OK_BUTTON = properties.getProperty("OK_BUTTON");
        SignUp.CANCEL_BUTTON = properties.getProperty("CANCEL_BUTTON");
        SignUp.NAMES_REGEX = properties.getProperty("NAMES_REGEX");
        SignUp.USERNAME_REGEX = properties.getProperty("USERNAME_REGEX");

        Drawing.ARROW_LENGTH = Double.valueOf(properties.getProperty("ARROW_LENGTH"));
        Drawing.ARROW_ANGLE = Double.valueOf(properties.getProperty("ARROW_ANGLE"));

        Tabs.HAPE_TAB = properties.getProperty("HAPE_TAB");
        Tabs.EMPLATE_TAB = properties.getProperty("EMPLATE_TAB");

        String[] rgbStrings = properties.getProperty("BKG_COLOR").split("#");
        int[] rgb = new int[4];
        for(int i = 0; i < rgbStrings.length; ++i){ rgb[i] = Integer.parseInt(rgbStrings[i]); }
        Selection.BKG_COLOR = new Color(rgb[0], rgb[1], rgb[2], rgb[3]);

        Template.DIR = Dirs.getDataDir().resolve(properties.getProperty("TEMPLATE_DIR")).toString();

        TextArea.DEFAULT_THEME = properties.getProperty("DEFAULT_THEME");
        TextArea.DEFAULT_COLOR_BLINDNESS_THEME = properties.getProperty("DEFAULT_COLOR_BLINDNESS_THEME");
    

    public static final class TextArea {
        public static String DEFAULT_THEME = "syntax_themes/default.xml";
        public static String DEFAULT_COLOR_BLINDNESS_THEME = "syntax_themes/default_color_blindness.xml";
    }

    }
}

class WarningPropertiesReader implements PropertiesReader{

    @Override
    public void read(Properties properties) {

    }
}
