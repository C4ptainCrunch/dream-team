package ressources;

import constants.Network;
import database.DAOFactory;
import database.UsersDAO;
import models.users.User;
import utils.ConfirmationEmailSender;
import utils.Log;

import javax.mail.MessagingException;
import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.logging.Logger;

@Path("user")
public class UserRessource {

    DAOFactory daoFactory = DAOFactory.getInstance();
    UsersDAO usersDAO = daoFactory.getUsersDAO();
    private final static Logger logger = Log.getLogger(UserRessource.class);

    @GET
    @Path("{user}")
    @Produces("application/xml")
    public String getUser(@PathParam("user") String username){
        return "Test";
    }

    @POST
    @Path("/get/{user}")
    @Produces("text/plain")
    public String getUserData(@PathParam("user") String username){
        User gotUser = this.usersDAO.findByUsername(username);
        if(gotUser != null){
            String toReturn = gotUser.getFirstName() + "/" + gotUser.getLastName() + "/" +
                              gotUser.getUsername() + "/" + gotUser.getEmail();
            return toReturn;
        } else {
            return "NOK";
        }
    }

    @POST
    @Path("/activate/{user}")
    @Produces("text/plain")
    public String validateToken(@PathParam("user") String username, @FormParam("token") String token){
        if(this.usersDAO.getTokenOfUser(username).equals(token)){
            this.usersDAO.activateUser(username);
            return Network.Token.TOKEN_OK;
        } else {
            return "NOK";
        }
    }

    @POST
    @Path("/login/{user}")
    @Produces("text/plain")
    public String login(@FormParam("username") String username, @FormParam("password") String password){
        User testUser = this.usersDAO.findByUsernameAndPassword(username,password);
        if(testUser!=null) {
            if(this.usersDAO.isActivated(testUser)) {
                return Network.Login.LOGIN_OK;
            }
            return Network.Login.ACCOUNT_NOT_ACTIVATED;
        }
        return Network.Login.LOGIN_FAILED;
    }

    @POST
    @Path("/signup/{user}")
    @Produces("text/plain")
    public String signUp(@FormParam("username") String username, @FormParam("firstname") String firstname,
                         @FormParam("lastname") String lastname, @FormParam("email") String email, @FormParam("password") String password){
        User user = new User(username, firstname, lastname, email);
        boolean failed = this.usersDAO.create(user);
        if (!failed){
            this.usersDAO.setPasswordToUser(user, password);
            ConfirmationEmailSender emailSender = new ConfirmationEmailSender();
            try{
                emailSender.send(email,this.usersDAO.getTokenOfUser(username));
            } catch (MessagingException ex) {
                logger.info("Email sending to " + email + " failed.");
            }
            return Network.Signup.SIGN_UP_OK;
        }
        return Network.Signup.SIGN_UP_FAILED;
    }

    @POST
    @Path("/edit/{user}")
    @Produces("text/plain")
    public String editUser(@FormParam("username") String username, @FormParam("firstname") String firstname,
                         @FormParam("lastname") String lastname, @FormParam("email") String email,
                         @FormParam("originalUsername") String originalUsername,
                         @FormParam("originalEmail") String originalEmail) {

        ArrayList<String> data = new ArrayList<>();
        data.add(firstname); data.add(lastname); data.add(username); data.add(email);
        
        String flag = this.usersDAO.edit(data,originalUsername,originalEmail);
        if (flag != "Error"){
            if(! originalEmail.equals(email)){
                ConfirmationEmailSender emailSender = new ConfirmationEmailSender();
                try{
                    emailSender.send(email,flag);
                } catch (MessagingException ex) {
                    logger.info("Email sending to " + email + " failed.");
                }
            }
            return Network.Signup.SIGN_UP_OK;
        }
        return Network.Signup.SIGN_UP_FAILED;


    }
}
