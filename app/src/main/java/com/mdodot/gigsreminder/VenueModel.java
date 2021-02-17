package com.mdodot.gigsreminder;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class VenueModel implements Serializable {
    private int Id;
    private String Name;
    private String Town;

    public VenueModel(int id, String name, String town) {
        this.Id = id;
        this.Name = name;
        this.Town = town;
    }

    public int getId() { return Id; }

    public void setId(int id) { Id = id; }

    public String getName() {
        return Name;
    }

    public String getTown() {
        return Town;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setTown(String town) {
        Town = town;
    }

    @NonNull
    @Override
    public String toString() {
        return Name;
    }
}
