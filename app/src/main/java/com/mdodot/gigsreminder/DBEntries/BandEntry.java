package com.mdodot.gigsreminder.DBEntries;

public class BandEntry {
    public static final String TABLE_NAME = "Band";
    public static final String COL_BAND_ID = "Id";
    public static final String COL_BAND_NAME = "Name";

    public static String createTable() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                COL_BAND_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_BAND_NAME + " TEXT " + ")";
    }
}
