package models.databaseModels;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by acaccia on 11/05/16.
 */
@XmlRootElement
public class Permissions {
    private String projectUID;
    private int userID;
    private boolean writable, readable;

    public Permissions() {}

    public Permissions(String projectUID, int userID, boolean write, boolean read) {
        this.projectUID = projectUID;
        this.userID = userID;
        this.writable = write;
        this.readable = read;
    }

    @XmlAttribute
    public String getProjectUID() {
        return projectUID;
    }

    @XmlAttribute
    public int getUserID() {
        return userID;
    }

    @XmlAttribute
    public boolean isWritable() {
        return writable;
    }

    @XmlAttribute
    public boolean isReadable() {
        return readable;
    }

    public void setProjectUID(String projectUID) {
        this.projectUID = projectUID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setWritable(boolean writable) {
        this.writable = writable;
    }

    public void setReadable(boolean readable) {
        this.readable = readable;
    }
}
