package database;

import constants.Database;
import models.databaseModels.Permissions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.ServerTest;

import java.io.File;
import java.util.UUID;

import static org.junit.Assert.*;

public class PermissionsDAOTest extends ServerTest {

    private final int user_id = 1;
    private final String project_id = UUID.randomUUID().toString();
    private final boolean read_perm = true;
    private final boolean write_perm = false;

    private PermissionsDAO dao;
    private DAOFactory daoFactory;

    @Before
    public void setUp() throws Exception {
        CreateDatabase.createDatabaseIfDoesntExists();
        this.daoFactory = DAOFactory.getInstance();
        this.dao = daoFactory.getPermissionsDAO();
        this.dao.create(new Permissions(project_id, user_id, write_perm, read_perm));
    }

    @After
    public void tearDown() throws Exception {
        this.dao.deletePermissions(user_id, project_id);
        /* To delete dirs made by the tests (because db creation depends on the actual path) */
        File db = new File(Database.DB_FILE);
        db.delete();
        File db_dir = new File(Database.DB_DIR);
        db_dir.delete();
        File db_dir_first = new File(Database.DB_HOME_TEST_DIR);
        db_dir_first.delete();
    }

    @Test
    public void testFindPermissions() throws Exception {
        Permissions permission = this.dao.findPermissions(project_id, user_id);
        assertEquals(permission.isReadable(), read_perm);
        assertEquals(permission.isWritable(), write_perm);
    }

    @Test
    public void testChangePermissions() throws Exception {
        boolean new_write_permission = true;
        this.dao.changePermissions(user_id, project_id, read_perm, new_write_permission);
        Permissions permissions = this.dao.findPermissions(project_id, user_id);
        assertEquals(permissions.isWritable(), new_write_permission);
    }
}