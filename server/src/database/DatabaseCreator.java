package database;

import constants.Database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.logging.Logger;

/**
 * Created by mrmmtb on 21.04.16.
 */
public class DatabaseCreator {

    public DatabaseCreator() {
        initDatabase();
    }

    private boolean exists() {
        File db_file = new File(Database.DB_FILE);
        if (db_file.exists()) {
            return true;
        }
        createDatabaseDir(db_file);
        return false;
    }

    private void createDatabaseDir(File db_file) {
        db_file.getParentFile().mkdirs();
    }

    private void initDatabase() {
        Connection connection;
        Statement statement;
        if (!exists()) {
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
    }

}
