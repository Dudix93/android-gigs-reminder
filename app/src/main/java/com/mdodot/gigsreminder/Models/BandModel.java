package com.mdodot.gigsreminder.Models;

import java.io.Serializable;
import java.util.Date;
import java.util.Timer;

public class BandModel implements Serializable {
    private int id;
    private String bandName;

    public BandModel(int id, String bandName) {
        this.id = id;
        this.bandName = bandName;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getBandName() { return bandName; }

    public void setBandName(String band) {
        this.bandName = bandName;
    }
}

