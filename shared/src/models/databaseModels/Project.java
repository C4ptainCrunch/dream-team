package models.databaseModels;

import javax.xml.bind.annotation.XmlAttribute;
import java.io.IOException;

public class Project {
    private int id;
    private int userID;
    private String path;
    private String last_modification;
    private boolean write_default;
    private boolean read_default;
    private int userId;

    public Project(){}

    public Project(models.project.Project project) throws IOException {
        this.path = project.getPath().toString();
        this.userID = project.getUserID();
        this.id = project.getID();
        this.last_modification = project.getLastChange().toString();
        this.write_default = project.getWriteDefault();
        this.read_default = project.getReadDefaukt();
    }

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

    public boolean readable() {return read_default;}
    public boolean writeable() {return write_default;}

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

    public void setUserID(int userId) {
        this.userId = userId;
    }
}