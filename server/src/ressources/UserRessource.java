package ressources;

import models.users.User;

import javax.ws.rs.*;
import java.util.Arrays;

@Path("user")
public class UserRessource {

    @GET
    @Path("{user}")
    @Produces("application/xml")
    public User getUser(@PathParam("user") String username){
        return new User(42, username, Arrays.asList("My document", "Other", "Last document"));
    }

    @POST
    @Path("/activate/{user}")
    @Produces("text/plain")
    public String validateToken(@PathParam("user") String username, @FormParam("token") String token){
        if(true){
            return "OK";
        } else {
            return "NOK";
        }
    }
}
