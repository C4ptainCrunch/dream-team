package constants;

import static constants.Database.*;
import static constants.Email.*;
import static constants.Tokens.TOKEN_ALPHABET;

import java.util.Properties;

public class ServerPropertiesLoader extends PropertiesLoader {
    private static final String DATABASE_PROPERTIES_FILENAME = "database.properties";
    private static final String EMAIL_PROPERTIES_FILENAME = "email.properties";
    private static final String TOKENS_PROPERTIES_FILENAME = "tokens.properties";

    public static void loadAll() {
        PropertiesLoader.loadAll();
        loadServerProperties();
    }

    public static void loadServerProperties() {
        loadDatabaseProperties();
        loadEmailProperties();
        loadTokensProperties();
    }

    public static void loadDatabaseProperties() {
        PropertiesLoader.load(DATABASE_PROPERTIES_FILENAME, new DatabasePropertiesReader());
    }

    public static void loadEmailProperties() {
        PropertiesLoader.load(EMAIL_PROPERTIES_FILENAME, new EmailPropertiesReader());
    }

    public static void loadTokensProperties() {
        PropertiesLoader.load(TOKENS_PROPERTIES_FILENAME, new TokensPropertiesReader());
    }

}

class DatabasePropertiesReader implements PropertiesReader {
    @Override
    public void read(Properties properties) {
        DB_HOME_TEST_DIR = properties.getProperty("DB_HOME_TEST_DIR");
        DB_DIR = properties.getProperty("DB_DIR");
        DB_FILE = DB_DIR + properties.getProperty("DB_FILENAME");
        SQLITE_JDBC = properties.getProperty("SQLITE_JDBC");
        SQLITE_DB_CONNECTION = properties.getProperty("DB_CONNECTION_PRELUDE") + DB_FILE;
    }
}

class EmailPropertiesReader implements PropertiesReader {
    @Override
    public void read(Properties properties) {
        SUBJECT_LINE = properties.getProperty("SUBJECT_LINE");
        EMAIL_BODY_PART_ONE = properties.getProperty("BODY_ONE");
        EMAIL_BODY_PART_TWO = properties.getProperty("BODY_TWO");
    }
}

class TokensPropertiesReader implements PropertiesReader {
    @Override
    public void read(Properties properties) {
        TOKEN_ALPHABET = properties.getProperty("TOKEN_ALPHABET");
    }
}
