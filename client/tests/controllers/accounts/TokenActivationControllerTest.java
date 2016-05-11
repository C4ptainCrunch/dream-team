package controllers.accounts;

import misc.utils.ClientTest;
import org.junit.After;
import org.junit.Before;

/**
 * Created by bambalaam on 27/04/16.
 */
public class TokenActivationControllerTest extends ClientTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    /*
    @Test
    public void testValidateToken() throws Exception {

        Client client = ClientBuilder.newClient();

        Form postForm = new Form("token","TOKENTEST");

        Response response = client.target("http://localhost:5555")
                .path("user/activate/"+"Bam")
                .request(MediaType.TEXT_PLAIN_TYPE)
                .post(Entity.form(postForm));

        assertEquals(response.readEntity(String.class),"OK");
    }*/

    /*@Test
    public void testGetUser() throws Exception {
        Client client = ClientBuilder.newClient();

        String entity = client.target("http://localhost:5555")
                .path("user/activate/"+"aaaa")
                .request(MediaType.TEXT_PLAIN_TYPE)
                .get();

    }*/
}
