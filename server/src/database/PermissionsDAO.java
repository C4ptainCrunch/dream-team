package database;

import utils.Log;

import java.util.logging.Logger;

public class PermissionsDAO {
    private static final Logger logger = Log.getLogger(UsersDAO.class);

    private DAOFactory daoFactory;

    PermissionsDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

}
