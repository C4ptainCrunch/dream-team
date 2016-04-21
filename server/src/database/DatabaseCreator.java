package database;

import constants.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Created by mrmmtb on 21.04.16.
 */
public class DatabaseCreator {

    public DatabaseCreator() {
        initDatabase();
    }

    private void initDatabase() {
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName(Database.SQLITE_JDBC);
            connection = DriverManager.getConnection(Database.SQLITE_DB_CONNECTION);
            statement = connection.createStatement();
            String sqlCreate = Database.SQLITE_CREATE_TABLE_USERS;
            statement.executeUpdate(sqlCreate);
            statement.close();
            connection.close();
        }catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }
}
