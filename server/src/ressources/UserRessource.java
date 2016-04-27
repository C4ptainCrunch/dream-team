package ressources;

import database.DAOFactory;
import database.UsersDAO;
import models.users.User;

import javax.ws.rs.*;
import java.util.Arrays;

@Path("user")
public class UserRessource {

    DAOFactory daoFactory = DAOFactory.getInstance();
    UsersDAO usersDAO = daoFactory.getUsersDAO();

    @GET
    @Path("{user}")
    @Produces("application/xml")
    public User getUser(@PathParam("user") String username){
        return new User(42, username, Arrays.asList("My document", "Other", "Last document"));
    }

    /*@POST
    @Path("/activate/{user}")
    @Produces("text/plain")
    public String validateToken(@PathParam("user") String username, @FormParam("token") String token){
        if(this.usersDAO.getTokenOfUser(username).equals(token)){
            return "OK";
        } else {
            return "NOK";
        }
    }

    @POST
    @Path("/login/{user}")
    @Produces("text/plain")
    public String login(@FormParam("token") String token){
        return "TEST";
    }

    @POST
    @Path("/create/{user}")
    @Produces("text/plain")
    public void createUser(@FormParam("token") String token){

    }

    @POST
    @Path("/edit/{user}")
    @Produces("text/plain")
    public void editUser(@FormParam("token") String token){

    }*/
}
