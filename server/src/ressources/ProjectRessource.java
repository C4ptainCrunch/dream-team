package ressources;

import utils.Log;

import javax.ws.rs.*;
import javax.ws.rs.core.StreamingOutput;
import java.io.FileOutputStream;
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
        java.nio.file.Path zipPath = Paths.get("");
        return output -> output.write(Files.readAllBytes(zipPath));
    }

    @POST
    @Path("/set/{projectId}")
    @Produces("text/plain")
    @Consumes({})
    public String setProject(@PathParam("projectId") int projectId, InputStream project) throws IOException {
        FileOutputStream output = new FileOutputStream("/tmp/plap.zip");
        output.write(project.read());
        return "OK";
    }
}
