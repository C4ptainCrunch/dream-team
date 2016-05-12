package constants;

import parser.TikzColors;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import static constants.Models.*;
import static constants.Network.*;
import static constants.ProjectConflicts.*;
import static constants.SyncModeSelection.*;


public class PropertiesLoader {
    private static final String MODELS_PROPERTIES_FILENAME = "models.properties";
    private static final String NETWORK_PROPERTIES_FILENAME = "network.properties";
    private static final String UTILS_PROPERTIES_FILENAME = "utils.properties";
    private static final String SYNCMODESELECTION_PROPERTIES_FILENAME = "syncmodeselection.properties";
    private static final String PROJECTSCONFLICTS_PROPERTIES_FILENAME = "projectconflicts.properties";

    protected static void load(String filename, PropertiesReader reader){
        InputStream stream = PropertiesLoader.class.getClassLoader().getResourceAsStream(filename);
        try {
            if(stream == null) { throw new IOException(); }
            Properties properties = new Properties();
            properties.load(stream);
            reader.read(properties);

        } catch(IOException e){
            System.err.println("Could not load properties file '" + filename + "'. Using default values. ");
        } catch (ClassCastException e){
            System.err.println("Syntax error in property file '" + filename + "'.");
        } catch (NumberFormatException e){
            System.err.println("Syntax error in property file '" + filename + "'.");
        }
    }

    public static void loadAll(){
        loadModelsProperties();
        loadNetworkProperties();
        loadUtilsProperties();
        loadsyncmodeselectionProperties();
        loadProjectConflictsProperties();
    }

    public static void loadModelsProperties(){
        load(MODELS_PROPERTIES_FILENAME, new ModelsPropertiesReader());
    }
    public static void loadNetworkProperties(){
        load(NETWORK_PROPERTIES_FILENAME, new NetworkPropertiesReader());
    }
    public static void loadUtilsProperties(){
        load(UTILS_PROPERTIES_FILENAME, new UtilsPropertiesReader());
    }
    public static void loadsyncmodeselectionProperties() { load(SYNCMODESELECTION_PROPERTIES_FILENAME, new SyncmodeSelectionPropertiesReader());}
    public static void loadProjectConflictsProperties(){PropertiesLoader.load(PROJECTSCONFLICTS_PROPERTIES_FILENAME, new ProjectConflictsPropertiesReader());}
}

interface PropertiesReader{
    void read(Properties properties);
}

class ModelsPropertiesReader implements PropertiesReader{
    @Override
    public void read(Properties properties){
        DEFAULT.STROKE = Integer.valueOf(properties.getProperty("DEFAULT_STROKE_WIDTH"));
        DEFAULT.COLOR = TikzColors.StringToColor(properties.getProperty("DEFAULT_OUTLINE_COLOR"));
        DEFAULT.BACKGROUND_COLOR = TikzColors.StringToColor(properties.getProperty("DEFAULT_BACKGROUND_COLOR"));
        DEFAULT.X = Integer.valueOf(properties.getProperty("DEFAULT_X_POSITION"));
        DEFAULT.Y = Integer.valueOf(properties.getProperty("DEFAULT_Y_POSITION"));
        DEFAULT.LABEL = properties.getProperty("DEFAULT_LABEL");
        DEFAULT.LENGTH = Float.valueOf(properties.getProperty("DEFAULT_LENGTH"));
        DEFAULT.SIDES = Integer.valueOf(properties.getProperty("DEFAULT_POLYGON_SIDES"));
        DEFAULT.EDGE_X_LENGTH = Float.valueOf(properties.getProperty("DEFAULT_EDGE_X_LENGTH"));

        Project.SAVE_FILE = properties.getProperty("DEFAULT_SAVE_FILENAME");
        Project.DIFF_FILE = properties.getProperty("DEFAULT_DIFF_FILENAME");

        Graph.LATEX_PRELUDE = properties.getProperty("DEFAULT_LATEX_PRELUDE");
        Graph.LATEX_POSTLUDE = properties.getProperty("DEFAULT_LATEX_POSTLUDE");

    }
}

class NetworkPropertiesReader implements  PropertiesReader{
    @Override
    public void read(Properties properties){
        HOST.HOSTNAME = properties.getProperty("HOSTNAME");
        HOST.PORT = Integer.valueOf(properties.getProperty("PORT"));
        HOST.COMPLETE_HOSTNAME = HOST.HOSTNAME + ":" + Integer.toString(HOST.PORT);

        Login.LOGIN_OK = properties.getProperty("LOGIN_OK");
        Login.LOGIN_FAILED = properties.getProperty("LOGIN_FAILED");
        Login.ACCOUNT_NOT_ACTIVATED = properties.getProperty("ACCOUNT_NOT_ACTIVATED");

        Token.TOKEN_OK = properties.getProperty("TOKEN_OK");

        Signup.SIGN_UP_OK = properties.getProperty("SIGN_UP_OK");
        Signup.SIGN_UP_FAILED = properties.getProperty("SIGN_UP_FAILED");
        Signup.FIELDS_NAMES = new ArrayList(Arrays.asList(properties.getProperty("FIELDS_NAMES").split("#")));
    }
}

class UtilsPropertiesReader implements  PropertiesReader{
    @Override
    public void read(Properties properties){
        Utils.HISTORY_PATH = properties.getProperty("HISTORY_PATH");
        Utils.LINUX_PATH = properties.getProperty("LINUX_PATH");
        Utils.MAC_PATH = properties.getProperty("MAC_PATH");
        Utils.WINDOWS_PATH_ONE = properties.getProperty("WINDOWS_PATH_ONE");
        Utils.WINDOWS_PATH_TWO = properties.getProperty("WINDOWS_PATH_TWO");
        Utils.DATE_FORMAT = properties.getProperty("DATE_FORMAT");
    }
}

class SyncmodeSelectionPropertiesReader implements  PropertiesReader {
    @Override
    public void read(Properties properties) {
        Title.TITLE = properties.getProperty("TITLE");

        Option.OPTION_OURS = properties.getProperty("OPTION_OURS");
        Option.OPTION_THEIRS = properties.getProperty("OPTION_THEIRS");
        Option.OPTION_OURS_MERGE = properties.getProperty("OPTION_OURS_MERGE");
        Option.OPTION_THEIRS_MERGE = properties.getProperty("OPTION_THEIRS_MERGE");
        Option.OPTION_FUSION = properties.getProperty("OPTION_FUSION");
        Option.OPTION_NO_FUSION = properties.getProperty("OPTION_NO_FUSION");

        Button.BUTTON_SYNC = properties.getProperty("BUTTON_SYNC");
    }
}

class ProjectConflictsPropertiesReader implements  PropertiesReader{
    @Override
    public void read(Properties properties){
        SAVE_USER_VERSION_ONLY = properties.getProperty("SAVE_USER_VERSION_ONLY");
        SAVE_USER_VERSION = properties.getProperty("SAVE_USER_VERSION");
        SAVE_SERVER_VERSION_ONLY = properties.getProperty("SAVE_SERVER_VERSION_ONLY");
        SAVE_SERVER_VERSION = properties.getProperty("SAVE_SERVER_VERSION");
        PUSH = properties.getProperty("PUSH");
    }
}
