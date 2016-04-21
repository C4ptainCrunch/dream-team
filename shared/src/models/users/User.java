package models.users;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement
public class User {
    private String username;
    private int id;
    private List<String> documents;

    public User() {
        // JAX-RS needs a constructor without any parameters
    }

    public User(int id, String username, List<String> documents) {
        this.id = id;
        this.username = username;
        this.documents = documents;
    }

    @XmlAttribute
    public String getUsername() {
        return username.toUpperCase();
    }


    @XmlAttribute
    public int getId() {
        return id;
    }

    @XmlElementWrapper
    @XmlElement(name="document")
    public List<String> getDocuments() {
        return documents;
    }
}
