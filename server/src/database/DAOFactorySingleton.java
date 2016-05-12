package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import utils.Log;
import constants.Database;

/**
 * Implementation of a Data Access Objects (DAO) Factory, which will be used to
 * let the server communicate with the database
 */
public class DAOFactorySingleton {

    private static final Logger logger = Log.getLogger(DAOFactorySingleton.class);
    private static final DAOFactorySingleton instance = new DAOFactorySingleton(Database.SQLITE_DB_CONNECTION);
    private String sqlite_db_connection;

    /**
     * Construct a new DAOFactorySingleton with the database's path.
     *
     * @param sqlite_db_connection
     *            the database path
     */

    public DAOFactorySingleton(String sqlite_db_connection) {
        this.sqlite_db_connection = sqlite_db_connection;
    }

    /**
     * Create and return an instance of the DAOFactorySingleton (and create the
     * SQLite database if doesn't exist)
     *
     * @return An instance of the DAOFactorySingleton
     */
    public static DAOFactorySingleton getInstance() {
        return instance;
    }

    /**
     * Create and returns a connection to the database
     *
     * @return A connection to the database
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.sqlite_db_connection);
    }

    /**
     * Return a DataAccessObject for the database's Users table
     *
     * @return A DAO for the database's Users table
     */
    public UsersDAO getUsersDAO() {
        return new UsersDAO(this);
    }

    public ProjectsDAO getProjectDAO() {
        return new ProjectsDAO(this);
    }

    public PermissionsDAO getPermissionsDAO() {
        return new PermissionsDAO(this);
    }
}
