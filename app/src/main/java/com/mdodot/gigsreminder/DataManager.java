package com.mdodot.gigsreminder;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static DataManager ourInstance = null;

    public ArrayList<GigModel> gigsList= new ArrayList<>();
    public ArrayList<VenueModel> venuesList= new ArrayList<>();

    public Cursor gigsCursor;
    public Cursor venuesCursor;

    public static  DataManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new DataManager();
        }
        return ourInstance;
    }
    public static void loadFromDatabase(DBHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        DataManager dm = getInstance();
        final String[] gigColumns = {
            GigEntry.COL_EVENT_ID,
            GigEntry.COL_EVENT_BAND,
            GigEntry.COL_EVENT_TOWN,
            GigEntry.COL_EVENT_DATE,
            GigEntry.COL_EVENT_TIME
        };
        final String[] venueColumns = {
            VenueEntry.COL_VENUE_ID,
            VenueEntry.COL_VENUE_NAME,
            VenueEntry.COL_VENUE_TOWN,
        };
        dm.gigsCursor = db.query(GigEntry.TABLE_NAME, gigColumns, null, null, null, null, null);
        dm.venuesCursor = db.query(GigEntry.TABLE_NAME, gigColumns, null, null, null, null, null);
        ;
    }

    public static void loadGigsFromDatabase(Cursor cursor) {
//        int gigIdPos = cursor.getColumnIndex(GigEntry.COL_EVENT_ID);
        int gigBandPos = cursor.getColumnIndex(GigEntry.COL_EVENT_BAND);
        int gigTownPos = cursor.getColumnIndex(GigEntry.COL_EVENT_TOWN);
        int gigDatePos = cursor.getColumnIndex(GigEntry.COL_EVENT_DATE);
        int gigTimePos = cursor.getColumnIndex(GigEntry.COL_EVENT_TIME);

        DataManager dm = getInstance();
        dm.gigsList.clear();
        while (cursor.moveToNext()) {
//            String gigId = cursor.getString(gigIdPos);
            String gigBand = cursor.getString(gigBandPos);
            String gigTown = cursor.getString(gigTownPos);
            String gigDate = cursor.getString(gigDatePos);
            String gigTime = cursor.getString(gigTimePos);
            dm.gigsList.add(new GigModel(gigBand, gigTown, gigDate, gigTime));
        }
        cursor.close();
    }

    public static void loadVenuesFromDatabase(Cursor cursor) {
//        int venueIdPos = cursor.getColumnIndex(VenueEntry.COL_VENUE_ID);
        int venueNamePos = cursor.getColumnIndex(VenueEntry.COL_VENUE_NAME);
        int venueTownPos = cursor.getColumnIndex(VenueEntry.COL_VENUE_TOWN);

        DataManager dm = getInstance();
        dm.venuesList.clear();
        while (cursor.moveToNext()) {
//            String venueId = cursor.getString(venueIdPos);
            String venueName = cursor.getString(venueNamePos);
            String venueTown = cursor.getString(venueTownPos);
            dm.venuesList.add(new VenueModel(venueName, venueTown));
        }
        cursor.close();
    }
}
