package database;

import constants.Database;
import utils.Log;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;


class FactoryRequests {
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
            "default_perm_read INTEGER NOT NULL DEFAULT false," +
            "FOREIGN KEY(user_id) REFERENCES Users(id) ON UPDATE CASCADE ON DELETE CASCADE);";
    public static final String SQLITE_CREATE_TABLE_PERMISSIONS = "CREATE TABLE Permissions("+
            "project_id INTEGER NOT NULL," +
            "user_id INTEGER NOT NULL," +
            "write_perm INTEGER NOT NULL," +
            "read_perm INTEGER NOT NULL," +
            "FOREIGN KEY(project_id) REFERENCES Projects(id) ON UPDATE CASCADE ON DELETE CASCADE," +
            "FOREIGN KEY(user_id) REFERENCES Users(id) ON UPDATE CASCADE ON DELETE CASCADE," +
            "PRIMARY KEY (project_id, user_id));";
}

/**
 * Implementation of a Data Access Objects (DAO) Factory, which will be used to let the server communicate with the database
 */
public class DAOFactory {

    private static final Logger logger = Log.getLogger(DAOFactory.class);
    private String sqlite_db_connection;

    /**
     * Construct a new DAOFactory with the database's path.
     * @param sqlite_db_connection the database path
     */

    public DAOFactory(String sqlite_db_connection) {
        this.sqlite_db_connection = sqlite_db_connection;
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

            String sqlActivatePragmas = FactoryRequests.SQLITE_DB_ACTIVATE_PRAGMAS;
            String sqlCreateUsers = FactoryRequests.SQLITE_CREATE_TABLE_USERS;
            String sqlCreateProjects = FactoryRequests.SQLITE_CREATE_TABLE_PROJECTS;
            String sqlCreatePermissions = FactoryRequests.SQLITE_CREATE_TABLE_PERMISSIONS;
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
     * Create and return an instance of the DAOFactory (and create the SQLite database if doesn't exist)
     * @return An instance of the DAOFactory
     */
    public static DAOFactory getInstance() {
        if (!databaseExists()) {
            createDatabase();
        }
        DAOFactory instance = new DAOFactory(Database.SQLITE_DB_CONNECTION);
        return instance;
    }

    /**
     * Create and returns a connection to the database
     * @return A connection to the database
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.sqlite_db_connection);
    }

    /**
     * Return a DataAccessObject for the database's Users table
     * @return A DAO for the database's Users table
     */
    public UsersDAO getUsersDAO() {
        return new UsersDAO(this);
    }
}
