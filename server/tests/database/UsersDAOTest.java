package database;

import static org.junit.Assert.*;

import java.io.File;

import models.databaseModels.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import constants.Database;
import utils.ServerTest;

/**
 * Created by mrmmtb on 27.04.16.
 */
public class UsersDAOTest extends ServerTest {

    private DAOFactory daoFactory;
    private UsersDAO usersDAO;
    private User testUser;

    @Before
    public void setup() throws Exception {
        this.daoFactory = DAOFactory.getInstance();
        this.usersDAO = this.daoFactory.getUsersDAO();
        this.testUser = new User(0,"testUser","Test","User","testUser@gmail.com");
        this.usersDAO.create(this.testUser);

    }

    @After
    public void tearDown() throws Exception {
        this.usersDAO.deleteUser(this.testUser);
        /* To delete dirs made by the tests (because db creation depends on the actual path) */
        File db = new File(Database.DB_FILE);
        db.delete();
        File db_dir = new File(Database.DB_DIR);
        db_dir.delete();
        File db_dir_first = new File(Database.DB_HOME_TEST_DIR);
        db_dir_first.delete();
    }


    @Test
    public void testRetrieveUsers() {
        User resultUser = this.usersDAO.findByUsername("testUser");
        assertNotNull(resultUser);
        assertEquals(this.testUser.getUsername(),resultUser.getUsername());
    }

    @Test
    public void testActivateUser() {
        this.usersDAO.activateUser(this.testUser.getUsername());
        assertTrue(this.usersDAO.isActivated(this.testUser));
    }

    @Test
    public void testSetPassword() {
        this.usersDAO.setPasswordToUser(this.testUser, "pw");
        User user = this.usersDAO.findByUsernameAndPassword("testUser", "pw");
        assertNotNull(user);
    }
}
