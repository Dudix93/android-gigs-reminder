package com.mdodot.gigsreminder;

class GigEntry {
    public static final String TABLE_NAME = "Event";
    public static final String COL_EVENT_ID = "Id";
    public static final String COL_EVENT_BAND = "Band";
    public static final String COL_EVENT_TOWN = "Town";
    public static final String COL_EVENT_DATE = "Date";
    public static final String COL_EVENT_TIME = "Time";
    public static final String COL_EVENT_VENUE = "Venue";

    public static String createTable() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                COL_EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_EVENT_BAND + " TEXT, " +
                COL_EVENT_TOWN + " TEXT, " +
                COL_EVENT_DATE + " TEXT, " +
                COL_EVENT_TIME + " INT, " +
                COL_EVENT_VENUE + " INT" + ")";
    }
}

class VenueEntry {
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

class BandEntry {
    public static final String TABLE_NAME = "Band";
    public static final String COL_BAND_ID = "Id";
    public static final String COL_BAND_NAME = "Name";

    public static String createTable() {
       return "CREATE TABLE " + TABLE_NAME + " (" +
               COL_BAND_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
               COL_BAND_NAME + " TEXT " +")";
    }
}

class EventBand {
    public static final String TABLE_NAME = "EventBand";
    public static final String COL_EVENT_BAND_ID = "Id";
    public static final String COL_EVENT_BAND_EVENT_ID = "EventId";
    public static final String COL_EVENT_BAND_BAND_ID = "BandId";

    public static String createTable() {
       return "CREATE TABLE " + TABLE_NAME + " (" +
               COL_EVENT_BAND_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
               COL_EVENT_BAND_EVENT_ID + " INT, " +
               COL_EVENT_BAND_BAND_ID + " INT " +")";
    }
}
