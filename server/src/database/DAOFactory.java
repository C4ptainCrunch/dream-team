package database;

import constants.Database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Implementation of a Data Access Objects (DAO) Factory, which will be used to let the server communicate with the database
 */
public class DAOFactory {

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
            String sqlCreate = Database.SQLITE_CREATE_TABLE_USERS;
            statement.executeUpdate(sqlCreate);
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
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
        return new UsersDAOImpl(this);
    }
}
