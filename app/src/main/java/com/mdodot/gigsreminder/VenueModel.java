package com.mdodot.gigsreminder;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class VenueModel implements Serializable {
    private int Id;
    private String Name;
    private String Town;
    private String placeId;

    public VenueModel(int id, String name, String town, String placeId) {
        this.Id = id;
        this.Name = name;
        this.Town = town;
        this.placeId = placeId;
    }

    public int getId() { return Id; }

    public void setId(int id) { Id = id; }

    public String getName() { return Name; }

    public String getTown() { return Town; }

    public void setName(String name) { Name = name; }

    public void setTown(String town) { Town = town; }

    public String getPlaceId() { return placeId; }

    public void setPlaceId(String placeId) { this.placeId = placeId; }

    @NonNull
    @Override
    public String toString() { return Name; }
}
