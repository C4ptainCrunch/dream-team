package models.users;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

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

    public User(String username, String firstName, String lastName, String email) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
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
