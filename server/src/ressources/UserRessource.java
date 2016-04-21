package ressources;

import models.users.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.Arrays;

@Path("user")
public class UserRessource {

    @GET
    @Path("{user}")
    @Produces("application/xml")
    public User getUser(@PathParam("user") String username){
        return new User(42, username, Arrays.asList("My document", "Other", "Last document"));
    }
}
