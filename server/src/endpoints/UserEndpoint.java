package endpoints;

import java.sql.SQLException;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import middleware.Secured;
import models.databaseModels.User;
import utils.ConfirmationEmailSender;
import utils.Log;
import utils.TokenCreator;
import constants.Network;
import database.DAOFactory;
import database.UsersDAO;

@Path("user")
public class UserEndpoint {
    UsersDAO usersDAO = DAOFactory.getInstance().getUsersDAO();
    private final static Logger logger = Log.getLogger(UserEndpoint.class);

    @GET
    @Secured
    @Path("/get")
    @Produces("application/xml")
    public User getUserData(@Context SecurityContext securityContext) throws SQLException {
        String username = securityContext.getUserPrincipal().getName();
        User user = this.usersDAO.findByUsername(username);
        if(user == null){
            throw new NotFoundException("User not found");
        }
        return user;
    }

    @POST
    @Path("/activate/{username}")
    @Produces("text/plain")
    public String validateToken(@PathParam("username") String username, @FormParam("token") String token) throws SQLException {
        if(this.usersDAO.getTokenOfUser(username).equals(token)){
            this.usersDAO.activateUser(username);
            return Network.Token.TOKEN_OK;
        } else {
            return "NOK";
        }
    }

    @POST
    @Path("/signup/{user}")
    public Response signUp(@FormParam("username") String username,
                           @FormParam("firstname") String firstname,
                           @FormParam("lastname") String lastname,
                           @FormParam("email") String email,
                           @FormParam("password") String password) throws SQLException {
        User user = new User(username, firstname, lastname, email);
        try {
            this.usersDAO.create(user);
            this.usersDAO.setPasswordToUser(user, password);
            try {
                ConfirmationEmailSender.send(email, this.usersDAO.getTokenOfUser(username));
            } catch (MessagingException ex) {
                logger.info("Email sending to " + email + " failed.");
            }
            return Response.ok().build();
        } catch (Exception e){
            logger.warning("Error while signup");
            e.printStackTrace();
            throw new ServerErrorException("Error while signup", 500);
        }
    }

    @POST
    @Secured
    @Path("/edit")
    @Produces("text/plain")
    @Consumes("application/xml")
    public String editUser(
            User user,
            @Context SecurityContext securityContext) throws SQLException {
        String username = securityContext.getUserPrincipal().getName();
        if(!user.getUsername().equals(username)){
            throw new BadRequestException("User username isn't the same as the db username");
        }
        User dbUser = usersDAO.findByUsername(username);
        dbUser.setFirstName(user.getFirstName());
        dbUser.setLastName(user.getLastName());
        boolean should_reset_email = !dbUser.getEmail().equals(user.getEmail());
        dbUser.setEmail(user.getEmail());

        this.usersDAO.update(dbUser);
        if(should_reset_email) {
            String token = TokenCreator.newToken();
            this.usersDAO.setToken(dbUser, token);
            try {
                ConfirmationEmailSender.send(dbUser.getEmail(),token);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        return Network.Token.TOKEN_OK;

    }
}
