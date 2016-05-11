package constants;

public final class Database {
    public static final String DB_HOME_TEST_DIR = "server";
    public static final String DB_DIR = "server/database";
    public static final String DB_FILE = DB_DIR + "/CreaTikZ.db";
    public static final String SQLITE_JDBC = "org.sqlite.JDBC";
    public static final String SQLITE_DB_CONNECTION = "jdbc:sqlite:"+ DB_FILE;
    public static final String SQLITE_DB_ACTIVATE_PRAGMAS = "PRAGMA foreign_keys = ON;";
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
            "user_id INTEGER NOT NULL," +
            "path VARCHAR(1023) NOT NULL UNIQUE," +
            "last_modification TEXT NOT NULL," +
            "default_perm_write INTEGER NOT NULL DEFAULT false," +
            "default_perm_read INTEGER NOT NULL DEFAULT false)," +
            "FOREIGN KEY(user_id) REFERENCES Users(id) ON UPDATE CASCADE ON DELETE CASCADE;";
    public static final String SQLITE_CREATE_TABLE_PERMISSIONS = "CREATE TABLE Permissions("+
            "project_id INTEGER NOT NULL," +
            "user_id INTEGER NOT NULL," +
            "write_perm INTEGER NOT NULL," +
            "read_perm INTEGER NOT NULL," +
            "FOREIGN KEY(project_id) REFERENCES Projects(id) ON UPDATE CASCADE ON DELETE CASCADE," +
            "FOREIGN KEY(user_id) REFERENCES Users(id) ON UPDATE CASCADE ON DELETE CASCADE," +
            "PRIMARY KEY (project_id, user_id);";
    public static final String SQL_SELECT_BY_USERNAME = "SELECT id, first_name, last_name, username, email " +
                                                         "FROM Users WHERE username = ?";
    public static final String SQL_SELECT_BY_ID = "SELECT id, first_name, last_name, username, email " +
            "FROM Users WHERE id = ?";
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

    public static final String SQL_INSERT_PROJECT = "INSERT INTO Projects(user_id, path, last_modification, default_perm_write, default_perm_read) VALUES (?, ?, ?, ?, ?);";
    public static final String SQL_EDIT_PROJECT = "UPDATE Projects SET path = ?, last_modification = ?, default_perm_write = ?, default_perm_read = ? WHERE path = ?";
    public static final String SQL_SELECT_PROJECT_BY_ID = "SELECT id, user_id, path, last_modification, default_perm_write, default_perm_read FROM Projects WHERE id = ?";
    public static final String SQL_PROJECT_IS_READABLE = "SELECT default_perm_read FROM Projects WHERE id = ?";
    public static final String SQL_PROJECT_IS_WRITABLE = "SELECT default_perm_write FROM Projects WHERE id = ?";
    public static final String SQL_PROJECT_DELETE = "DELETE FROM Projects WHERE id = ?";

    public static final String SQL_INSERT_PERMISSIONS = "INSERT INTO Permissions(project_id, user_id, write_perm, read_perm) VALUES (?,?,?,?);";
    public static final String SQL_SELECT_PERMISSIONS = "SELECT * FROM Permissions WHERE project_id = ? AND user_id = ? ;";
    public static final String SQL_CHANGE_PERMISSIONS = "UPDATE Permissions SET read_perm = ?, write_perm = ? WHERE project_id = ? AND user_id = ? ;";
    public static final String SQL_PERMISSINOS_DELETE = "DELETE FROM Permissions WHERE project_id = ? AND user_id = ? ;";
}
