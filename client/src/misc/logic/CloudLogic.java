package misc.logic;

import misc.utils.RequestBuilder;
import models.project.Project;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import utils.Dirs;
import utils.Log;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

public class CloudLogic {
    private static Logger logger = Log.getLogger(CloudLogic.class);

    private CloudLogic() {
    }

    public static void cloudifyProject(Project project) throws IOException {
        InputStream stream = new FileInputStream(project.getPath().toFile());

        Response r = RequestBuilder.put("/project/upload", stream, MediaType.APPLICATION_OCTET_STREAM).invoke();

        if(r.getStatus() == 400) {
            logger.info("The project is already existing in the cloud");
            // TODO alert : project is existing
        } else if (r.getStatus() == 200){
            project.move(Dirs.getDataDir().resolve(Paths.get("cloud/" + project.getName())).toFile());
        } else {
            logger.info("Unknown error while uploading: " + Integer.toString(r.getStatus()));
            // TODO : alert unknown error
        }
    }

    public static List<models.databaseModels.Project> getSharedProjects() {
        Response r = RequestBuilder.get("/project/list").invoke();
        return r.readEntity(new GenericType<List<models.databaseModels.Project>>(){});
    }
}
