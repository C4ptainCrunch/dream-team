package endpoints;

import database.DAOFactory;
import database.ProjectsDAO;
import database.UsersDAO;
import middleware.Secured;
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
import java.util.logging.Logger;

@Path("project")
public class ProjectEndpoint {
    ProjectsDAO projectsDAO = DAOFactory.getInstance().getProjectDAO();
    UsersDAO usersDAO = DAOFactory.getInstance().getUsersDAO();
    private final static Logger logger = Log.getLogger(ProjectEndpoint.class);

    @GET
    @Secured
    @Path("/list")
    public List<Project> getProjectList(@Context SecurityContext securityContext){
        String username = securityContext.getUserPrincipal().getName();
        User user = usersDAO.findByUsername(username);
        return projectsDAO.getAllReadableProject(user.getId());
    }

    @PUT
    @Secured
    @Path("/project/upload")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public Response upload(@FormDataParam("project")InputStream project,
                           @Context SecurityContext securityContext) throws IOException {
        String username = securityContext.getUserPrincipal().getName();
        User user = usersDAO.findByUsername(username);

        java.nio.file.Path tmpFile = File.createTempFile("project-upload", ".crea").toPath();
        Files.copy(project, tmpFile);

        models.project.Project p = new models.project.Project(tmpFile);
        p.move(new File("server/projects/" + p.getName()));
        Project dbProject = new Project(p);
        if (projectsDAO.findByID(dbProject.getId()) != null){
            throw new BadRequestException("Project already exist");
        }
        dbProject.setUserID(user.getId());

        p.setUserName(user.getUsername());

        projectsDAO.create(dbProject);
        return Response.ok().build();
    }
}
