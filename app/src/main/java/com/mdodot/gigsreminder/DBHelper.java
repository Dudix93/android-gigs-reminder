package com.mdodot.gigsreminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + GigEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + VenueEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void deleteGig(SQLiteDatabase sqLiteDatabase, int id) {
        sqLiteDatabase.execSQL("DELETE FROM  " + GigEntry.TABLE_NAME + " where "+ GigEntry.COL_EVENT_ID + " = " + id);
    }

    public void deleteVenue(SQLiteDatabase sqLiteDatabase, int id) {
        sqLiteDatabase.execSQL("DELETE FROM  " + VenueEntry.TABLE_NAME + " where "+ VenueEntry.COL_VENUE_ID + " = " + id);
    }

    public boolean isVenueAssigned(SQLiteDatabase sqLiteDatabase, int id) {
        ArrayList<Integer> gigIds = new ArrayList<>();
        Cursor res = sqLiteDatabase.rawQuery("SELECT " + GigEntry.COL_EVENT_ID + " FROM  " + GigEntry.TABLE_NAME + " where " + GigEntry.COL_EVENT_VENUE + " = " + id, null);
        return res.getCount() > 0;
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
    }
