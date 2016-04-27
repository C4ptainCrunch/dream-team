package database;

import models.users.User;

/**
 * Created by mrmmtb on 25.04.16.
 */
public interface UsersDAO {
    boolean create(User user);

    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);

    void setPasswordToUser(User user, String password);

    boolean isActivated(User user);

    void activateUser(User user);

    String getTokenOfUser(String username);

    void deleteUser(User user);
}
