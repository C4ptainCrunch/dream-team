package controllers.accounts;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.*;

/**
 * Created by bambalaam on 27/04/16.
 */
public class TokenActivationControllerTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testValidateToken() throws Exception {

        Client client = ClientBuilder.newClient();

        Form postForm = new Form("token","TOKENTEST");

        Response response = client.target("http://localhost:5555")
                .path("user/activate/"+"Bam")
                .request(MediaType.TEXT_PLAIN_TYPE)
                .post(Entity.form(postForm));

        assertEquals(response.readEntity(String.class),"OK");
    }

    /*@Test
    public void testGetUser() throws Exception {
        Client client = ClientBuilder.newClient();

        String entity = client.target("http://localhost:5555")
                .path("user/activate/"+"aaaa")
                .request(MediaType.TEXT_PLAIN_TYPE)
                .get();

    }*/
}