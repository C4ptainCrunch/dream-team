package endpoints;

import database.DAOFactory;
import database.PermissionsDAO;
import database.ProjectsDAO;
import database.UsersDAO;
import middleware.Secured;
import models.databaseModels.Permissions;
import models.databaseModels.Project;
import models.databaseModels.User;
import org.glassfish.jersey.media.multipart.FormDataParam;
import utils.Log;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Path("project")
public class ProjectEndpoint {
    ProjectsDAO projectsDAO = DAOFactory.getInstance().getProjectDAO();
    UsersDAO usersDAO = DAOFactory.getInstance().getUsersDAO();
    PermissionsDAO permissionsDAO = DAOFactory.getInstance().getPermissionsDAO();
    private final static Logger logger = Log.getLogger(ProjectEndpoint.class);

    @GET
    @Secured
    @Path("/list")
    @Produces("application/xml")
    public List<Project> getProjectList(@Context SecurityContext securityContext){
        String username = securityContext.getUserPrincipal().getName();
        User user = usersDAO.findByUsername(username);
        return projectsDAO.getAllReadableProject(user.getId());
    }

    @PUT
    @Secured
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upload(InputStream project,
                           @Context SecurityContext securityContext) throws IOException {
        String username = securityContext.getUserPrincipal().getName();
        User user = usersDAO.findByUsername(username);

        java.nio.file.Path tmpFile = File.createTempFile("project-upload", ".crea").toPath();
        Files.copy(project, tmpFile);

        models.project.Project p = new models.project.Project(tmpFile);
        if (projectsDAO.findByUid(p.getUid()) != null){
            throw new BadRequestException("Project already exist");
        }

        p.move(new File("server/projects/" + p.getUid()));
        Project dbProject = new Project(p);
        dbProject.setUserID(user.getId());

        p.setUserName(user.getUsername());

        projectsDAO.create(dbProject);
        return Response.ok().build();
    }

    @PUT
    @Secured
    @Path("/update")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response update(InputStream project,
                           @Context SecurityContext securityContext) throws IOException {
        String username = securityContext.getUserPrincipal().getName();
        User user = usersDAO.findByUsername(username);

        java.nio.file.Path tmpFile = File.createTempFile("project-upload", ".crea").toPath();
        Files.copy(project, tmpFile);

        models.project.Project p = new models.project.Project(tmpFile);
        if (projectsDAO.findByUid(p.getUid()) != null){
            throw new BadRequestException("Project does not exist");
        }

        if(!hasWritePerm(p, user)){
            throw new NotAuthorizedException("You can't edit this project");
        }

        return Response.ok().build();
    }

    private boolean hasWritePerm(Project project, User user){
        Optional<Permissions> personnalWritePermissions = permissionsDAO.findPermissions(project.getUid(), user.getId());
        if (personnalWritePermissions.isPresent()){
            return personnalWritePermissions.get().isWritable();
        }
        return project.readable();
    }
}
