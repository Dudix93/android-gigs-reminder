package com.mdodot.gigsreminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

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

//    public void saveToDB(String Band, String Town, String Date, String Time) {
//        SQLiteDatabase database = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COL_EVENT_BAND, Band);
//        values.put(COL_EVENT_TOWN, Town);
//        values.put(COL_EVENT_DATE, Date);
//        values.put(COL_EVENT_TIME, Time);
//        database.insert(TABLE_EVENT_NAME, null, values);
//    }
//
//    public Cursor readFromDB() {
//        SQLiteDatabase database = this.getReadableDatabase();
//
//        String[] projection = {
//            COL_EVENT_ID,
//            COL_EVENT_BAND,
//            COL_EVENT_TOWN,
//            COL_EVENT_DATE,
//            COL_EVENT_TIME
//        };
//
//        return database.query(TABLE_EVENT_NAME, projection, null, null, null, null, null);
//    }
}
