package constants;

public final class Database {
    public static String DB_HOME_TEST_DIR = "server";
    public static String DB_DIR = "server/database";
    public static String DB_FILE = DB_DIR + "/CreaTikZ.db";
    public static String SQLITE_JDBC = "org.sqlite.JDBC";
    public static String SQLITE_DB_CONNECTION = "jdbc:sqlite:"+ DB_FILE;
}
