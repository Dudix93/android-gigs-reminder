package com.mdodot.gigsreminder;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mdodot.gigsreminder.DBEntries.GigEntry;
import com.mdodot.gigsreminder.DBEntries.VenueEntry;
import com.mdodot.gigsreminder.Models.GigModel;
import com.mdodot.gigsreminder.Models.VenueModel;

import java.util.ArrayList;

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
            GigEntry.COL_EVENT_TIME,
            GigEntry.COL_EVENT_VENUE
        };
        final String[] venueColumns = {
            VenueEntry.COL_VENUE_ID,
            VenueEntry.COL_VENUE_NAME,
            VenueEntry.COL_VENUE_TOWN,
            VenueEntry.COL_VENUE_PLACE_ID,
        };
        dm.gigsCursor = db.query(GigEntry.TABLE_NAME, gigColumns, null, null, null, null, null);
        dm.venuesCursor = db.query(VenueEntry.TABLE_NAME, venueColumns, null, null, null, null, null);
        ;
    }

    public static void loadGigsFromDatabase(Cursor cursor) {
        int gigIdPos = cursor.getColumnIndex(GigEntry.COL_EVENT_ID);
        int gigBandPos = cursor.getColumnIndex(GigEntry.COL_EVENT_BAND);
        int gigTownPos = cursor.getColumnIndex(GigEntry.COL_EVENT_TOWN);
        int gigDatePos = cursor.getColumnIndex(GigEntry.COL_EVENT_DATE);
        int gigTimePos = cursor.getColumnIndex(GigEntry.COL_EVENT_TIME);
        int gigVenuePos = cursor.getColumnIndex(GigEntry.COL_EVENT_VENUE);

        DataManager dm = getInstance();
        dm.gigsList.clear();
        while (cursor.moveToNext()) {
            int gigId = cursor.getInt(gigIdPos);
            String gigBand = cursor.getString(gigBandPos);
            String gigTown = cursor.getString(gigTownPos);
            String gigDate = cursor.getString(gigDatePos);
            String gigTime = cursor.getString(gigTimePos);
            int gigVenue = cursor.getInt(gigVenuePos);
            dm.gigsList.add(new GigModel(gigId, gigBand, gigTown, gigDate, gigTime, gigVenue));
        }
        cursor.close();
    }

    public static void loadVenuesFromDatabase(Cursor cursor) {
        int venueIdPos = cursor.getColumnIndex(VenueEntry.COL_VENUE_ID);
        int venueNamePos = cursor.getColumnIndex(VenueEntry.COL_VENUE_NAME);
        int venueTownPos = cursor.getColumnIndex(VenueEntry.COL_VENUE_TOWN);
        int venuePlaceIdPos = cursor.getColumnIndex(VenueEntry.COL_VENUE_PLACE_ID);

        DataManager dm = getInstance();
        dm.venuesList.clear();
        while (cursor.moveToNext()) {
            int venueId = cursor.getInt(venueIdPos);
            String venueName = cursor.getString(venueNamePos);
            String venueTown = cursor.getString(venueTownPos);
            String venuePlaceId = cursor.getString(venuePlaceIdPos);
            dm.venuesList.add(new VenueModel(venueId, venueName, venueTown, venuePlaceId));
        }
        cursor.close();
    }
}
