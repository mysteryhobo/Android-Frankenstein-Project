package csci4100.uoit.ca.mobilenoteproject;

import android.net.Uri;

import java.util.Date;

/**
 * Created by Peter Little on 08/11/15.
 */
public class Note {
    private long id;
    private String name;
    private String description;
    private String date;
    private String time;
    private boolean hasPicture = false;
    private boolean hasVideo = false;
    private boolean hasPDF = false;
    private boolean hasdescription = false;
    private Uri imageUri;


    public Note(String name, String description, String date, String time){
        this.name = name;
        this.description = description;
        this.hasdescription = true;
        this.date = date;

        this.time = time;
    }

    public Note(String name, String description, String date, Uri image){
        this.name = name;
        this.description = description;
        this.hasdescription = true;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}