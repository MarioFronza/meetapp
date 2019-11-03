package br.udesc.ddm.meetapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Meetup implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("description")
    private String description;
    @SerializedName("title")
    private String title;
    @SerializedName("location")
    private String location;
    @SerializedName("date")
    private String date;
    @SerializedName("image")
    private Image image;
    @SerializedName("user")
    private User user;

    public Meetup() {
    }

    public Meetup(int id, String description, String title, String location, String date, Image image, User user) {
        this.id = id;
        this.description = description;
        this.title = title;
        this.location = location;
        this.date = date;
        this.image = image;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public Image getImage() {
        return image;
    }

    public User getUser() {
        return user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meetup{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", location='" + location + '\'' +
                ", date='" + date + '\'' +
                ", image=" + image +
                ", user=" + user +
                '}';
    }


}


