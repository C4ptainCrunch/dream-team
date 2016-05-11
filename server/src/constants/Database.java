package constants;

public final class Database {
    public static final String DB_HOME_TEST_DIR = "server";
    public static final String DB_DIR = "server/database";
    public static final String DB_FILE = DB_DIR + "/CreaTikZ.db";
    public static final String SQLITE_JDBC = "org.sqlite.JDBC";
    public static final String SQLITE_DB_CONNECTION = "jdbc:sqlite:"+ DB_FILE;
    public static final String SQLITE_CREATE_TABLE_USERS = "CREATE TABLE Users(" +
            "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "first_name VARCHAR(16)," +
            "last_name VARCHAR(32)," +
            "username VARCHAR(16) NOT NULL UNIQUE," +
            "email VARCHAR(32) NOT NULL UNIQUE," +
            "token VARCHAR(32) NOT NULL," +
            "activated INTEGER(1) NOT NULL,"+
            "password TEXT);";
    public static final String SQLITE_CREATE_TABLE_PROJECTS = "CREATE TABLE Projects("+
            "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "user_id INTEGER NOT NULL REFERENCES Users(id)," +
            "path VARCHAR(1023) NOT NULL," +
            "last_modification TEXT NOT NULL," +
            "default_perm_write BOOLEAN NOT NULL DEFAULT false," +
            "default_perm_read BOOLEAN NOT NULL DEFAULT false);";
    public static final String SQL_SELECT_BY_USERNAME = "SELECT id, first_name, last_name, username, email " +
                                                         "FROM Users WHERE username = ?";
    public static final String SQL_MATCH_USERNAME_PASSWORD = "SELECT username, password " +
                                                              "FROM Users WHERE username = ? and password = ?";
    public static final String SQL_INSERT_USER = "INSERT INTO Users(first_name, last_name, username, email, token, activated) " +
                                             "VALUES (?, ?, ?, ?, ?, 0)";
    public static final String SQL_EDIT_USER = "UPDATE Users " +
                                               "SET first_name = ?,last_name = ?,username= ?,email= ?" +
                                               "WHERE username= ?";
    public static final String SQL_SET_PASSWORD_TO_USER = "UPDATE Users SET password = ? WHERE username = ?";
    public static final String SQL_GET_TOKEN_BY_USERNAME = "SELECT token FROM Users WHERE username = ?";
    public static final String SQL_IS_ACTIVATED = "SELECT activated FROM Users WHERE username = ?";
    public static final String SQL_ACTIVATE_USER = "UPDATE Users SET activated = 1 WHERE username = ?";
    public static final String SQL_DISABLE_USER = "UPDATE Users SET activated = 0, token = ? WHERE username=?";
    public static final String SQL_DELETE_USER = "DELETE FROM Users WHERE username = ?";
}
