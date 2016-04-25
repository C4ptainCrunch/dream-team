package constants;

/**
 * Created by mrmmtb on 21.04.16.
 */
public final class Database {

    public static final String DB_FILE = "server/database/CreaTikZ.db";
    public static final String SQLITE_JDBC = "org.sqlite.JDBC";
    public static final String SQLITE_DB_CONNECTION = "jdbc:sqlite:"+ DB_FILE;
    public static final String SQLITE_CREATE_TABLE_USERS = "CREATE TABLE Users(" +
            "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "first_name VARHCAR(16)," +
            "last_name VARCHAR(32)," +
            "username VARCHAR(16) NOT NULL," +
            "email VARCAR(32) NOT NULL," +
            "password TEXT NOT NULL);";
}
