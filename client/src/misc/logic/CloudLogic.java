package misc.logic;

import misc.utils.RequestBuilder;
import models.project.Project;
import utils.Dirs;
import utils.Log;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.logging.Logger;

public class CloudLogic {
    private static Logger logger = Log.getLogger(CloudLogic.class);

    private CloudLogic() {
    }

    public static void cloudifyProject(final Project project) throws IOException {
        InputStream stream = new FileInputStream(project.getPath().toFile());

        Response r = RequestBuilder.put("/project/upload", stream, MediaType.APPLICATION_OCTET_STREAM).invoke();

        if(r.getStatus() == 400) {
            logger.info("The project is already existing in the cloud");
        } else if (r.getStatus() == 200){
            Files.delete(project.getPath());
        } else {
            logger.info("Unknown error while uploading: " + Integer.toString(r.getStatus()));
        }
    }

    public static List<models.databaseModels.Project> getSharedProjects() {
        Response r = RequestBuilder.get("/project/list").invoke();
        return r.readEntity(new GenericType<List<models.databaseModels.Project>>(){});
    }

    public static Path getLocalCopy(final String uid) throws IOException {
        Response r = RequestBuilder.get("/project/" + uid).invoke();
        if(r.getStatus() != 200) {
            throw new IOException("Error while downloading project :" + r.getStatus());
        }
        InputStream is = r.readEntity(InputStream.class);
        Path tmp = File.createTempFile("local-copy", ".crea").toPath();
        Files.copy(is, tmp, StandardCopyOption.REPLACE_EXISTING);
        return tmp;
    }

    public static Path getLocalCopy(final models.databaseModels.Project project) throws IOException {
        return getLocalCopy(project.getUid());
    }


    public static Path getLocalOrDistantCopy(final models.databaseModels.Project project) throws IOException {
        Path cloudDir = Dirs.getDataDir().resolve("cloud");
        if (!cloudDir.toFile().exists()) {
            Files.createDirectories(cloudDir);
        }
        Path path = cloudDir.resolve(project.getUid() + ".crea");
        try {
            if (new Project(path).getUid().equals(project.getUid())) {
                logger.info("Project " + project.getUid() + " was not on disk");
                return path;
            }
        } catch (Exception e) {
            logger.info("Project " + project.getUid() + " was not on disk");
        }
        logger.fine("Downloading project " + project.getUid());
        return CloudLogic.getLocalCopy(project);
    }

    public static boolean AskForMerge(final Project p) throws FileNotFoundException {
        InputStream stream = new FileInputStream(p.getPath().toFile());
        Response r = RequestBuilder
                .put("/project/checkConflicts", stream, MediaType.APPLICATION_OCTET_STREAM)
                .invoke();
        String s=  r.readEntity(String.class);
        System.out.println(s);
        return s.equals("1");
    }

    public static boolean Merge(final Project p, final String policy) throws FileNotFoundException {
        InputStream stream = new FileInputStream(p.getPath().toFile());
        Response r = RequestBuilder
                .put("/project/update/" + policy, stream, MediaType.APPLICATION_OCTET_STREAM)
                .invoke();
        return r.getStatus() == 200;
    }
}
