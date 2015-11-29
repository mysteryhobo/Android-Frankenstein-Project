package csci4100.uoit.ca.mobilenoteproject;

import android.net.Uri;

import java.util.Date;

/**
 * Created by ubuntu on 08/11/15.
 */
public class Note {
    private String name;
    private String description;
    private Date dateCreated;
    private boolean hasPicture = false;
    private boolean hasVideo = false;
    private boolean hasPDF = false;
    private boolean hasdescription = false;
    private Uri imageUri;


    public Note(String name, String description, Date date){
        this.name = name;
        this.description = description;
        this.hasdescription = true;
        this.dateCreated = date;
    }

    public Note(String name, String description, Date date, Uri image){
        this.name = name;
        this.description = description;
        this.hasdescription = true;
        this.dateCreated = date;
        this.imageUri = image;
        this.hasPicture = true;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean isHasPicture() {
        return hasPicture;
    }

    public void setHasPicture(boolean hasPicture) {
        this.hasPicture = hasPicture;
    }

    public boolean isHasVideo() {
        return hasVideo;
    }

    public void setHasVideo(boolean hasVideo) {
        this.hasVideo = hasVideo;
    }

    public boolean isHasPDF() {
        return hasPDF;
    }

    public void setHasPDF(boolean hasPDF) {
        this.hasPDF = hasPDF;
    }
}
