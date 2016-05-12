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

    @GET
    @Secured
    @Path("/info/{projectUid}")
    @Produces({"application/xml"})
    public Project getInfo(@PathParam("projectUid") String projectUid,
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

        return dbProject;
    }

    @POST
    @Secured
    @Path("/set_permissions/{projectUid}")
    public Response setPermissions(@PathParam("projectUid") String projectUid,
                                   @FormParam("readPerm") boolean readPermission,
                                   @FormParam("writePerm") boolean writePermission,
                                   @Context SecurityContext securityContext) throws SQLException {
        String username = securityContext.getUserPrincipal().getName();
        User user = this.usersDAO.findByUsername(username);

        Project dbProject = this.projectsDAO.findByUid(projectUid);

        if(dbProject == null){
            throw new NotFoundException("Project not found");
        }
        if(!(user.getId() == dbProject.getUserID())){
            throw new NotAuthorizedException("You can't edit this project information");
        }

        dbProject.setWrite_default(writePermission);
        dbProject.setRead_default(readPermission);

        this.projectsDAO.update(dbProject);

        return Response.ok().build();
    }

    @POST
    @Secured
    @Path("/get_permission_for_user/{projectUid}")
    @Produces("application/xml")
    public Permissions getPermissionForUser(@PathParam("projectUid") String projectUid,
                                            @FormParam("userName") String username,
                                            @Context SecurityContext securityContext) throws SQLException {

        String editorUsername = securityContext.getUserPrincipal().getName();
        User userEditor = this.usersDAO.findByUsername(editorUsername);

        User user = this.usersDAO.findByUsername(username);

        Project dbProject = this.projectsDAO.findByUid(projectUid);
        if(dbProject == null){
            throw new NotFoundException("Project not found");
        }

        if(user == null){
            throw  new NotFoundException("this user doesn't exist");
        }

        if(!hasReadPerm(dbProject, userEditor)){
            throw new NotAuthorizedException("You can't see this project permission");
        }



        Permissions perm = permissionsDAO.findPermissions(dbProject.getUid(), user.getId());
        if(perm == null){
            throw new  NotFoundException("There aren't any permission for this user on this project");
        }
        return perm;
    }

    @POST
    @Secured
    @Path("/set_permission_for_user/{projectUid}")
    public Response setPermissionForUser(@PathParam("projectUid") String projectUid,
                                         @FormParam("userName") String username,
                                         @FormParam("readPerm") boolean readPermission,
                                         @FormParam("writePerm") boolean writePermission,
                                         @Context SecurityContext securityContext) throws Exception {
        String editorUsername = securityContext.getUserPrincipal().getName();
        User userEditor = this.usersDAO.findByUsername(editorUsername);

        User user = this.usersDAO.findByUsername(username);

        Project dbProject = this.projectsDAO.findByUid(projectUid);

        if(dbProject == null){
            throw new NotFoundException("Project not found");
        }
        if(user == null){
            throw  new NotFoundException("User not found");
        }
        if(!(userEditor.getId() == dbProject.getUserID())){
            throw new NotAuthorizedException("You can't edit this project permission");
        }
        Permissions perm = new Permissions(projectUid, user.getId(), writePermission, readPermission, "");

        Permissions servPerm = permissionsDAO.findPermissions(projectUid, user.getId());

        if(servPerm != null){
            permissionsDAO.changePermissions(user.getId(), projectUid, readPermission, writePermission);
        } else {
            permissionsDAO.create(perm);
        }
        return Response.ok().build();
    }

    @GET
    @Secured
    @Path("/list_permissions/{projectUid}")
    public GenericEntity<List<Permissions>> listPermissions(@PathParam("projectUid") String projectUid,
                                                            @Context SecurityContext securityContext) throws SQLException {
        String username = securityContext.getUserPrincipal().getName();
        User user = this.usersDAO.findByUsername(username);

        Project dbProject = this.projectsDAO.findByUid(projectUid);

        if(dbProject == null){
            throw new NotFoundException("Project not found");
        }
        if(!hasReadPerm(dbProject, user)){
            throw new NotAuthorizedException("You can't read this project");
        }

        List<Permissions> permissions = permissionsDAO.findAllPermissions(projectUid);
        return new  GenericEntity<List<Permissions>> (permissions) {};
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
