package com.mdodot.gigsreminder;

import java.util.Date;
import java.util.Timer;

public class GigModel {
    private String band;
    private String town;
//    private Date date;
//    private Timer time;

    public GigModel(String band, String town) {
        this.band = band;
        this.town = town;
    }

    public String getTown() { return town; }

    public void setTown(String town) { this.town = town; }

    public String getBand() { return band; }

    public void setBand(String band) {
        this.band = band;
    }

//    public Date getDate() {
//        return date;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }
//
//    public Timer getTime() {
//        return time;
//    }
//
//    public void setTime(Timer time) {
//        this.time = time;
//    }
}
