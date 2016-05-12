package database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.logging.Logger;

import utils.Log;
import constants.Database;

class DatabaseCreationRequests {
    public static final String SQLITE_DB_ACTIVATE_PRAGMAS = "PRAGMA foreign_keys = ON;";
    public static final String SQLITE_CREATE_TABLE_USERS = "CREATE TABLE Users(" + "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + "first_name VARCHAR(16)," + "last_name VARCHAR(32)," + "username VARCHAR(16) NOT NULL UNIQUE,"
            + "email VARCHAR(32) NOT NULL UNIQUE," + "token VARCHAR(32) NOT NULL," + "activated INTEGER(1) NOT NULL," + "password TEXT);";
    public static final String SQLITE_CREATE_TABLE_PROJECTS = "CREATE TABLE Projects(" + "uid TEXT NOT NULL PRIMARY KEY,"
            + "user_id INTEGER NOT NULL," + "path VARCHAR(1023) NOT NULL UNIQUE," + "last_modification TEXT NOT NULL,"
            + "default_perm_write INTEGER NOT NULL DEFAULT false," + "default_perm_read INTEGER NOT NULL DEFAULT false,"
            + "name VARCHAR (255) NOT NULL, " + "FOREIGN KEY(user_id) REFERENCES Users(id) ON UPDATE CASCADE ON DELETE CASCADE);";
    public static final String SQLITE_CREATE_TABLE_PERMISSIONS = "CREATE TABLE Permissions(" + "project_uid TEXT NOT NULL,"
            + "user_id INTEGER NOT NULL," + "write_perm INTEGER NOT NULL," + "read_perm INTEGER NOT NULL,"
            + "FOREIGN KEY(project_uid) REFERENCES Projects(uid) ON UPDATE CASCADE ON DELETE CASCADE,"
            + "FOREIGN KEY(user_id) REFERENCES Users(id) ON UPDATE CASCADE ON DELETE CASCADE," + "PRIMARY KEY (project_uid, user_id));";
}

/**
 * Class to create the SQLite database
 */
public class CreateDatabase {
    private static final Logger logger = Log.getLogger(CreateDatabase.class);

    CreateDatabase() {

    }

    private static boolean databaseExists() {
        File db_file = new File(Database.DB_FILE);
        if (db_file.exists()) {
            return true;
        }
        createDatabaseDir(db_file);
        return false;
    }

    private static void createDatabaseDir(File db_file) {
        db_file.getParentFile().mkdirs();
    }

    private static void createDatabase() {
        Connection connection;
        Statement statement;
        try {
            Class.forName(Database.SQLITE_JDBC);
            connection = DriverManager.getConnection(Database.SQLITE_DB_CONNECTION);
            statement = connection.createStatement();

            String sqlActivatePragmas = DatabaseCreationRequests.SQLITE_DB_ACTIVATE_PRAGMAS;
            String sqlCreateUsers = DatabaseCreationRequests.SQLITE_CREATE_TABLE_USERS;
            String sqlCreateProjects = DatabaseCreationRequests.SQLITE_CREATE_TABLE_PROJECTS;
            String sqlCreatePermissions = DatabaseCreationRequests.SQLITE_CREATE_TABLE_PERMISSIONS;
            statement.executeUpdate(sqlActivatePragmas);
            statement.executeUpdate(sqlCreateUsers);
            statement.executeUpdate(sqlCreateProjects);
            statement.executeUpdate(sqlCreatePermissions);

            statement.close();
            connection.close();
        } catch (Exception e) {
            logger.severe(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Creates the database if it doesn't exists
     */
    public static void createDatabaseIfDoesntExists() {
        if (!databaseExists()) {
            createDatabase();
        }
    }
}
