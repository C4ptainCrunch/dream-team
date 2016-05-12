package database;

import models.databaseModels.Permissions;
import utils.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static database.DAOUtilities.initializationPreparedRequest;
import static database.DAOUtilities.mapPermissions;
import static database.DAOUtilities.silentClosures;

class PermissionsRequests {
    public static final String SQL_INSERT_PERMISSIONS = "INSERT INTO Permissions(project_uid, user_id, write_perm, read_perm) VALUES (?,?,?,?);";
    public static final String SQL_SELECT_PERMISSIONS = "SELECT * FROM Permissions WHERE project_uid = ? AND user_id = ? ;";
    public static final String SQL_CHANGE_PERMISSIONS = "UPDATE Permissions SET read_perm = ?, write_perm = ? WHERE project_uid = ? AND user_id = ? ;";
    public static final String SQL_PERMISSINOS_DELETE = "DELETE FROM Permissions WHERE project_uid = ? AND user_id = ? ;";
    public static final String SQL_GET_BY_PROJECT = "SELECT * from Permissions WHERE project_uid = ?;";
}

public class PermissionsDAO {
    private static final Logger logger = Log.getLogger(UsersDAO.class);

    private DAOFactorySingleton daoFactorySingleton;

    PermissionsDAO(DAOFactorySingleton daoFactorySingleton) {
        this.daoFactorySingleton = daoFactorySingleton;
    }

    public void create(Permissions permissions) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet autoGeneratedValues = null;
        connection = daoFactorySingleton.getConnection();
        preparedStatement = initializationPreparedRequest(connection, PermissionsRequests.SQL_INSERT_PERMISSIONS, true, permissions.getProjectUID(), permissions.getUserID(), permissions.isReadable(), permissions.isWritable());
        int status = preparedStatement.executeUpdate();
        if (status == 0) {
            throw new Exception("Failed to create a new permissions instance");
        }
    }

    public Permissions findPermissions(String project_uid, int user_id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Permissions permissions = null;
        try {
            resultSet = DAOUtilities.executeQuery(daoFactorySingleton, connection, preparedStatement, resultSet, PermissionsRequests.SQL_SELECT_PERMISSIONS, project_uid, user_id);
            if (resultSet.next()){
                permissions = DAOUtilities.mapPermissions(resultSet);
            }
        } finally {
            silentClosures(resultSet, preparedStatement, connection);
        }
        return permissions;
    }

    public void changePermissions(int user_id, String project_uid, boolean read_perm, boolean write_perm) throws Exception {
        int status = DAOUtilities.executeUpdate(daoFactorySingleton, PermissionsRequests.SQL_CHANGE_PERMISSIONS, project_uid, user_id);
        if (status == 0) {
            throw  new Exception(String.format("Failed to set permissions for User %d in Project %d", user_id, project_uid));
        }
    }

    public void deletePermissions(int user_id, String project_uid) throws Exception {
        int status = DAOUtilities.executeUpdate(daoFactorySingleton, PermissionsRequests.SQL_PERMISSINOS_DELETE, project_uid, user_id);
        if (status == 0) {
            throw  new  Exception(String.format("Failed to delete permissions for User %d in Project %d", user_id, project_uid));
        }
    }

    public List<Permissions> findAllPermissions(String projectUid) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Permissions> permissions = new ArrayList<>();
        try {
            resultSet = DAOUtilities.executeQuery(daoFactorySingleton, connection, preparedStatement, resultSet, PermissionsRequests.SQL_GET_BY_PROJECT, projectUid);
            while (resultSet.next()) {
                permissions.add(mapPermissions(resultSet));
            }
        } finally {
            silentClosures(resultSet, preparedStatement, connection);
        }
        return permissions;
    }
}
