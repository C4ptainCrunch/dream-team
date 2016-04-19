package models.project;

import java.io.Serializable;
import java.util.Date;

public class Diff implements Serializable{
    private Date date;
    private String patch;

    public Diff(Date date, String patch) {
        this.date = date;
        this.patch = patch;
    }

    public Date getDate() {
        return date;
    }

    public String getPatch() {
        return patch;
    }
}
