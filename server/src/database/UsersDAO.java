package database;

import models.users.User;

import java.util.ArrayList;

/**
 * Created by mrmmtb on 25.04.16.
 */
public interface UsersDAO {
    boolean create(User user);

    String edit(ArrayList<String> data, String originalUsername, String originalEmail);

    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);

    void setPasswordToUser(User user, String password);

    boolean isActivated(User user);

    void activateUser(String username);

    String disableUser(String username);

    String getTokenOfUser(String username);

    void deleteUser(User user);
}
