package database;

import java.sql.*;

/**
 * Created by mrmmtb on 25.04.16.
 */
public class DAOUtilities {

    public DAOUtilities() {

    }

    public static PreparedStatement initializationPreparedRequest( Connection connection, String sql, boolean returnGeneratedKeys, Object... objets ) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement( sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS );
        for ( int i = 0; i < objets.length; i++ ) {
            preparedStatement.setObject( i + 1, objets[i] );
        }
        return preparedStatement;
    }

    public static void silentClosing( ResultSet resultSet ) {
        if ( resultSet != null ) {
            try {
                resultSet.close();
            } catch ( SQLException e ) {
                System.out.println( "Failed to close the ResultSet : " + e.getMessage() );
            }
        }
    }

    public static void silentClosing( Statement statement ) {
        if ( statement != null ) {
            try {
                statement.close();
            } catch ( SQLException e ) {
                System.out.println( "Failed to close the Statement : " + e.getMessage() );
            }
        }
    }

    public static void silentClosing( Connection connection ) {
        if ( connection != null ) {
            try {
                connection.close();
            } catch ( SQLException e ) {
                System.out.println( "Failed to close the connection : " + e.getMessage() );
            }
        }
    }

    public static void silentClosures(Statement statement, Connection connection ) {
        silentClosing( statement );
        silentClosing( connection );
    }

    public static void silentClosures( ResultSet resultSet, Statement statement, Connection connection ) {
        silentClosing( resultSet );
        silentClosing( statement );
        silentClosing( connection );
    }
}
