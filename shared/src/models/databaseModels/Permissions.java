package models.databaseModels;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by acaccia on 11/05/16.
 */
public class Permissions {
    private int projectID, userID;
    private boolean writable, readable;

    public Permissions() {}

    public Permissions(int projectID, int userID, boolean write, boolean read) {
        this.projectID = projectID;
        this.userID = userID;
        this.writable = write;
        this.readable = read;
    }

    @XmlAttribute
    public int getProjectID() {
        return projectID;
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
}
