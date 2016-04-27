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
            "username VARCHAR(16) NOT NULL UNIQUE," +
            "email VARCAR(32) NOT NULL UNIQUE," +
            "password TEXT);";
    public static final String SQL_SELECT_BY_USERNAME = "SELECT id, first_name, last_name, username, email " +
                                                         "FROM Users WHERE username = ?";
    public static final String SQL_MATCH_USERNAME_PASSWORD = "SELECT username, password " +
                                                              "FROM Users WHERE username = ? and password = ?";
    public static final String SQL_INSERT = "INSERT INTO Users(first_name, last_name, username, email) " +
                                             "VALUES (?, ?, ?, ?)";
    public static final String SQL_SET_PASSWORD_TO_USER = "UPDATE Users SET password = ? WHERE username = ?";
    public static final String SQL_GET_TOKEN_BY_USERNAME = "SELECT token FROM Users WHERE username = ?";
}
