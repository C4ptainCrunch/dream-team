package ressources;

import org.glassfish.jersey.media.multipart.FormDataParam;
import utils.Log;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

@Path("project")
public class ProjectRessource {
    private final static Logger logger = Log.getLogger(ProjectRessource.class);

    @GET
    @Path("/get/{projectId}")
    @Produces("application/octet-stream")
    public StreamingOutput getProject(@PathParam("projectId") int projectId){
        // TODO do this
        java.nio.file.Path zipPath = Paths.get("");
        return output -> output.write(Files.readAllBytes(zipPath));
    }

    @POST
    @Path("/set/{projectId}")
    @Produces("text/plain")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public String setProject(@PathParam("projectId") int projectId, @FormDataParam("project")InputStream project) throws IOException {
        // TODO do this
        java.nio.file.Path projectPath = Paths.get("/tmp/plap.zip");
        Files.copy(project, projectPath);
        return "OK";
    }
}
