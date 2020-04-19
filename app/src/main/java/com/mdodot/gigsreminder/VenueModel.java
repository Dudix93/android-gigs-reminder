package com.mdodot.gigsreminder;

public class VenueModel {
    private String Name;
    private String Town;

    public VenueModel(String name, String town) {
        Name = name;
        Town = town;
    }

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
}
