package database;

import java.sql.*;
import java.util.logging.Logger;

import models.databaseModels.Permissions;
import models.databaseModels.Project;
import models.databaseModels.User;
import utils.Log;

/**
 * Implementation of class regrouping common methods used by every DAO.
 */
public class DAOUtilities {
    private static final Logger logger = Log.getLogger(DAOUtilities.class);

    public DAOUtilities() {

    }

    /**
     * Creates and returns a PreparedStatement wich will be send to the database with a given connection and SQL query.
     * @param connection The connection to the database
     * @param sql The SQL query
     * @param returnGeneratedKeys A boolean to return (if true) auto generated keys by the SQL query (like the auto generated id when creating a user)
     * @param objets Parameters of the SQL query
     * @return
     * @throws SQLException
     */
    public static PreparedStatement initializationPreparedRequest( Connection connection, String sql, boolean returnGeneratedKeys, Object... objets ) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement( sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS );
        for ( int i = 0; i < objets.length; i++ ) {
            preparedStatement.setObject( i + 1, objets[i] );
        }
        return preparedStatement;
    }

    /**
     * Closes the given ResultSet.
     * @param resultSet The ResultSet to close
     */
    public static void silentClosing( ResultSet resultSet ) {
        if ( resultSet != null ) {
            try {
                resultSet.close();
            } catch ( SQLException e ) {
                logger.warning( "Failed to close the ResultSet : " + e.toString() );
            }
        }
    }

    /**
     * Closes the given Statement.
     * @param statement The Statement to close
     */
    public static void silentClosing( Statement statement ) {
        if ( statement != null ) {
            try {
                statement.close();
            } catch ( SQLException e ) {
                logger.warning( "Failed to close the Statement : " + e.toString() );
            }
        }
    }

    /**
     * Closes the given Connection.
     * @param connection The Connection to close
     */
    public static void silentClosing( Connection connection ) {
        if ( connection != null ) {
            try {
                connection.close();
            } catch ( SQLException e ) {
                logger.warning( "Failed to close the connection : " + e.toString() );
            }
        }
    }

    /**
     * Closes a given Statement and Connection.
     * @param statement the Statement to close
     * @param connection the Connection to close
     */
    public static void silentClosures(Statement statement, Connection connection ) {
        silentClosing( statement );
        silentClosing( connection );
    }

    /**
     * Closes a give ResultSet, Statement and Connection.
     * @param resultSet the ResultSet to close
     * @param statement the Statement to close
     * @param connection the Connection to close
     */
    public static void silentClosures( ResultSet resultSet, Statement statement, Connection connection ) {
        silentClosing( resultSet );
        silentClosing( statement );
        silentClosing( connection );
    }

    public static ResultSet executeQuery(DAOFactory daoFactory, Connection connection, PreparedStatement preparedStatement, ResultSet resultSet, String sqlQuery, Object... objects) {
        try {
            connection = daoFactory.getConnection();
            preparedStatement = initializationPreparedRequest(connection, sqlQuery, false, objects);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            logger.severe(e.toString());
        }
        return resultSet;
    }

    public static int executeUpdate(DAOFactory daoFactory, String sqlQuery, Object... objects) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int status = 0;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = initializationPreparedRequest(connection, sqlQuery, false, objects);
            status = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.severe(e.toString());
        } finally {
            silentClosures(preparedStatement, connection);
        }
        return status;
    }

    public static User mapUser(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String first_name = resultSet.getString("first_name");
        String last_name = resultSet.getString("last_name");
        String username = resultSet.getString("username");
        String email = resultSet.getString("email");
        return new User(id, username, first_name, last_name, email);
    }

    public static Project mapProject(ResultSet resultSet) throws SQLException {
        String uid = resultSet.getString("uid");
        int user_id = resultSet.getInt("user_id");
        String path = resultSet.getString("path");
        String last_modification = resultSet.getString("last_modification");
        boolean default_perm_write = resultSet.getInt("default_perm_write") == 1;
        boolean default_perm_read = resultSet.getInt("default_perm_read") == 1;
        String name = resultSet.getString("name");
        return new Project(uid, user_id, path, last_modification, default_perm_write, default_perm_read, name);
    }

    public static Permissions mapPermissions(ResultSet resultSet) throws SQLException{
        int userID = resultSet.getInt("user_id");
        String projectUID = resultSet.getString("project_uid");
        boolean readable = resultSet.getInt("read_perm") == 1;
        boolean writeable = resultSet.getInt("write_perm") == 1;
        return new Permissions(projectUID, userID, writeable, readable);
    }
}
