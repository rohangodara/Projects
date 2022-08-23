package gitlet;

// TODO: any imports you need here

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date; // TODO: You'll likely use this in this class
import java.util.HashMap;


/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author Prithvi Singh & Rohan Godara
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /**
     * The message of this Commit.
     */
    private String message;
    //private HashMap<String, Blob> addedBlobs; //https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html
    //private HashMap<String, Blob> removedBlobs;
    private Date dateTime;
    private String parentSha1;
    private HashMap<String, Blob> trackedBlobs;

    /* TODO: fill in the rest of this class. */

    public Commit(String message, String parentSha, Date date, HashMap<String, Blob> added) {
        this.message = message;
        this.parentSha1 = parentSha;
        this.dateTime = date;
        this.trackedBlobs = added;
    }

    public String getMessage() {
        return this.message;
    }

    public String getTime() {
        String format = "EEE MMM dd HH:mm:ss YYYY Z";
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return (formatter.format(this.dateTime));
    }

    public String getParentHash() {
        return this.parentSha1;
    }


    public HashMap<String, Blob> getTrackedBlobs() {
        return this.trackedBlobs;
    }
}

