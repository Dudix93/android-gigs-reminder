package com.mdodot.gigsreminder;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DB_VER = 1;
    private static final String DB_NAME = "gigsDb";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(GigEntry.createTable());
        sqLiteDatabase.execSQL(VenueEntry.createTable());
        sqLiteDatabase.execSQL(BandEntry.createTable());
        sqLiteDatabase.execSQL(EventBand.createTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + GigEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + VenueEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BandEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EventBand.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void deleteGig(SQLiteDatabase sqLiteDatabase, int id) {
        sqLiteDatabase.execSQL("DELETE FROM  " + GigEntry.TABLE_NAME + " where "+ GigEntry.COL_EVENT_ID + " = " + id);
    }

    public void deleteVenue(SQLiteDatabase sqLiteDatabase, int id) {
        sqLiteDatabase.execSQL("DELETE FROM  " + VenueEntry.TABLE_NAME + " where "+ VenueEntry.COL_VENUE_ID + " = " + id);
    }
    public void deleteBand(SQLiteDatabase sqLiteDatabase, int id) {
        sqLiteDatabase.execSQL("DELETE FROM  " + BandEntry.TABLE_NAME + " where "+ BandEntry.COL_BAND_ID + " = " + id);
    }

    public boolean isVenueAssigned(SQLiteDatabase sqLiteDatabase, int id) {
        Cursor res = sqLiteDatabase.rawQuery("SELECT " + GigEntry.COL_EVENT_ID +
                " FROM  " + GigEntry.TABLE_NAME +
                " where " + GigEntry.COL_EVENT_VENUE + " = " + id, null);
        return res.getCount() > 0;
    }

    public boolean isBandListed(SQLiteDatabase sqLiteDatabase, String bandName) {
        Cursor res = sqLiteDatabase.rawQuery("SELECT " + BandEntry.COL_BAND_ID +
                " FROM  " + BandEntry.TABLE_NAME +
                " where " + BandEntry.COL_BAND_NAME + " = '" + bandName + "'", null);
        return res.getCount() > 0;
    }

    public boolean isBandAssignedToEvent(SQLiteDatabase sqLiteDatabase, int bandId, int eventId) {
        Cursor res = sqLiteDatabase.rawQuery("SELECT " + EventBand.COL_EVENT_BAND_ID +
                " FROM  " + EventBand.TABLE_NAME +
                " where " + EventBand.COL_EVENT_BAND_BAND_ID + " = " + bandId +
                " and " + EventBand.COL_EVENT_BAND_EVENT_ID + " = " + eventId, null);
        return res.getCount() > 0;
    }

    public int eventsAssignedToSupport(SQLiteDatabase sqLiteDatabase, int bandId) {
        Cursor res = sqLiteDatabase.rawQuery("SELECT " + EventBand.COL_EVENT_BAND_ID +
                " FROM  " + EventBand.TABLE_NAME +
                " where " + EventBand.COL_EVENT_BAND_BAND_ID + " = " + bandId, null);
        return res.getCount();
    }

    public void unassignBandFromEvent(SQLiteDatabase sqLiteDatabase, int bandId, int eventId) {
        sqLiteDatabase.execSQL("DELETE FROM " + EventBand.TABLE_NAME +
                " where " + EventBand.COL_EVENT_BAND_BAND_ID + " = " + bandId +
                " and " + EventBand.COL_EVENT_BAND_EVENT_ID + " = " + eventId);
    }

    public Cursor getBandsByEvent(SQLiteDatabase sqLiteDatabase, int id) {
        Cursor res = sqLiteDatabase.rawQuery("SELECT " +
                BandEntry.TABLE_NAME + "." + BandEntry.COL_BAND_ID + " , " +
                BandEntry.TABLE_NAME + "." + BandEntry.COL_BAND_NAME +
                " FROM  " + BandEntry.TABLE_NAME +
                " INNER JOIN " + EventBand.TABLE_NAME +
                " ON " + BandEntry.TABLE_NAME + "." + BandEntry.COL_BAND_ID + " = " + EventBand.COL_EVENT_BAND_BAND_ID +
                " where " + EventBand.TABLE_NAME + "." + EventBand.COL_EVENT_BAND_EVENT_ID + " = " + id, null);
        return res;
    }

    public int getBandId(SQLiteDatabase sqLiteDatabase, String bandName) {
        Cursor res = sqLiteDatabase.rawQuery("SELECT " +
                BandEntry.TABLE_NAME + "." + BandEntry.COL_BAND_ID +
                " FROM  " + BandEntry.TABLE_NAME +
                " where " + BandEntry.TABLE_NAME + "." + BandEntry.COL_BAND_NAME + " = '" + bandName + "'" , null);
        res.moveToFirst();
        int bandIdPos = res.getColumnIndex(BandEntry.COL_BAND_ID);
        return res.getInt(bandIdPos);
    }

    public Cursor venueEvents(SQLiteDatabase sqLiteDatabase, String placeId) {
        Cursor res = sqLiteDatabase.rawQuery(" SELECT "+
                GigEntry.TABLE_NAME + "." + GigEntry.COL_EVENT_BAND + " , " +
                GigEntry.TABLE_NAME + "." + GigEntry.COL_EVENT_DATE +
                " FROM " + GigEntry.TABLE_NAME +
                " INNER JOIN " + VenueEntry.TABLE_NAME +
                " ON " + VenueEntry.TABLE_NAME + "." + VenueEntry.COL_VENUE_ID + " = " + GigEntry.TABLE_NAME + "." + GigEntry.COL_EVENT_VENUE +
                " WHERE " + VenueEntry.TABLE_NAME + "." + VenueEntry.COL_VENUE_PLACE_ID + " = '" + placeId + "'", null);
        return res;
    }

    public Cursor getGigById(SQLiteDatabase sqLiteDatabase, int gigId) {
        Cursor res = sqLiteDatabase.rawQuery(" SELECT "+
                GigEntry.TABLE_NAME + "." + GigEntry.COL_EVENT_BAND + " , " +
                GigEntry.TABLE_NAME + "." + GigEntry.COL_EVENT_VENUE + " , " +
                GigEntry.TABLE_NAME + "." + GigEntry.COL_EVENT_TIME + " , " +
                GigEntry.TABLE_NAME + "." + GigEntry.COL_EVENT_TOWN + " , " +
                GigEntry.TABLE_NAME + "." + GigEntry.COL_EVENT_DATE +
                " FROM " + GigEntry.TABLE_NAME +
                " WHERE " + GigEntry.TABLE_NAME + "." + GigEntry.COL_EVENT_ID + " = '" + gigId + "'", null);
        return res;
    }

    public String getPlaceId(SQLiteDatabase sqLiteDatabase, int venueId) {
        Cursor res = sqLiteDatabase.rawQuery("SELECT " + VenueEntry.TABLE_NAME + "." + VenueEntry.COL_VENUE_PLACE_ID +
                " FROM  " + VenueEntry.TABLE_NAME +
                " where " + VenueEntry.TABLE_NAME + "." + VenueEntry.COL_VENUE_ID + " = '" + venueId + "'" , null);
        if (res.getCount() > 0) {
            res.moveToFirst();
            int placeIdPos = res.getColumnIndex(VenueEntry.COL_VENUE_PLACE_ID);
            return res.getString(placeIdPos);
        }
        else {
            return "error";
        }
    }
}
