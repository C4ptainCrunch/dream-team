package database;

import models.users.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mrmmtb on 27.04.16.
 */
public class UsersDAOTest {

    private DAOFactory daoFactory;
    private UsersDAO usersDAO;
    private User testUser;

    @Before
    public void setup() throws Exception {
        this.daoFactory = DAOFactory.getInstance();
        this.usersDAO = this.daoFactory.getUsersDAO();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCreateUser() {
        this.testUser = new User(0,"testUser","Test","User","testUser@gmail.com");
        this.usersDAO.create(testUser);
    }

    @Test
    public void testRetrieveUsers() {
        User resultUser = this.usersDAO.findByUsername("testUser");
        assertNotNull(resultUser);
        assertEquals(this.testUser.getUsername(),resultUser.getUsername());
    }
}
