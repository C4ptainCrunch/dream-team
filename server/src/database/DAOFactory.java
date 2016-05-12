package database;

import constants.Database;
import utils.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;


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



    /**
     * Create and return an instance of the DAOFactory (and create the SQLite database if doesn't exist)
     * @return An instance of the DAOFactory
     */
    public static DAOFactory getInstance() {
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

    public ProjectsDAO getProjectDAO() {
        return new ProjectsDAO(this);
    }

    public PermissionsDAO getPermissionsDAO() {
        return new PermissionsDAO(this);
    }
}
