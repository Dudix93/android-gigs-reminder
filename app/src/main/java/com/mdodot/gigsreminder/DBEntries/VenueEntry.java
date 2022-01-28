package com.mdodot.gigsreminder.DBEntries;

public class VenueEntry {
    public static final String TABLE_NAME = "Venue";
    public static final String COL_VENUE_ID = "Id";
    public static final String COL_VENUE_NAME = "Name";
    public static final String COL_VENUE_TOWN = "Town"; 
    public static final String COL_VENUE_PLACE_ID = "placeId";

    public static String createTable() {
       return "CREATE TABLE " + TABLE_NAME + " (" +
                COL_VENUE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_VENUE_NAME + " TEXT, " +
                COL_VENUE_TOWN + " TEXT, " +
                COL_VENUE_PLACE_ID + " TEXT " +")";
    }
}

