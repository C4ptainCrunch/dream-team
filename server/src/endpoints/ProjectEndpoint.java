package endpoints;

import database.DAOFactorySingleton;
import database.PermissionsDAO;
import database.ProjectsDAO;
import database.UsersDAO;
import middleware.Secured;
import models.databaseModels.Permissions;
import models.databaseModels.Project;
import models.databaseModels.User;
import utils.Log;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Path("project")
public class ProjectEndpoint {
    ProjectsDAO projectsDAO = DAOFactorySingleton.getInstance().getProjectDAO();
    UsersDAO usersDAO = DAOFactorySingleton.getInstance().getUsersDAO();
    PermissionsDAO permissionsDAO = DAOFactorySingleton.getInstance().getPermissionsDAO();
    private final static Logger logger = Log.getLogger(ProjectEndpoint.class);

    @GET
    @Secured
    @Path("/list")
    @Produces(MediaType.APPLICATION_XML)
    public GenericEntity<List<Project>> getProjectList(@Context SecurityContext securityContext) throws SQLException {
        String username = securityContext.getUserPrincipal().getName();
        User user = usersDAO.findByUsername(username);

        List<Project> projects = new ArrayList<>();
        for (Project project: projectsDAO.getAllReadableProject(user.getId())) {
            if (hasReadPerm(project, user)){
                project.setCurrentUserReadPerm(true);
                project.setCurrentUserWritePerm(hasWritePerm(project, user));
                projects.add(project);
            }
        }
        return new GenericEntity<List<Project>>(projects) {};
    }

    @PUT
    @Secured
    @Path("/upload")
    @Consumes("application/octet-stream")
    public Response upload(InputStream project,
                           @Context SecurityContext securityContext) throws Exception {
        String username = securityContext.getUserPrincipal().getName();
        User user = usersDAO.findByUsername(username);

        java.nio.file.Path tmpFile = File.createTempFile("project-upload", ".crea").toPath();
        Files.copy(project, tmpFile, StandardCopyOption.REPLACE_EXISTING);

        models.project.Project p = new models.project.Project(tmpFile);
        if (projectsDAO.findByUid(p.getUid()) != null){
            throw new BadRequestException("Project already exist");
        }

        java.nio.file.Path storageDir = Paths.get("server/projects/");
        if(!storageDir.toFile().exists()){
            Files.createDirectories(storageDir);
        }
        p.move(storageDir.resolve(p.getUid() + ".crea").toFile());

        Project dbProject = new Project(p);
        dbProject.setUserID(user.getId());

        p.setUserName(user.getUsername());

        projectsDAO.create(dbProject);
        return Response.ok().build();
    }

    @PUT
    @Secured
    @Path("/update")
    @Consumes("application/octet-stream")
    public Response update(InputStream project,
                           @Context SecurityContext securityContext) throws IOException, SQLException {
        String username = securityContext.getUserPrincipal().getName();
        User user = usersDAO.findByUsername(username);

        java.nio.file.Path tmpFile = File.createTempFile("project-upload", ".crea").toPath();

        models.project.Project p = new models.project.Project(tmpFile);
        if (projectsDAO.findByUid(p.getUid()) != null){
            throw new BadRequestException("Project does not exist");
        }

        Project dbProject = new Project(p);
        if(!hasWritePerm(dbProject, user)){
            throw new NotAuthorizedException("You can't edit this project");
        }

        return Response.ok().build();
    }

    @GET
    @Secured
    @Path("/{projectUid}")
    @Produces({"application/octet-stream"})
    public Response get(@PathParam("projectUid") String projectUid,
                            @Context SecurityContext securityContext) throws SQLException, IOException {
        String username = securityContext.getUserPrincipal().getName();
        User user = this.usersDAO.findByUsername(username);

        Project dbProject = this.projectsDAO.findByUid(projectUid);
        if(dbProject == null){
            throw new NotFoundException("Project not found");
        }

        if(!hasReadPerm(dbProject, user)){
            throw new NotAuthorizedException("You can't read this project");
        }
        return Response.ok(Files.readAllBytes(Paths.get(dbProject.getPath())), "application/octet-stream").build();

    }

    private boolean hasWritePerm(Project project, User user) throws SQLException {
        if(user.getId() == project.getUserID()){
            return true;
        }
        Permissions writePermissions = permissionsDAO.findPermissions(project.getUid(), user.getId());
        if (writePermissions != null){
            return writePermissions.isWritable();
        }
        return project.isWrite_default();
    }

    private boolean hasReadPerm(Project project, User user) throws SQLException {
        if(user.getId() == project.getUserID()){
            return true;
        }
        Permissions readPermissions = permissionsDAO.findPermissions(project.getUid(), user.getId());
        if (readPermissions != null){
            return readPermissions.isWritable();
        }
        return project.isRead_default();
    }
}
