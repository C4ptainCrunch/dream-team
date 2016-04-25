import database.DAOFactory;
import database.UsersDAO;
import models.users.User;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by mrmmtb on 25.04.16.
 */
public class TestDAO {

    private DAOFactory daoFactory;


    public TestDAO() {
        initDAOFactory();
    }

    private void initDAOFactory() {
        this.daoFactory = DAOFactory.getInstance();
    }

    public void test(){
        UsersDAO usersDAO = this.daoFactory.getUsersDAO();
        User u = usersDAO.findByUsernameAndPassword("OK","O");
        System.out.println(u==null);
    }
}

