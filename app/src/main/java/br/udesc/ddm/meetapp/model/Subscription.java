package br.udesc.ddm.meetapp.model;

import com.google.gson.annotations.SerializedName;

public class Subscription {

    @SerializedName("id")
    private int id;
    @SerializedName("meetup")
    private Meetup meetup;

    public Subscription() {
    }

    public Subscription(int id, Meetup meetup) {
        this.id = id;
        this.meetup = meetup;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Meetup getMeetup() {
        return meetup;
    }

    public void setMeetup(Meetup meetup) {
        this.meetup = meetup;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", meetup=" + meetup +
                '}';
    }
}
