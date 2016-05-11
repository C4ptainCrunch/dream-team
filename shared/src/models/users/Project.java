package models.users;

import javax.xml.bind.annotation.XmlAttribute;

public class Project {
    private int id;
    private User user;
    private String path;
    private String last_modification;
    private boolean write_default;
    private boolean read_default;

    public Project(){}

    public Project(User user, String path, String last_modification, boolean write_default, boolean read_default) {
        this.user = user;
        this.path = path;
        this.last_modification = last_modification;
        this.write_default = write_default;
        this.read_default = read_default;
    }

    public Project(int id, User user, String path, String last_modification, boolean write_default, boolean read_default) {
        this.id = id;
        this.user = user;
        this.path = path;
        this.last_modification = last_modification;
        this.write_default = write_default;
        this.read_default = read_default;
    }


    @XmlAttribute
    public User getUser() {
        return user;
    }

    @XmlAttribute
    public int getId() {
        return id;
    }

    @XmlAttribute
    public String getPath() {
        return path;
    }

    @XmlAttribute
    public String getLast_modification() {
        return last_modification;
    }

    @XmlAttribute
    public int isWrite_default() {
        return write_default ? 1 : 0;
    }

    @XmlAttribute
    public int isRead_default() {
        return read_default ? 1 : 0;
    }

    public void setId(int id){
        this.id = id;
    }
}