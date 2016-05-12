package database;

import constants.Database;
import models.databaseModels.Project;
import models.databaseModels.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.ServerTest;

import java.io.File;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class ProjectsDAOTest extends ServerTest {

    private final String project_uid = UUID.randomUUID().toString();
    private final int user_id = 1;
    private final String path = "test";
    private final String last_modification = "test_DELETE";
    private final boolean write_default = false;
    private final boolean read_default = true;
    private final String name = "project";
    private final String username = "c4";

    private ProjectsDAO dao;
    private DAOFactorySingleton daoFactory;
    private Project project;
    private UsersDAO usersDAO;
    private User testUser;

    @Before
    public void setUp() throws Exception {
        CreateDatabase.createDatabaseIfDoesntExists();
        this.daoFactory = DAOFactorySingleton.getInstance();
        project = new Project(project_uid, user_id, path, last_modification, write_default, read_default, name, username);
        this.dao = daoFactory.getProjectDAO();
        this.usersDAO = this.daoFactory.getUsersDAO();
        this.testUser = new User(user_id,username,"Test","User","testUser@gmail.com");
        this.usersDAO.create(this.testUser);
        this.dao.create(project);
    }

    @After
    public void tearDown() throws Exception {
        this.dao.deleteProject(project_uid);
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
    public void testFindByUid() throws Exception {
        Project found_project = this.dao.findByUid(project_uid);
        assertEquals(found_project.getName(), project.getName());
        assertEquals(found_project.isRead_default(), project.isRead_default());
        assertEquals(found_project.getPath(), project.getPath());

    }

    @Test
    public void testIsReadableByDefault() throws Exception {
        boolean readable_by_default = this.dao.isReadableByDefault(project_uid);
        assertEquals(read_default, readable_by_default);
    }

    @Test
    public void testIsWritableByDefault() throws Exception {
        boolean writable_by_default = this.dao.isWritableByDefault(project_uid);
        assertEquals(write_default, writable_by_default);
    }

    @Test
    public void testGetAllProjects() throws Exception {
        List<Project> projects = this.dao.getAllProjects();
        assertEquals(projects.size(), 1);
    }

    @Test
    public void testGetAllReadableProject() throws Exception {
        List<Project> projects = this.dao.getAllReadableProject(user_id);
        assertEquals(projects.size(), 1);
    }
}