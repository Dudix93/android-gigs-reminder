package com.mdodot.gigsreminder.Models;

import java.io.Serializable;
import java.util.Date;
import java.util.Timer;

public class GigModel implements Serializable {
    private int id;
    private String band;
    private String town;
    private String date;
    private String time;
    private int venue;

    public GigModel(int id, String band, String town, String date, String time, int venue) {
        this.id = id;
        this.band = band;
        this.town = town;
        this.date = date;
        this.time = time;
        this.venue = venue;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getTown() { return town; }

    public void setTown(String town) { this.town = town; }

    public String getBand() { return band; }

    public void setBand(String band) {
        this.band = band;
    }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }

    public void setTime(String time) { this.time = time; }

    public int getVenue() { return venue; }

    public void setVenue(int venue) { this.venue = venue; }
}
