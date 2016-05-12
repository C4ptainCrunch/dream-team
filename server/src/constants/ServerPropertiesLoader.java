package constants;

import java.util.Properties;
import static constants.Database.*;
import static constants.Email.*;
import static constants.Tokens.*;
import static constants.ProjectConflicts.*;

/**
 * Created by jhellinckx on 10/05/16.
 */
public class ServerPropertiesLoader extends PropertiesLoader {
    private static final String DATABASE_PROPERTIES_FILENAME = "database.properties";
    private static final String EMAIL_PROPERTIES_FILENAME = "email.properties";
    private static final String TOKENS_PROPERTIES_FILENAME = "tokens.properties";
    private static final String PROJECTSCONFLICTS_PROPERTIES_FILENAME = "projectconflicts.properties";

    public static void loadAll(){
        PropertiesLoader.loadAll();
        loadServerProperties();
    }

    public static void loadServerProperties(){
        loadDatabaseProperties();
        loadEmailProperties();
        loadTokensProperties();
        loadProjectConflictsProperties();
    }

    public static void loadDatabaseProperties(){
        PropertiesLoader.load(DATABASE_PROPERTIES_FILENAME, new DatabasePropertiesReader());
    }

    public static void loadEmailProperties(){
        PropertiesLoader.load(EMAIL_PROPERTIES_FILENAME, new EmailPropertiesReader());
    }

    public static void loadTokensProperties(){
        PropertiesLoader.load(TOKENS_PROPERTIES_FILENAME, new TokensPropertiesReader());
    }

    public static void loadProjectConflictsProperties(){
        PropertiesLoader.load(PROJECTSCONFLICTS_PROPERTIES_FILENAME, new ProjectConflictsPropertiesReader());
    }
}

class DatabasePropertiesReader implements PropertiesReader{
    @Override
    public void read(Properties properties){
        DB_HOME_TEST_DIR = properties.getProperty("DB_HOME_TEST_DIR");
        DB_DIR = properties.getProperty("DB_DIR");
        DB_FILE = DB_DIR + properties.getProperty("DB_FILENAME");
        SQLITE_JDBC = properties.getProperty("SQLITE_JDBC");
        SQLITE_DB_CONNECTION = properties.getProperty("DB_CONNECTION_PRELUDE") + DB_FILE;
    }
}

class EmailPropertiesReader implements PropertiesReader{
    @Override
    public void read(Properties properties){
        SUBJECT_LINE = properties.getProperty("SUBJECT_LINE");
        EMAIL_BODY_PART_ONE = properties.getProperty("BODY_ONE");
        EMAIL_BODY_PART_TWO = properties.getProperty("BODY_TWO");
    }
}

class TokensPropertiesReader implements PropertiesReader{
    @Override
    public void read(Properties properties){
        TOKEN_ALPHABET = properties.getProperty("TOKEN_ALPHABET");
    }
}

class ProjectConflictsPropertiesReader implements  PropertiesReader{
    @Override
    public void read(Properties properties){
        SAVE_USER_VERSION_ONLY = properties.getProperty("SAVE_USER_VERSION_ONLY");
        SAVE_USER_VERSION = properties.getProperty("SAVE_USER_VERSION");
        SAVE_SERVER_VERSION_ONLY = properties.getProperty("SAVE_SERVER_VERSION_ONLY");
        SAVE_SERVER_VERSION = properties.getProperty("SAVE_SERVER_VERSIION");
    }
}
