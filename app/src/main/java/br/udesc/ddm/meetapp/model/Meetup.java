package br.udesc.ddm.meetapp.model;

public class Meetup {

    private String title;
    private String location;
    private String date;
    private String user;
    private int image;

    public Meetup() {
    }

    public Meetup(String title, String location, String date, String user, int image) {
        this.title = title;
        this.location = location;
        this.date = date;
        this.user = user;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
