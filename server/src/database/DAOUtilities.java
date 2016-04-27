package database;

import java.sql.*;

/**
 * Implementation of class regrouping common methods used by every DAO.
 */
public class DAOUtilities {

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
                System.out.println( "Failed to close the ResultSet : " + e.getMessage() );
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
                System.out.println( "Failed to close the Statement : " + e.getMessage() );
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
                System.out.println( "Failed to close the connection : " + e.getMessage() );
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
}
