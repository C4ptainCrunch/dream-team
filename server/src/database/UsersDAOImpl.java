package database;

import models.users.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static database.DAOUtilities.*;

/**
 * Created by mrmmtb on 25.04.16.
 */
public class UsersDAOImpl implements  UsersDAO {

    private DAOFactory daoFactory;
    private static final String SQL_SELECT_BY_USERNAME = "SELECT id, first_name, last_name, username, email FROM Users WHERE username = ?";
    private static final String SQL_MATCH_USERNAME_PASSWORD = "SELECT username, password FROM Users WHERE username = ? and password = ?";

    UsersDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public User findByUsername(String username) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = initializationPreparedRequest( connection, SQL_SELECT_BY_USERNAME, false, username );
            resultSet = preparedStatement.executeQuery();
            if ( resultSet.next() ) {
                user = map( resultSet );
            }
        } catch ( SQLException e ) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            silentClosures( resultSet, preparedStatement, connection );
        }

        return user;
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = initializationPreparedRequest(connection, SQL_MATCH_USERNAME_PASSWORD, false, username, password);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = findByUsername(username);
            }
        }catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }finally {
            silentClosures( resultSet, preparedStatement, connection );
        }
        return user;
    }


    private static User map(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String first_name = resultSet.getString("first_name");
        String last_name = resultSet.getString("last_name");
        String username = resultSet.getString("username");
        String email = resultSet.getString("email");
        return new User(id, first_name, last_name, username, email);
    }
}
