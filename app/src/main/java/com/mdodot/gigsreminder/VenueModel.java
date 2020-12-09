package com.mdodot.gigsreminder;

public class VenueModel {
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
}