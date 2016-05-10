package models.project;

import java.io.Serializable;
import java.util.Date;

/**
 * A Diff object is a serializable object that represents the changes made from the creation of a Project object.
 * It also contains a Date object that gives the date of the modification.
 */

public class Diff implements Serializable {
    private Date date;
    private String patch;

    /**
     * Default constructor.
     * @param date The date of a modification.
     * @param patch The modification represented as a String.
     */

    public Diff(Date date, String patch) {
        this.date = date;
        this.patch = patch;
    }

    /**
     * Returns the date of the modification.
     * @return a Date object.
     */

    public Date getDate() {
        return date;
    }

    /**
     * Returns the modification as a String.
     * @return the modification.
     */

    public String getPatch() {
        return patch;
    }
}
