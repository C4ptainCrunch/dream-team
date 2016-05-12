package models.databaseModels;

import java.io.IOException;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Project {
    private String username;
    private String uid;
    private int userID;
    private String path;
    private String last_modification;
    private boolean write_default;
    private boolean read_default;
    private String name;

    private boolean currentUserWritePerm = false;
    private boolean currentUserReadPerm = false;

    public Project() {
    }

    public Project(models.project.Project project) throws IOException {
        this.path = project.getPath().toString();
        this.uid = project.getUid();
        this.userID = 0;
        this.last_modification = project.getLastChange().toString();
        this.write_default = project.getWriteDefault();
        this.read_default = project.getReadDefault();
        this.name = project.getName();
    }

    public Project(String uid, int user, String path, String last_modification, boolean write_default, boolean read_default, String name,
            String username) {
        this.uid = uid;
        this.userID = user;
        this.path = path;
        this.last_modification = last_modification;
        this.write_default = write_default;
        this.read_default = read_default;
        this.name = name;
        this.username = username;
    }

    public String toString() {
        return this.getName();
    }

    @XmlAttribute
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @XmlAttribute
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @XmlAttribute
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @XmlAttribute
    public String getLast_modification() {
        return last_modification;
    }

    public void setLast_modification(String last_modification) {
        this.last_modification = last_modification;
    }

    @XmlAttribute
    public boolean isWrite_default() {
        return write_default;
    }

    public void setWrite_default(boolean write_default) {
        this.write_default = write_default;
    }

    @XmlAttribute
    public boolean isRead_default() {
        return read_default;
    }

    public void setRead_default(boolean read_default) {
        this.read_default = read_default;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    public boolean isCurrentUserWritePerm() {
        return currentUserWritePerm;
    }

    public void setCurrentUserWritePerm(boolean currentUserWritePerm) {
        this.currentUserWritePerm = currentUserWritePerm;
    }

    @XmlAttribute
    public boolean isCurrentUserReadPerm() {
        return currentUserReadPerm;
    }

    public void setCurrentUserReadPerm(boolean currentUserReadPerm) {
        this.currentUserReadPerm = currentUserReadPerm;
    }

    @XmlAttribute
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
