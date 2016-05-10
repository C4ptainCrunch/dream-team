package constants;

/**
 * Created by mrmmtb on 21.04.16.
 */
public final class Database {
    public static final String DB_HOME_TEST_DIR = "server";
    public static final String DB_DIR = "server/database";
    public static final String DB_FILE = DB_DIR + "/CreaTikZ.db";
    public static final String SQLITE_JDBC = "org.sqlite.JDBC";
    public static final String SQLITE_DB_CONNECTION = "jdbc:sqlite:"+ DB_FILE;
}
