package database;

import constants.Database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

/**
 * Created by mrmmtb on 21.04.16.
 */
public class DAOFactory {

    private String sqlite_db_connection;

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

    public static DAOFactory getInstance() {
        if (!databaseExists()) {
            createDatabase();
        }
        DAOFactory instance = new DAOFactory(Database.SQLITE_DB_CONNECTION);
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.sqlite_db_connection);
    }

    public UsersDAO getUsersDAO() {
        return new UsersDAOImpl(this);
    }
}
