package constants;

import java.util.Properties;
import static constants.Errors.*;

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

    }
}

class WarningPropertiesReader implements PropertiesReader{

    @Override
    public void read(Properties properties) {

    }
}
