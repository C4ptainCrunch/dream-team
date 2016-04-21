import javax.ws.rs.*;

@Path("/helloworld")
public class RestServer {

    @GET
    @Produces("text/html")
    public String getMessage(){
        System.out.println("sayHello()");
        return "Hello, world!";
    }
}
