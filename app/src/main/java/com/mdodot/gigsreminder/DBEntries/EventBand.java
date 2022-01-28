package com.mdodot.gigsreminder.DBEntries;

public class EventBand {
    public static final String TABLE_NAME = "EventBand";
    public static final String COL_EVENT_BAND_ID = "Id";
    public static final String COL_EVENT_BAND_EVENT_ID = "EventId";
    public static final String COL_EVENT_BAND_BAND_ID = "BandId";

    public static String createTable() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                COL_EVENT_BAND_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_EVENT_BAND_EVENT_ID + " INT, " +
                COL_EVENT_BAND_BAND_ID + " INT " + ")";
    }
}
