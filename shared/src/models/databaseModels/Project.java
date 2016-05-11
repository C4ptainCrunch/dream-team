package models.databaseModels;

import javax.xml.bind.annotation.XmlAttribute;

public class Project {
    private int id;
    private int userID;
    private String path;
    private String last_modification;
    private boolean write_default;
    private boolean read_default;

    public Project(){}

    public Project(int user, String path, String last_modification, boolean write_default, boolean read_default) {
        this.userID = user;
        this.path = path;
        this.last_modification = last_modification;
        this.write_default = write_default;
        this.read_default = read_default;
    }

    public Project(int id, int user, String path, String last_modification, boolean write_default, boolean read_default) {
        this.id = id;
        this.userID = user;
        this.path = path;
        this.last_modification = last_modification;
        this.write_default = write_default;
        this.read_default = read_default;
    }


    @XmlAttribute
    public int getUserID() {
        return userID;
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