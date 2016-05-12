package models.databaseModels;

import javax.xml.bind.annotation.XmlAttribute;
import java.io.IOException;

public class Project {
    private String uid;
    private int userID;
    private String path;
    private String last_modification;
    private boolean write_default;
    private boolean read_default;

    public Project(){}

    public Project(models.project.Project project) throws IOException {
        this.path = project.getPath().toString();
        this.uid = project.getUid();
        this.userID = 0;
        this.last_modification = project.getLastChange().toString();
        this.write_default = project.getWriteDefault();
        this.read_default = project.getReadDefault();
    }

    public Project(int user, String path, String last_modification, boolean write_default, boolean read_default) {
        this.userID = user;
        this.path = path;
        this.last_modification = last_modification;
        this.write_default = write_default;
        this.read_default = read_default;
    }

    public Project(String uid, int user, String path, String last_modification, boolean write_default, boolean read_default) {
        this.uid = uid;
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
    public String getUid() {
        return uid;
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

    public void setUid(String uid){
        this.uid = uid;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public boolean hasWritePerm(User user){
        return true; //TODO
    }
}
