package com.mdodot.gigsreminder;

import java.util.Date;
import java.util.Timer;

public class GigModel {
    private int id;
    private String band;
    private String town;
    private String date;
    private String time;

    public GigModel(int id, String band, String town, String date, String time) {
        this.id = id;
        this.band = band;
        this.town = town;
        this.date = date;
        this.time = time;
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
}
