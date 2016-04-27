package models.users;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement
public class User {
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> documents;


    public User() {
        // JAX-RS needs a constructor without any parameters
    }

    public User(int id, String username, List<String> documents) {
        this.id = id;
        this.username = username;
        this.documents = documents;
    }

    public User(int id, String username, String firstName, String lastName, String email) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @XmlAttribute
    public String getUsername() {
        return username;
    }

    @XmlAttribute
    public int getId() {
        return id;
    }

    @XmlAttribute
    public String getFirstName() { return firstName; }

    @XmlAttribute
    public String getLastName() { return lastName; }

    @XmlAttribute
    public String getEmail() { return email; }

    @XmlElementWrapper
    @XmlElement(name="document")
    public List<String> getDocuments() {
        return documents;
    }

    public void setId(int id) {this.id = id;}
}
